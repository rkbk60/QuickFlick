package com.rkbk60.quickflick

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.inputmethodservice.InputMethodService
import android.inputmethodservice.KeyboardView
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import com.rkbk60.quickflick.domain.*
import com.rkbk60.quickflick.model.*

/**
 * main input method service
 */

class CustomIME : InputMethodService(), KeyboardView.OnKeyboardActionListener {
    private lateinit var keyboardView: CustomKeyboardView
    private val keyboardController by lazy { KeyboardController(this) }

    private val resourceServer by lazy { ResourceServer(this) }

    private val keymap = KeymapController()
    private val modStorage = ModKeyStorage()
    private lateinit var flickFactory: FlickFactory
    private lateinit var multiTapManager: MultiTapManager

    // current keyboard information
    private var isRight
        get() = resourceServer.keyboardIsRight.current
        set(value) { resourceServer.keyboardIsRight.current = value }
    private var isPortrait
        get()  = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        set(_) = Unit
    private var heightLevel
        get()  = resourceServer.keyboardHeight.current.toInt()
        set(_) = Unit

    // current action information
    private var tapX = -1
    private var tapY = -1
    private var lastAction = MotionEvent.ACTION_UP
    private var onPressCode = KeyIndex.NOTHING
    private var flick = Flick.NONE
    private var canInput = true

    private lateinit var indicatorFactory: IndicatorFactory

    private lateinit var editorInfo: EditorInfo

    @SuppressLint("InflateParams")
    override fun onCreateInputView(): View {
        keyboardView = layoutInflater.inflate(R.layout.keyboardview, null) as CustomKeyboardView
        return keyboardView.apply {
            setKeyboardWith(keyboardController, isRight, isPortrait, heightLevel)
            isPreviewEnabled = false
            setOnKeyboardActionListener(this@CustomIME)
            setOnTouchListener Listener@ { _, event ->
                val x = event.x.toInt()
                val y = event.y.toInt()
                val action = event.action and MotionEvent.ACTION_MASK
                lastAction = action
                when (action) {
                    MotionEvent.ACTION_DOWN -> {
                        tapX = x
                        tapY = y
                        return@Listener false
                    }
                    MotionEvent.ACTION_POINTER_DOWN -> {
                        multiTapManager.addTapCount()
                        when {
                            multiTapManager.canCancelInput() -> {
                                resetTapPoint()
                                canInput = false
                                onPressCode = KeyIndex.NOTHING
                                flick = Flick.NONE
                            }
                            multiTapManager.canCancelFlick() -> {
                                tapX = x
                                tapY = y
                                flick = Flick.NONE
                            }
                        }
                        indicateFlickState()
                        return@Listener true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        flick = flickFactory.makeWith(tapX, tapY, x, y)
                        indicateFlickState()
                        return@Listener true
                    }
                    MotionEvent.ACTION_UP -> {
                        indicateFlickState()
                        multiTapManager.resetTapCount()
                        return@Listener false
                    }
                }
                return@Listener true
            }
        }
    }

    override fun onStartInputView(info: EditorInfo?, restarting: Boolean) {
        super.onStartInputView(info, restarting)
        editorInfo = info ?: EditorInfo()
        flickFactory = FlickFactory(resourceServer.thresholdX1.current,
                                    resourceServer.thresholdX2.current,
                                    resourceServer.thresholdY1.current,
                                    resourceServer.thresholdY2.current)
        multiTapManager = MultiTapManager(resourceServer.canCancelFlick.current,
                                          resourceServer.canCancelInput.current)
        indicatorFactory = IndicatorFactory(resourceServer.supplyIndicatorBackground())
        setInputView(onCreateInputView())
        indicateFlickState()
    }

    override fun onPress(primaryCode: Int) {
        onPressCode = primaryCode
        canInput = KeyIndex.isValid(primaryCode)
        indicateFlickState()
    }

