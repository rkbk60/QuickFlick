package com.rkbk60.quickflick.model

import android.view.KeyEvent

/**
 * Key Info object which has ASCII code except modifier one.
 */
sealed class AsciiKeyInfo : KeyInfo() {
    /**
     * Tags to show what implemented classes can modify.
     * It is equals to that classes which implemented this interface
     * can convert to {@code KeyEvent.KEYCODE}.
     * What one key has corresponding key code means to can construct KeyEvent instance with
     * modifier key, like Ctrl or Alt.
     */
    interface Modifiable {
        /**
         * Value of corresponding key code(KeyEvent.KEYCODE_XXX).
         */
        val code: Int
    }

    /**
     * Character information object.
     */
    abstract class CharKey : AsciiKeyInfo() {
        /**
         * Character that object shows.
         */
        abstract val char: Char
    }

    object NUM_0 : CharKey(), Modifiable {
        override val char = '0'
        override val code = KeyEvent.KEYCODE_0
    }

    object NUM_1 : CharKey(), Modifiable {
        override val char = '1'
        override val code = KeyEvent.KEYCODE_1
    }

    object NUM_2 : CharKey(), Modifiable {
        override val char = '2'
        override val code = KeyEvent.KEYCODE_2
    }

    object NUM_3 : CharKey(), Modifiable {
        override val char = '3'
        override val code = KeyEvent.KEYCODE_3
    }

    object NUM_4 : CharKey(), Modifiable {
        override val char = '4'
        override val code = KeyEvent.KEYCODE_4
    }

    object NUM_5 : CharKey(), Modifiable {
        override val char = '5'
        override val code = KeyEvent.KEYCODE_5
    }

    object NUM_6 : CharKey(), Modifiable {
        override val char = '6'
        override val code = KeyEvent.KEYCODE_6
    }

    object NUM_7 : CharKey(), Modifiable {
        override val char = '7'
        override val code = KeyEvent.KEYCODE_7
    }

    object NUM_8 : CharKey(), Modifiable {
        override val char = '8'
        override val code = KeyEvent.KEYCODE_8
    }

    object NUM_9 : CharKey(), Modifiable {
        override val char = '9'
        override val code = KeyEvent.KEYCODE_9
    }

    object SMALL_A : CharKey(), Modifiable {
        override val char = 'a'
        override val code = KeyEvent.KEYCODE_A
    }

    object LARGE_A : CharKey(), Modifiable {
        override val char = 'A'
        override val code = KeyEvent.KEYCODE_A
        override val mods = listOf(ModKeyInfo.SHIFT)
    }

    object SMALL_B : CharKey(), Modifiable {
        override val char = 'b'
        override val code = KeyEvent.KEYCODE_B
    }

    object LARGE_B : CharKey(), Modifiable {
        override val char = 'B'
        override val code = KeyEvent.KEYCODE_B
        override val mods = listOf(ModKeyInfo.SHIFT)
    }

    object SMALL_C : CharKey(), Modifiable {
        override val char = 'c'
        override val code = KeyEvent.KEYCODE_C
    }

    object LARGE_C : CharKey(), Modifiable {
        override val char = 'C'
        override val code = KeyEvent.KEYCODE_D
        override val mods = listOf(ModKeyInfo.SHIFT)
    }

    object SMALL_D : CharKey(), Modifiable {
        override val char = 'd'
        override val code = KeyEvent.KEYCODE_D
    }

    object LARGE_D : CharKey(), Modifiable {
        override val char = 'D'
        override val code = KeyEvent.KEYCODE_D
        override val mods = listOf(ModKeyInfo.SHIFT)
    }

    object SMALL_E : CharKey(), Modifiable {
        override val char = 'e'
        override val code = KeyEvent.KEYCODE_E
    }

    object LARGE_E : CharKey(), Modifiable {
        override val char = 'E'
        override val code = KeyEvent.KEYCODE_E
        override val mods = listOf(ModKeyInfo.SHIFT)
    }

    object SMALL_F : CharKey(), Modifiable {
        override val char = 'f'
        override val code = KeyEvent.KEYCODE_F
    }

    object LARGE_F : CharKey(), Modifiable {
        override val char = 'F'
        override val code = KeyEvent.KEYCODE_F
        override val mods = listOf(ModKeyInfo.SHIFT)
    }

    object SMALL_G : CharKey(), Modifiable {
        override val char = 'g'
        override val code = KeyEvent.KEYCODE_G
    }

    object LARGE_G : CharKey(), Modifiable {
        override val char = 'G'
        override val code = KeyEvent.KEYCODE_G
        override val mods = listOf(ModKeyInfo.SHIFT)
    }

