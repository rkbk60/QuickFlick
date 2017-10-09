package com.rkbk60.quickflick

import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.text.InputType
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection

/**
 * main input method service
 */

class CustomIME : InputMethodService(), KeyboardView.OnKeyboardActionListener {

    private lateinit var keyboardView: CustomKeyboardView
    private lateinit var keyboardManager: KeyboardManager
    private lateinit var keyboard: Keyboard
    private lateinit var keyList: List<Keyboard.Key>

    private var onPressCode = 0
    private var lastActionCode = Integer.MIN_VALUE

    private var tapX = 0
    private var tapY = 0

    private lateinit var flick: Flick

    private lateinit var keymap: Keymap
    private lateinit var keymapController: KeymapController

    // TODO: move to Keyboard or KeyboardManager
    private var metaKey
            = ModKey(KeyEvent.KEYCODE_META_LEFT,  KeyEvent.META_META_ON  or KeyEvent.META_META_LEFT_ON)
    private var ctrlKey
            = ModKey(KeyEvent.KEYCODE_CTRL_LEFT,  KeyEvent.META_CTRL_ON  or KeyEvent.META_CTRL_LEFT_ON)
    private var altKey
            = ModKey(KeyEvent.KEYCODE_ALT_LEFT,   KeyEvent.META_ALT_ON   or KeyEvent.META_ALT_LEFT_ON)
    private var shiftKey
            = ModKey(KeyEvent.KEYCODE_SHIFT_LEFT, KeyEvent.META_SHIFT_ON or KeyEvent.META_SHIFT_LEFT_ON)

    private val modKeyList = listOf(metaKey, ctrlKey, altKey, shiftKey)

    private var multiTapSetting = MultiTapSetting()

    private val arrowKey = ArrowKey(this)

    private val charConverter = CharConverter()

    private var inputTypeClass = 0

    private lateinit var editorInfo: EditorInfo



    override fun onCreateInputView(): View {
        keyboardView = layoutInflater.inflate(R.layout.keyboardview, null) as CustomKeyboardView

        keyboardManager = KeyboardManager(this, keyboardView)
        keyboard = keyboardManager.keyboard

        keymapController = KeymapController()
        keymap = keymapController.createInitialKeymap()

        keyboardView.setKeyboard(keyboard, keymap)
        keyboardView.setOnKeyboardActionListener(this)
        keyboardView.isPreviewEnabled = false


        flick = Flick()

        keyList = keyboard.keys
        keyboardView.setOnTouchListener OnTouchListener@ { _, event ->
            val x = event.x.toInt()
            val y = event.y.toInt()
            val actionCode = event.action and MotionEvent.ACTION_MASK
            lastActionCode = actionCode
            return@OnTouchListener when (actionCode) {
                MotionEvent.ACTION_DOWN -> {
                    val key = keyList.find { it.isInside(x, y) } ?: return@OnTouchListener true
                    if (key.codes[0] in KeyNumbers.LIST_VALID)  {
                        resetTapState(x, y)
                        arrowKey.toggleable = true
                        false
                    } else {
                        onPressCode = SpecialKeyCode.NULL
                        true
                    }
                }
                MotionEvent.ACTION_POINTER_DOWN -> {
                    multiTapSetting.addCount()
                    when {
                        multiTapSetting.canCancelInput() -> onPressCode = SpecialKeyCode.NULL
                        multiTapSetting.canCancelFlick() -> resetTapState(x, y)
                    }
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    if (onPressCode !in KeyNumbers.LIST_VALID) return@OnTouchListener true
                    if ((onPressCode == KeyNumbers.ARROW) and (flick.direction != Flick.Direction.NONE)) {
                        arrowKey.toggleable = false
                        if (arrowKey.isRepeatingMode())
                            arrowKey.execRepeatingInput(keymap.searchKeycode(onPressCode, flick))
                    }
                    if (hasInputLimit()) {
                        val tmpFlick = Flick()
                        tmpFlick.update(tapX, tapY, x, y)
                        val char = keymap.searchKeycode(onPressCode, tmpFlick).toChar()
                        if (isTappingCharKey() and getInputtableChars().any { it == char }) {
                            flick.sync(tmpFlick)
                            keyboardView.indicate(flick, onPressCode)
                        }
                    } else {
                        flick.update(tapX, tapY, x, y)
                        keyboardView.indicate(flick, onPressCode)
                    }
                    true
                }
                MotionEvent.ACTION_UP -> {
                    keyboardView.indicate(Flick(), 0)
                    multiTapSetting.resetCount()
                    arrowKey.stopRepeatingInput()
                    false
                }
                else -> true
            }
        }

        return keyboardView
    }