    override fun onRelease(primaryCode: Int) {
        resetTapPoint()
        onPressCode = KeyIndex.NOTHING
        canInput = true
        flick = Flick.NONE
        indicateFlickState()
    }

    override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
        if (!canInput) {
            return
        }
        val key = keymap.getKey(onPressCode, flick)
        if (key is ModKeyInfo) {
            modStorage.update(key, isSubMod = false)
            return
        }
        if (key.mods.isNotEmpty()) {
            key.mods.map { modStorage.update(it, isSubMod = true) }
        }
        sendKey(key, modStorage.toSet())
        modStorage.resetUnlessLock()
    }

    private fun sendKey(key: KeyInfo, mods: Set<ModKeyInfo>) {
        if (key === AsciiKeyInfo.ENTER && mods.isEmpty()) {
            val editorAction = editorInfo.actionId.and(
                    EditorInfo.IME_MASK_ACTION or EditorInfo.IME_FLAG_NO_ENTER_ACTION)
            when (editorAction) {
                EditorInfo.IME_ACTION_GO,
                EditorInfo.IME_ACTION_DONE,
                EditorInfo.IME_ACTION_NEXT,
                EditorInfo.IME_ACTION_SEARCH,
                EditorInfo.IME_ACTION_SEND -> {
                    currentInputConnection?.performEditorAction(editorAction)
                    return
                }
            }
        }

        if (key is AsciiKeyInfo && key is AsciiKeyInfo.Modifiable) {
            val ic = currentInputConnection ?: return
            val now = System.currentTimeMillis()
            val modsList: List<Pair<ModKeyInfo, Int>> = mods.fold(listOf(), { list, mod ->
                if (list.isEmpty()) {
                    listOf(Pair(mod, 0))
                } else {
                    list.toMutableList().plus(
                            Pair(mod, list.last().first.meta or list.last().second)
                    ).toList()
                }
            })
            val lastMeta = modsList.lastOrNull()?.let { it.first.meta or it.second } ?: 0
            modsList.map {
                ic.sendKeyEvent(KeyEvent(
                        now, now, KeyEvent.ACTION_DOWN, it.first.code, 0, it.first.meta or it.second))
            }
            listOf(KeyEvent.ACTION_DOWN, KeyEvent.ACTION_UP).map {
                ic.sendKeyEvent(KeyEvent(
                        now, now, it, key.code, 0, lastMeta))
            }
            modsList.reversed().map {
                ic.sendKeyEvent(KeyEvent(
                        now, now, KeyEvent.ACTION_UP, it.first.code, 0, it.second))
            }
            modStorage.resetUnlessLock()
            return
        }

        if (key is AsciiKeyInfo.CharKey && key !is AsciiKeyInfo.Modifiable) {
            super.sendKeyChar(key.char)
            modStorage.resetUnlessLock()
            return
        }

        if (key === TriggerKeyInfo.KEYBOARD_LAYOUT) {
            isRight = !isRight
            setInputView(onCreateInputView())
            return
        }
    }

    private fun indicateFlickState() {
        val key = keyboardView.keyboard.keys.first { it.codes[0] == KeyIndex.INDICATOR } ?: return
        indicatorFactory.also {
            it.enable = canInput
            it.isDuringInput = lastAction != MotionEvent.ACTION_UP
            it.left = key.x
            it.right = key.x + key.width
            it.top = key.y
            it.bottom = key.y + key.height
            it.direction = flick.direction
            it.currentDistance = flick.distance
            it.maxDistance = keymap.getMaxDistance(onPressCode, flick.direction)
        }
        keyboardView.apply {
            indicator = indicatorFactory.makeIndicator()
            invalidateKey(KeyIndex.INDICATOR)
        }
    }

    private fun resetTapPoint() {
        tapX = -1
        tapY = -1
    }

    override fun swipeLeft() {}

    override fun swipeRight() {}

    override fun swipeUp() {}

    override fun swipeDown() {}

    override fun onText(text: CharSequence?) {}
}