    object SMALL_H : CharKey(), Modifiable {
        override val char = 'h'
        override val code = KeyEvent.KEYCODE_H
    }

    object LARGE_H : CharKey(), Modifiable {
        override val char = 'H'
        override val code = KeyEvent.KEYCODE_H
        override val mods = listOf(ModKeyInfo.SHIFT)
    }

    object SMALL_I : CharKey(), Modifiable {
        override val char = 'i'
        override val code = KeyEvent.KEYCODE_I
    }

    object LARGE_I : CharKey(), Modifiable {
        override val char = 'I'
        override val code = KeyEvent.KEYCODE_I
        override val mods = listOf(ModKeyInfo.SHIFT)
    }

    object SMALL_J : CharKey(), Modifiable {
        override val char = 'j'
        override val code = KeyEvent.KEYCODE_J
    }

    object LARGE_J : CharKey(), Modifiable {
        override val char = 'J'
        override val code = KeyEvent.KEYCODE_J
        override val mods = listOf(ModKeyInfo.SHIFT)
    }

    object SMALL_K : CharKey(), Modifiable {
        override val char = 'k'
        override val code = KeyEvent.KEYCODE_K
    }

    object LARGE_K : CharKey(), Modifiable {
        override val char = 'K'
        override val code = KeyEvent.KEYCODE_K
        override val mods = listOf(ModKeyInfo.SHIFT)
    }

    object SMALL_L : CharKey(), Modifiable {
        override val char = 'l'
        override val code = KeyEvent.KEYCODE_L
    }

    object LARGE_L : CharKey(), Modifiable {
        override val char = 'L'
        override val code = KeyEvent.KEYCODE_L
        override val mods = listOf(ModKeyInfo.SHIFT)
    }

    object SMALL_M : CharKey(), Modifiable {
        override val char = 'm'
        override val code = KeyEvent.KEYCODE_M
    }

    object LARGE_M : CharKey(), Modifiable {
        override val char = 'M'
        override val code = KeyEvent.KEYCODE_M
        override val mods = listOf(ModKeyInfo.SHIFT)
    }

    object SMALL_N : CharKey(), Modifiable {
        override val char = 'n'
        override val code = KeyEvent.KEYCODE_N
    }

    object LARGE_N : CharKey(), Modifiable {
        override val char = 'N'
        override val code = KeyEvent.KEYCODE_N
        override val mods = listOf(ModKeyInfo.SHIFT)
    }

    object SMALL_O : CharKey(), Modifiable {
        override val char = 'o'
        override val code = KeyEvent.KEYCODE_O
    }

    object LARGE_O : CharKey(), Modifiable {
        override val char = 'O'
        override val code = KeyEvent.KEYCODE_O
        override val mods = listOf(ModKeyInfo.SHIFT)
    }

    object SMALL_P : CharKey(), Modifiable {
        override val char = 'p'
        override val code = KeyEvent.KEYCODE_P
    }

    object LARGE_P : CharKey(), Modifiable {
        override val char = 'P'
        override val code = KeyEvent.KEYCODE_P
        override val mods = listOf(ModKeyInfo.SHIFT)
    }

    object SMALL_Q : CharKey(), Modifiable {
        override val char = 'q'
        override val code = KeyEvent.KEYCODE_Q
    }

    object LARGE_Q : CharKey(), Modifiable {
        override val char = 'Q'
        override val code = KeyEvent.KEYCODE_Q
        override val mods = listOf(ModKeyInfo.SHIFT)
    }

    object SMALL_R : CharKey(), Modifiable {
        override val char = 'r'
        override val code = KeyEvent.KEYCODE_R
    }

    object LARGE_R : CharKey(), Modifiable {
        override val char = 'R'
        override val code = KeyEvent.KEYCODE_R
        override val mods = listOf(ModKeyInfo.SHIFT)
    }

    object SMALL_S : CharKey(), Modifiable {
        override val char = 's'
        override val code = KeyEvent.KEYCODE_S
    }

    object LARGE_S : CharKey(), Modifiable {
        override val char = 'S'
        override val code = KeyEvent.KEYCODE_S
        override val mods = listOf(ModKeyInfo.SHIFT)
    }

    object SMALL_T : CharKey(), Modifiable {
        override val char = 't'
        override val code = KeyEvent.KEYCODE_T
    }

    object LARGE_T : CharKey(), Modifiable {
        override val char = 'T'
        override val code = KeyEvent.KEYCODE_T
        override val mods = listOf(ModKeyInfo.SHIFT)
    }