    override fun onStartInputView(info: EditorInfo, restarting: Boolean) {
        super.onStartInputView(info, restarting)
        editorInfo = info
        keyList = keyboard.keys
        flick.updateDistanceThreshold(keyboardView.context)
        multiTapSetting.updateSettings(keyboardView.context)
        modKeyList.forEach { it.turnOff() }
        inputTypeClass = InputType.TYPE_MASK_CLASS and
                (currentInputEditorInfo?.inputType ?: InputType.TYPE_CLASS_TEXT)
        arrowKey.reset()
    }


    override fun onPress(primaryCode: Int) {
        onPressCode = primaryCode
    }

    override fun onRelease(primaryCode: Int) {}

    override fun onKey(primaryCode: Int, keyCodes: IntArray) {

        val inputConnection = currentInputConnection ?: return
        val code = keymap.searchKeycode(onPressCode, flick)
        var flagTurnModKeyOff = true

        when (code) {
            SpecialKeyCode.NULL
            -> flagTurnModKeyOff = false

            SpecialKeyCode.TOGGLE_ARROWKEY_MODES -> {
                if ((onPressCode != KeyNumbers.ARROW) or !(arrowKey.toggleable)) return
                arrowKey.toggle()
                keyboardManager.updateArrowKeyFace(arrowKey.state)
                keymapController.updateArrowKeymap(arrowKey.state)
            }

            SpecialKeyCode.TOGGLE_ADJUSTMENT ->
                keyboardManager.changeKeyAdjustment()

            SpecialKeyCode.TOGGLE_ADJUSTMENT_ALIGN ->
                keyboardManager.changeKeyAdjustmentAlign()

            SpecialKeyCode.BACKSPACE,
            SpecialKeyCode.DELETE,
            SpecialKeyCode.TAB,
            SpecialKeyCode.ESCAPE,
            SpecialKeyCode.F1,
            SpecialKeyCode.F2,
            SpecialKeyCode.F3,
            SpecialKeyCode.F4,
            SpecialKeyCode.F5,
            SpecialKeyCode.F6,
            SpecialKeyCode.F7,
            SpecialKeyCode.F8,
            SpecialKeyCode.F9,
            SpecialKeyCode.F10,
            SpecialKeyCode.F11,
            SpecialKeyCode.F12,
            SpecialKeyCode.HOME,
            SpecialKeyCode.END,
            SpecialKeyCode.PAGE_DOWN,
            SpecialKeyCode.PAGE_UP -> {
                modKeyList.forEach { if (it.isEnabled()) sendModKeyEvent(inputConnection, it, true) }
                sendSpecialKeyEvent(inputConnection, code)
            }

            SpecialKeyCode.ENTER -> {
                val action = editorInfo.imeOptions.and(
                        EditorInfo.IME_MASK_ACTION or EditorInfo.IME_FLAG_NO_ENTER_ACTION)
                when (action) {
                    EditorInfo.IME_ACTION_GO,
                    EditorInfo.IME_ACTION_DONE,
                    EditorInfo.IME_ACTION_NEXT,
                    EditorInfo.IME_ACTION_SEARCH,
                    EditorInfo.IME_ACTION_SEND -> inputConnection.performEditorAction(action)
                    else -> {
                        modKeyList.forEach { if (it.isEnabled()) sendModKeyEvent(inputConnection, it, true) }
                        sendSpecialKeyEvent(inputConnection, SpecialKeyCode.ENTER)
                    }
                }
            }

            SpecialKeyCode.LEFT,
            SpecialKeyCode.RIGHT,
            SpecialKeyCode.DOWN,
            SpecialKeyCode.UP -> {
                if ((lastActionCode == MotionEvent.ACTION_UP) and (arrowKey.isRepeatingMode())) return
                modKeyList.forEach { if (it.isEnabled()) sendModKeyEvent(inputConnection, it, true) }
                sendSpecialKeyEvent(inputConnection, code)
            }

            SpecialKeyCode.SHIFT_TAB -> {
                shiftKey.turnOn()
                modKeyList.forEach { if (it.isEnabled()) sendModKeyEvent(inputConnection, it, true) }
                sendSpecialKeyEvent(inputConnection, SpecialKeyCode.TAB)
            }

            SpecialKeyCode.SHIFT_ENTER -> {
                shiftKey.turnOn()
                modKeyList.forEach { if (it.isEnabled()) sendModKeyEvent(inputConnection, it, true) }
                sendSpecialKeyEvent(inputConnection, SpecialKeyCode.ENTER)
            }

            SpecialKeyCode.META -> {
                metaKey.toggleOnOff()
                flagTurnModKeyOff = false
            }

            SpecialKeyCode.CTRL -> {
                ctrlKey.toggleOnOff()
                flagTurnModKeyOff = false
            }

            SpecialKeyCode.ALT -> {
                altKey.toggleOnOff()
                flagTurnModKeyOff = false
            }

            SpecialKeyCode.META_LOCK -> {
                metaKey.toggleLock()
                flagTurnModKeyOff = false
            }

            SpecialKeyCode.CTRL_LOCK -> {
                ctrlKey.toggleLock()
                flagTurnModKeyOff = false
            }

            SpecialKeyCode.ALT_LOCK -> {
                altKey.toggleLock()
                flagTurnModKeyOff = false
            }

            else -> {
                val char = code.toChar()
                if (modKeyList.any { it.isEnabled() }) {
                    modKeyList.forEach {
                        if (it.isEnabled()) sendModKeyEvent(inputConnection, it, true)
                    }
                    if (char.isUpperCase()) {
                        shiftKey.turnOn()
                        sendModKeyEvent(inputConnection, shiftKey, true)
                        sendCharKeyEvent(inputConnection, char.toLowerCase())
                        sendModKeyEvent(inputConnection, shiftKey, false)
                    } else {
                        sendCharKeyEvent(inputConnection, char)
                    }
                } else {
                    sendKeyChar(char)
                }
            }
        }

        if (flagTurnModKeyOff) modKeyList.forEach {
            if (it.isEnabled()) sendModKeyEvent(inputConnection, it, false)
            it.turnOffUnlessLock()
        }

        keyboardManager.updateMetaAltKeyFace(metaKey.isEnabled(), altKey.isEnabled())
        keyboardManager.updateCtrlAltKeyFace(ctrlKey.isEnabled(), altKey.isEnabled())
    }

