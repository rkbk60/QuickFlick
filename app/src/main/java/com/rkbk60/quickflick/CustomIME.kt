package com.rkbk60.quickflick

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.inputmethodservice.InputMethodService
import android.inputmethodservice.KeyboardView
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import com.rkbk60.quickflick.domain.FlickFactory
import com.rkbk60.quickflick.domain.KeyInfoStorage
import com.rkbk60.quickflick.domain.KeymapController
import com.rkbk60.quickflick.model.*

/**
 * main input method service
 */

class CustomIME : InputMethodService(), KeyboardView.OnKeyboardActionListener {
    private lateinit var keyboardView: CustomKeyboardView
    private val keyboardController by lazy { KeyboardController(this) }

    private val keymap = KeymapController()
    private val keyInfoStorage = KeyInfoStorage()
    private val flickFactory = FlickFactory(10, 10, 10, 10)

    // current keyboard information
    private var isRight = true
    private var isPortrait
        get()  = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        set(_) = Unit
    private var heightLevel
        get()  = 2
        set(_) = Unit

    // current action information
    private var tapX = -1
    private var tapY = -1
    private var lastAction = 0
    private var onPressCode = KeyIndex.NOTHING
    private var flick = flickFactory.makeEmptyFlick()

    private var editorInfo: EditorInfo? = null

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
                        onPressCode = KeyIndex.NOTHING
                        keyInfoStorage.resetUnlessLock()
                        tapX = x
                        tapY = y
                        return@Listener false
                    }
                    MotionEvent.ACTION_UP -> {
                        isRight = !isRight
                        setInputView(onCreateInputView())
                        return@Listener false
                    }
                }
                return@Listener true
            }
        }
    }

    override fun onStartInputView(info: EditorInfo?, restarting: Boolean) {
        super.onStartInputView(info, restarting)
        editorInfo = info
        setInputView(onCreateInputView())
    }

    override fun onPress(primaryCode: Int) {
        onPressCode = primaryCode
    }

    override fun onRelease(primaryCode: Int) {
    }

    override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
    }

    private fun sendKey(key: KeyInfo, mods: Set<ModKeyInfo>) {
    }

    private fun resetTapState() {
        tapX = -1
        tapY = -1
        onPressCode = KeyIndex.NOTHING
        flick = flickFactory.makeEmptyFlick()
    }

    override fun swipeLeft() {}

    override fun swipeRight() {}

    override fun swipeUp() {}

    override fun swipeDown() {}

    override fun onText(text: CharSequence?) {}
}
