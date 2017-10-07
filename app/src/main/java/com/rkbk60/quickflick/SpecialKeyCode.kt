package com.rkbk60.quickflick

import android.view.KeyEvent

/**
 * Special keycode definer for keymap
 */

object SpecialKeyCode {

    private val floor = 1.shl(10)

    // basic code in any keyboard
    val BACKSPACE = KeyEvent.KEYCODE_DEL or floor
    val TAB       = KeyEvent.KEYCODE_TAB or floor
    val ENTER     = KeyEvent.KEYCODE_ENTER or floor
    val META      = KeyEvent.KEYCODE_META_LEFT or floor
    val CTRL      = KeyEvent.KEYCODE_CTRL_LEFT or floor
    val ALT       = KeyEvent.KEYCODE_ALT_LEFT or floor
    val ESCAPE    = KeyEvent.KEYCODE_ESCAPE or floor
    val PAGE_UP   = KeyEvent.KEYCODE_PAGE_UP or floor
    val PAGE_DOWN = KeyEvent.KEYCODE_PAGE_DOWN or floor
    val END       = KeyEvent.KEYCODE_MOVE_END or floor
    val HOME      = KeyEvent.KEYCODE_MOVE_HOME or floor
    val LEFT      = KeyEvent.KEYCODE_DPAD_LEFT or floor
    val RIGHT     = KeyEvent.KEYCODE_DPAD_RIGHT or floor
    val UP        = KeyEvent.KEYCODE_DPAD_UP or floor
    val DOWN      = KeyEvent.KEYCODE_DPAD_DOWN or floor
    val INSERT    = KeyEvent.KEYCODE_INSERT or floor
    val DELETE    = KeyEvent.KEYCODE_FORWARD_DEL or floor
    val FUNCTION  = KeyEvent.KEYCODE_FUNCTION or floor
    val F1        = KeyEvent.KEYCODE_F1 or floor
    val F2        = KeyEvent.KEYCODE_F2 or floor
    val F3        = KeyEvent.KEYCODE_F3 or floor
    val F4        = KeyEvent.KEYCODE_F4 or floor
    val F5        = KeyEvent.KEYCODE_F5 or floor
    val F6        = KeyEvent.KEYCODE_F6 or floor
    val F7        = KeyEvent.KEYCODE_F7 or floor
    val F8        = KeyEvent.KEYCODE_F8 or floor
    val F9        = KeyEvent.KEYCODE_F9 or floor
    val F10       = KeyEvent.KEYCODE_F10 or floor
    val F11       = KeyEvent.KEYCODE_F11 or floor
    val F12       = KeyEvent.KEYCODE_F12 or floor

    // custom code
    private val customFloor = floor.shl(2)
    val NULL                    = 0 or customFloor
    val META_LOCK               = 1 or customFloor
    val CTRL_LOCK               = 2 or customFloor
    val ALT_LOCK                = 3 or customFloor
    val SHIFT_TAB               = 4 or customFloor
    val SHIFT_ENTER             = 5 or customFloor
    val TOGGLE_ADJUSTMENT       = 6 or customFloor
    val TOGGLE_ADJUSTMENT_ALIGN = 7 or customFloor
    val TOGGLE_ARROWKEY_MODES   = 8 or customFloor



    fun convertToKeyEventCode(code: Int): Int =
            code xor if (code and customFloor == customFloor) customFloor else floor

    fun convertToString(code: Int): String = when (convertToKeyEventCode(code)) {
        NULL -> ""
        BACKSPACE -> "BS"
        TAB -> "Tab"
        ENTER -> "Enter"
        META -> "Meta"
        CTRL -> "Ctrl"
        ALT -> "Alt"
        ESCAPE -> "Esc"
        PAGE_UP -> "PUp"
        PAGE_DOWN -> "PDown"
        END -> "End"
        HOME -> "Home"
        LEFT, RIGHT, UP, DOWN -> ""
        INSERT -> "Insert"
        FUNCTION -> "Fn"
        F1 -> "F1"
        F2 -> "F2"
        F3 -> "F3"
        F4 -> "F4"
        F5 -> "F5"
        F6 -> "F6"
        F7 -> "F7"
        F8 -> "F8"
        F9 -> "F9"
        F10 -> "F10"
        F11 -> "F11"
        F12 -> "F12"
        META_LOCK -> "MLock"
        CTRL_LOCK -> "CLock"
        ALT_LOCK -> "ALock"
        SHIFT_TAB -> "STab"
        SHIFT_ENTER -> "SEnter"
        else -> ""
    }

}
