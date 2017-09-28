package com.rkbk60.quickflick

import android.view.KeyEvent

/**
 * Special keycode definer for keymap
 */

object SpecialKeyCode {

    private val floor = 4095

    // basic code in any keyboard
    val BACKSPACE = KeyEvent.KEYCODE_DEL + floor
    val TAB       = KeyEvent.KEYCODE_TAB + floor
    val ENTER     = KeyEvent.KEYCODE_ENTER + floor
    val META      = KeyEvent.KEYCODE_META_LEFT + floor
    val CTRL      = KeyEvent.KEYCODE_CTRL_LEFT + floor
    val ALT       = KeyEvent.KEYCODE_ALT_LEFT + floor
    val ESCAPE    = KeyEvent.KEYCODE_ESCAPE + floor
    val PAGE_UP   = KeyEvent.KEYCODE_PAGE_UP + floor
    val PAGE_DOWN = KeyEvent.KEYCODE_PAGE_DOWN + floor
    val END       = KeyEvent.KEYCODE_MOVE_END + floor
    val HOME      = KeyEvent.KEYCODE_MOVE_HOME + floor
    val LEFT      = KeyEvent.KEYCODE_DPAD_LEFT + floor
    val RIGHT     = KeyEvent.KEYCODE_DPAD_RIGHT + floor
    val UP        = KeyEvent.KEYCODE_DPAD_UP + floor
    val DOWN      = KeyEvent.KEYCODE_DPAD_DOWN + floor
    val INSERT    = KeyEvent.KEYCODE_INSERT + floor
    val DELETE    = KeyEvent.KEYCODE_FORWARD_DEL + floor
    val FUNCTION  = KeyEvent.KEYCODE_FUNCTION + floor
    val F1        = KeyEvent.KEYCODE_F1 + floor
    val F2        = KeyEvent.KEYCODE_F2 + floor
    val F3        = KeyEvent.KEYCODE_F3 + floor
    val F4        = KeyEvent.KEYCODE_F4 + floor
    val F5        = KeyEvent.KEYCODE_F5 + floor
    val F6        = KeyEvent.KEYCODE_F6 + floor
    val F7        = KeyEvent.KEYCODE_F7 + floor
    val F8        = KeyEvent.KEYCODE_F8 + floor
    val F9        = KeyEvent.KEYCODE_F9 + floor
    val F10       = KeyEvent.KEYCODE_F10 + floor
    val F11       = KeyEvent.KEYCODE_F11 + floor
    val F12       = KeyEvent.KEYCODE_F12 + floor

    // custom code
    private val customFloor = 40950
    val NULL        = 0
    val META_LOCK   = 1 + customFloor
    val CTRL_LOCK   = 2 + customFloor
    val ALT_LOCK    = 3 + customFloor
    val SHIFT_TAB   = 4 + customFloor
    val SHIFT_ENTER = 5 + customFloor

    fun convertToKeyEventCode(code: Int): Int = code - floor

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
        SHIFT_TAB -> "S-Tab"
        SHIFT_ENTER -> "S-Enter"
        else -> ""
    }

}