    override fun onText(text: CharSequence) {}

    override fun swipeLeft() {}

    override fun swipeRight() {}

    override fun swipeDown() {}

    override fun swipeUp() {}



    private fun hasInputLimit(): Boolean = inputTypeClass > InputType.TYPE_CLASS_TEXT

    private fun getInputtableChars(): List<Char> {
        if (!hasInputLimit()) return emptyList()
        val base = "0123456789-"
        return when (inputTypeClass) {
            InputType.TYPE_CLASS_NUMBER   -> base
            InputType.TYPE_CLASS_PHONE    -> "$base*#"
            InputType.TYPE_CLASS_DATETIME -> "$base/: "
            else -> ""
        }.toCharArray().toList()
    }

    private fun resetTapState(x: Int, y: Int) {
        tapX = x
        tapY = y
        flick.reset()
        keyboardView.indicate(Flick(), 0)
    }

    private fun isTappingCharKey(): Boolean =
            listOf(7, 8, 9, 12, 13, 14, 17, 18, 19).any { it == onPressCode }

    private fun sendModKeyEvent(ic: InputConnection, modKey: ModKey, isDown: Boolean) {
        sendKeyEvent(ic, modKey.action, isDown, true)
    }

    private fun sendCharKeyEvent(ic: InputConnection, char: Char) {
        val keycode = charConverter.convert(char)
        sendKeyEvent(ic, keycode, true,  true)
        sendKeyEvent(ic, keycode, false, true)
    }

    fun sendSpecialKeyEvent(ic: InputConnection, code: Int) {
        val trueCode = SpecialKeyCode.convertToKeyEventCode(code)
        sendKeyEvent(ic, trueCode, true,  true)
        sendKeyEvent(ic, trueCode, false, true)
    }

    private fun sendKeyEvent(ic: InputConnection, code: Int, isDown: Boolean, withMeta: Boolean) {
        val now = System.currentTimeMillis()
        val action = if (isDown) KeyEvent.ACTION_DOWN else KeyEvent.ACTION_UP
        val meta = if (withMeta) getMetaState() else 0
        ic.sendKeyEvent(KeyEvent(now, now, action, code, 0, meta))
    }

    private fun getMetaState(): Int {
        var meta = 0
        modKeyList.forEach { if (it.isEnabled()) meta = meta or it.meta }
        return meta
    }

}
