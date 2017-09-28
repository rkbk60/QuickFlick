package com.rkbk60.quickflick

import android.view.KeyCharacterMap

/**
 * Convert Char to KeyEvent.KEYCODE_char
 */
class CharConverter {
    private var charArray = CharArray(1)
    private val keyCharacterMap = KeyCharacterMap.load(KeyCharacterMap.VIRTUAL_KEYBOARD)

    fun convert(char: Char): Int {
        charArray[0] = char
        return keyCharacterMap.getEvents(charArray)[0].keyCode
    }
}