    object SMALL_U : CharKey(), Modifiable {
        override val char = 'u'
        override val code = KeyEvent.KEYCODE_U
    }

    object LARGE_U : CharKey(), Modifiable {
        override val char = 'U'
        override val code = KeyEvent.KEYCODE_U
        override val mods = listOf(ModKeyInfo.SHIFT)
    }

    object SMALL_V : CharKey(), Modifiable {
        override val char = 'v'
        override val code = KeyEvent.KEYCODE_V
    }

    object LARGE_V : CharKey(), Modifiable {
        override val char = 'V'
        override val code = KeyEvent.KEYCODE_V
        override val mods = listOf(ModKeyInfo.SHIFT)
    }

    object SMALL_W : CharKey(), Modifiable {
        override val char = 'w'
        override val code = KeyEvent.KEYCODE_W
    }

    object LARGE_W : CharKey(), Modifiable {
        override val char = 'W'
        override val code = KeyEvent.KEYCODE_W
        override val mods = listOf(ModKeyInfo.SHIFT)
    }

    object SMALL_X : CharKey(), Modifiable {
        override val char = 'x'
        override val code = KeyEvent.KEYCODE_X
    }

    object LARGE_X : CharKey(), Modifiable {
        override val char = 'X'
        override val code = KeyEvent.KEYCODE_X
        override val mods = listOf(ModKeyInfo.SHIFT)
    }

    object SMALL_Y : CharKey(), Modifiable {
        override val char = 'y'
        override val code = KeyEvent.KEYCODE_Y
    }

    object LARGE_Y : CharKey(), Modifiable {
        override val char = 'Y'
        override val code = KeyEvent.KEYCODE_Y
        override val mods = listOf(ModKeyInfo.SHIFT)
    }

    object SMALL_Z : CharKey(), Modifiable {
        override val char = 'z'
        override val code = KeyEvent.KEYCODE_Z
    }

    object LARGE_Z : CharKey(), Modifiable {
        override val char = 'Z'
        override val code = KeyEvent.KEYCODE_Z
        override val mods = listOf(ModKeyInfo.SHIFT)
    }

    object SPACE : CharKey(), Modifiable {
        override val char = ' '
        override val code = KeyEvent.KEYCODE_SPACE
    }

    object EXCLAMATION : CharKey() {
        override val char = '!'
    }

    object AT : CharKey(), Modifiable {
        override val char = '@'
        override val code = KeyEvent.KEYCODE_AT
    }

    object POUND : CharKey(), Modifiable {
        override val char = '#'
        override val code = KeyEvent.KEYCODE_POUND
    }

    object DOLLAR : CharKey() {
        override val char = '$'
    }

    object PERCENT : CharKey() {
        override val char = '%'
    }

    object CARET : CharKey() {
        override val char = '^'
    }

    object AMPERSAND : CharKey() {
        override val char = '&'
    }

    object STAR : CharKey(), Modifiable {
        override val char = '*'
        override val code = KeyEvent.KEYCODE_STAR
    }

    object PERIOD : CharKey(), Modifiable {
        override val char = '.'
        override val code = KeyEvent.KEYCODE_PERIOD
    }

    object COMMA : CharKey(), Modifiable {
        override val char = ','
        override val code = KeyEvent.KEYCODE_COMMA
    }

    object QUESTION : CharKey() {
        override val char = '?'
    }

    object SLASH : CharKey(), Modifiable {
        override val char = '/'
        override val code = KeyEvent.KEYCODE_SLASH
    }

    object UNDERSCORE : CharKey() {
        override val char = '_'
    }

    object MINUS : CharKey(), Modifiable {
        override val char = '-'
        override val code = KeyEvent.KEYCODE_MINUS
    }

    object PLUS : CharKey(), Modifiable {
        override val char = '+'
        override val code = KeyEvent.KEYCODE_PLUS
    }

    object EQUALS : CharKey(), Modifiable {
        override val char = '='
        override val code = KeyEvent.KEYCODE_EQUALS
    }

    object COLON : CharKey() {
        override val char = ':'
    }

    object SEMICOLON : CharKey(), Modifiable {
        override val char = ';'
        override val code = KeyEvent.KEYCODE_SEMICOLON
    }

    object DITTO : CharKey() {
        override val char = '"'
    }

    object APOSTROPHE : CharKey(), Modifiable {
        override val char = '\''
        override val code = KeyEvent.KEYCODE_APOSTROPHE
    }

    object BAR : CharKey() {
        override val char = '|'
    }

    object BACKSLASH : CharKey(), Modifiable {
        override val char = '\\'
        override val code = KeyEvent.KEYCODE_BACKSLASH
    }

    object TILDE : CharKey() {
        override val char = '~'
    }

    object GRAVE : CharKey(), Modifiable {
        override val char = '`'
        override val code = KeyEvent.KEYCODE_GRAVE
    }

    object BRACKET1_LEFT : CharKey() {
        override val char = '('
    }

    object BRACKET1_RIGHT : CharKey() {
        override val char = ')'
    }

    object BRACKET2_LEFT : CharKey() {
        override val char = '{'
    }

    object BRACKET2_RIGHT : CharKey() {
        override val char = '}'
    }

    object BRACKET3_LEFT : CharKey(), Modifiable {
        override val char = '['
        override val code = KeyEvent.KEYCODE_LEFT_BRACKET
    }

    object BRACKET3_RIGHT : CharKey(), Modifiable {
        override val char = ']'
        override val code = KeyEvent.KEYCODE_RIGHT_BRACKET
    }

    object BRACKET4_LEFT : CharKey() {
        override val char = '<'
    }

    object BRACKET4_RIGHT : CharKey() {
        override val char = '>'
    }

    /**
     * Non character key information object.
     * All these keys has KeyEvent.KEYCODE, so classes extended this
     * have to define [code] equal to KeyEvent.KEYCODE_XXX.
     */
    abstract class UnCharKey : AsciiKeyInfo(), Modifiable

    object ESCAPE : UnCharKey() {
        override val code = KeyEvent.KEYCODE_ESCAPE
    }

    object TAB : UnCharKey() {
        override val code = KeyEvent.KEYCODE_TAB
    }

    object SHIFT_TAB : UnCharKey() {
        override val code = TAB.code
        override val mods = listOf(ModKeyInfo.SHIFT)
    }

    object ENTER : UnCharKey() {
        override val code = KeyEvent.KEYCODE_ENTER
    }

    object SHIFT_ENTER : UnCharKey() {
        override val code = ENTER.code
        override val mods = listOf(ModKeyInfo.SHIFT)
    }

    object FORWARD_DEL : UnCharKey() {
        override val code = KeyEvent.KEYCODE_FORWARD_DEL
    }

    object BACK_DEL : UnCharKey() {
        override val code = KeyEvent.KEYCODE_DEL
    }

    abstract class DirectionKey : UnCharKey()

    object LEFT : DirectionKey() {
        override val code = KeyEvent.KEYCODE_DPAD_LEFT
    }

    object RIGHT : DirectionKey() {
        override val code = KeyEvent.KEYCODE_DPAD_RIGHT
    }

    object UP : DirectionKey() {
        override val code = KeyEvent.KEYCODE_DPAD_UP
    }

    object DOWN : DirectionKey() {
        override val code = KeyEvent.KEYCODE_DPAD_DOWN
    }

    object MOVE_HOME : UnCharKey() {
        override val code = KeyEvent.KEYCODE_MOVE_HOME
    }

    object MOVE_END : UnCharKey() {
        override val code = KeyEvent.KEYCODE_MOVE_END
    }

    object PAGE_UP : UnCharKey() {
        override val code = KeyEvent.KEYCODE_PAGE_UP
    }

    object PAGE_DOWN : UnCharKey() {
        override val code = KeyEvent.KEYCODE_PAGE_DOWN
    }

    object INSERT : UnCharKey() {
        override val code = KeyEvent.KEYCODE_INSERT
    }

    object F1 : UnCharKey() {
        override val code = KeyEvent.KEYCODE_F1
    }

    object F2 : UnCharKey() {
        override val code = KeyEvent.KEYCODE_F2
    }

    object F3 : UnCharKey() {
        override val code = KeyEvent.KEYCODE_F3
    }

    object F4 : UnCharKey() {
        override val code = KeyEvent.KEYCODE_F4
    }

    object F5 : UnCharKey() {
        override val code = KeyEvent.KEYCODE_F5
    }

    object F6 : UnCharKey() {
        override val code = KeyEvent.KEYCODE_F6
    }

    object F7 : UnCharKey() {
        override val code = KeyEvent.KEYCODE_F7
    }

    object F8 : UnCharKey() {
        override val code = KeyEvent.KEYCODE_F8
    }

    object F9 : UnCharKey() {
        override val code = KeyEvent.KEYCODE_F9
    }

    object F10 : UnCharKey() {
        override val code = KeyEvent.KEYCODE_F10
    }

    object F11 : UnCharKey() {
        override val code = KeyEvent.KEYCODE_F11
    }

    object F12 : UnCharKey() {
        override val code = KeyEvent.KEYCODE_F12
    }
}
