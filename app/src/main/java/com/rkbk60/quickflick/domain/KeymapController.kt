package com.rkbk60.quickflick.domain

import com.rkbk60.quickflick.model.*

/**
 * Factory and controller for Keymap.
 */
class KeymapController {
    /**
     * Target keymap to manage by this.
     */
    private val keymap = Keymap()

    /**
     * Defines initial keymap.
     */
    fun createKeymap() {
        generateCharKeymap()
        generateUnCharKeymap()
        generateArrowKeymap(ArrowKey.Mode.DEFAULT)
        generateFnKeymap()
    }

    /**
     * Updates arrow key keymap with current mode.
     */
    fun updateArrowKeymap(mode: ArrowKey.Mode) {
        generateArrowKeymap(mode)
    }

    /**
     * Finds stored KeyInfo from keymap with index and Flick.
     * When keymap have not defined or can't find matching KeyInfo,
     * this method will return KeyInfo.NULL.
     * @param index index code of keymap (equal to Keyboard.Key.codes)
     * @param flick flick object that has current flick information
     * @return KeyInfo matching [index] and [flick], or KeyInfo.NULL
     * @see Keymap.getKey
     */
    fun getKey(index: Int, flick: Flick): KeyInfo =
            keymap.getKey(index, flick)

    /**
     * Returns max distance that can find KeyInfo in keymap matching parameters.
     * If there aren't matching keymap or KeyInfo, it will be return 0.
     * @param index index code of keymap (equal to Keyboard.Key.codes)
     * @param direction index direction
     * @return max distance
     * @see Keymap.getMaxDistance
     */
    fun getMaxDistance(index: Int, direction: Flick.Direction): Int =
            keymap.getMaxDistance(index, direction)

    /**
     * Returns max distance that can find any KeyInfo in all keymap.
     * @return max distance
     */
    fun getMaxDistance(): Int =
            keymap.getMaxDistance()

    /**
     * Defines AsciiKeyInfo.CharKey to keymap.
     */
    private fun generateCharKeymap() {
        keymap.apply {
            clear(KeyIndex.D1)
            put(KeyIndex.D1, Flick.Direction.NONE,  AsciiKeyInfo.NUM_0)
            put(KeyIndex.D1, Flick.Direction.DOWN,  AsciiKeyInfo.EXCLAMATION)
            put(KeyIndex.D1, Flick.Direction.LEFT,  AsciiKeyInfo.AT)
            put(KeyIndex.D1, Flick.Direction.UP,    AsciiKeyInfo.POUND)
            put(KeyIndex.D1, Flick.Direction.RIGHT, AsciiKeyInfo.DOLLAR)
            clear(KeyIndex.E1)
            put(KeyIndex.E1, Flick.Direction.NONE,  AsciiKeyInfo.SPACE)
            put(KeyIndex.E1, Flick.Direction.DOWN,  AsciiKeyInfo.PERCENT)
            put(KeyIndex.E1, Flick.Direction.LEFT,  AsciiKeyInfo.CARET)
            put(KeyIndex.E1, Flick.Direction.UP,    AsciiKeyInfo.AMPERSAND)
            put(KeyIndex.E1, Flick.Direction.RIGHT, AsciiKeyInfo.STAR)
            clear(KeyIndex.C2)
            put(KeyIndex.C2, Flick.Direction.NONE,  AsciiKeyInfo.NUM_1)
            put(KeyIndex.C2, Flick.Direction.DOWN,  AsciiKeyInfo.PERIOD)
            put(KeyIndex.C2, Flick.Direction.LEFT,  AsciiKeyInfo.COMMA)
            put(KeyIndex.C2, Flick.Direction.UP,    AsciiKeyInfo.QUESTION)
            put(KeyIndex.C2, Flick.Direction.RIGHT, AsciiKeyInfo.SLASH)
            clear(KeyIndex.D2)
            put(KeyIndex.D2, Flick.Direction.NONE,  AsciiKeyInfo.NUM_2)
            put(KeyIndex.D2, Flick.Direction.DOWN,  AsciiKeyInfo.LARGE_A)
            put(KeyIndex.D2, Flick.Direction.DOWN,  AsciiKeyInfo.SMALL_A)
            put(KeyIndex.D2, Flick.Direction.LEFT,  AsciiKeyInfo.LARGE_B)
            put(KeyIndex.D2, Flick.Direction.LEFT,  AsciiKeyInfo.SMALL_B)
            put(KeyIndex.D2, Flick.Direction.UP,    AsciiKeyInfo.LARGE_C)
            put(KeyIndex.D2, Flick.Direction.UP,    AsciiKeyInfo.SMALL_C)
            put(KeyIndex.D2, Flick.Direction.RIGHT, AsciiKeyInfo.UNDERSCORE)
            put(KeyIndex.D2, Flick.Direction.RIGHT, AsciiKeyInfo.MINUS)
            clear(KeyIndex.E2)
            put(KeyIndex.E2, Flick.Direction.NONE,  AsciiKeyInfo.NUM_3)
            put(KeyIndex.E2, Flick.Direction.DOWN,  AsciiKeyInfo.LARGE_D)
            put(KeyIndex.E2, Flick.Direction.DOWN,  AsciiKeyInfo.SMALL_D)
            put(KeyIndex.E2, Flick.Direction.LEFT,  AsciiKeyInfo.LARGE_E)
            put(KeyIndex.E2, Flick.Direction.LEFT,  AsciiKeyInfo.SMALL_E)
            put(KeyIndex.E2, Flick.Direction.UP,    AsciiKeyInfo.LARGE_F)
            put(KeyIndex.E2, Flick.Direction.UP,    AsciiKeyInfo.SMALL_F)
            put(KeyIndex.E2, Flick.Direction.RIGHT, AsciiKeyInfo.PLUS)
            put(KeyIndex.E2, Flick.Direction.RIGHT, AsciiKeyInfo.EQUALS)
            clear(KeyIndex.C3)
            put(KeyIndex.C3, Flick.Direction.NONE,  AsciiKeyInfo.NUM_4)
            put(KeyIndex.C3, Flick.Direction.DOWN,  AsciiKeyInfo.LARGE_G)
            put(KeyIndex.C3, Flick.Direction.DOWN,  AsciiKeyInfo.SMALL_G)
            put(KeyIndex.C3, Flick.Direction.LEFT,  AsciiKeyInfo.LARGE_H)
            put(KeyIndex.C3, Flick.Direction.LEFT,  AsciiKeyInfo.SMALL_H)
            put(KeyIndex.C3, Flick.Direction.UP,    AsciiKeyInfo.LARGE_I)
            put(KeyIndex.C3, Flick.Direction.UP,    AsciiKeyInfo.SMALL_I)
            put(KeyIndex.C3, Flick.Direction.RIGHT, AsciiKeyInfo.COLON)
            put(KeyIndex.C3, Flick.Direction.RIGHT, AsciiKeyInfo.SEMICOLON)
            clear(KeyIndex.D3)
            put(KeyIndex.D3, Flick.Direction.NONE,  AsciiKeyInfo.NUM_5)
            put(KeyIndex.D3, Flick.Direction.DOWN,  AsciiKeyInfo.LARGE_J)
            put(KeyIndex.D3, Flick.Direction.DOWN,  AsciiKeyInfo.SMALL_J)
            put(KeyIndex.D3, Flick.Direction.LEFT,  AsciiKeyInfo.LARGE_K)
            put(KeyIndex.D3, Flick.Direction.LEFT,  AsciiKeyInfo.SMALL_K)
            put(KeyIndex.D3, Flick.Direction.UP,    AsciiKeyInfo.LARGE_L)
            put(KeyIndex.D3, Flick.Direction.UP,    AsciiKeyInfo.SMALL_L)
            put(KeyIndex.D3, Flick.Direction.RIGHT, AsciiKeyInfo.APOSTROPHE)
            put(KeyIndex.D3, Flick.Direction.RIGHT, AsciiKeyInfo.DITTO)
            clear(KeyIndex.E3)
            put(KeyIndex.E3, Flick.Direction.NONE,  AsciiKeyInfo.NUM_6)
            put(KeyIndex.E3, Flick.Direction.DOWN,  AsciiKeyInfo.LARGE_M)
            put(KeyIndex.E3, Flick.Direction.DOWN,  AsciiKeyInfo.SMALL_M)
            put(KeyIndex.E3, Flick.Direction.LEFT,  AsciiKeyInfo.LARGE_N)
            put(KeyIndex.E3, Flick.Direction.LEFT,  AsciiKeyInfo.SMALL_N)
            put(KeyIndex.E3, Flick.Direction.UP,    AsciiKeyInfo.LARGE_O)
            put(KeyIndex.E3, Flick.Direction.UP,    AsciiKeyInfo.SMALL_O)
            put(KeyIndex.E3, Flick.Direction.RIGHT, AsciiKeyInfo.BAR)
            put(KeyIndex.E3, Flick.Direction.RIGHT, AsciiKeyInfo.BACKSLASH)
            clear(KeyIndex.B4)
            put(KeyIndex.B4, Flick.Direction.NONE,  AsciiKeyInfo.BRACKET1_LEFT)
            put(KeyIndex.B4, Flick.Direction.RIGHT, AsciiKeyInfo.BRACKET2_LEFT)
            put(KeyIndex.B4, Flick.Direction.RIGHT, AsciiKeyInfo.BRACKET3_LEFT)
            put(KeyIndex.B4, Flick.Direction.RIGHT, AsciiKeyInfo.BRACKET4_LEFT)
            clear(KeyIndex.C4)
            put(KeyIndex.C4, Flick.Direction.NONE,  AsciiKeyInfo.NUM_7)
            put(KeyIndex.C4, Flick.Direction.DOWN,  AsciiKeyInfo.LARGE_P)
            put(KeyIndex.C4, Flick.Direction.DOWN,  AsciiKeyInfo.SMALL_P)
            put(KeyIndex.C4, Flick.Direction.LEFT,  AsciiKeyInfo.LARGE_Q)
            put(KeyIndex.C4, Flick.Direction.LEFT,  AsciiKeyInfo.SMALL_Q)
            put(KeyIndex.C4, Flick.Direction.UP,    AsciiKeyInfo.LARGE_R)
            put(KeyIndex.C4, Flick.Direction.UP,    AsciiKeyInfo.SMALL_R)
            put(KeyIndex.C4, Flick.Direction.RIGHT, AsciiKeyInfo.LARGE_S)
            put(KeyIndex.C4, Flick.Direction.RIGHT, AsciiKeyInfo.SMALL_S)
            clear(KeyIndex.D4)
            put(KeyIndex.D4, Flick.Direction.NONE,  AsciiKeyInfo.NUM_8)
            put(KeyIndex.D4, Flick.Direction.DOWN,  AsciiKeyInfo.LARGE_T)
            put(KeyIndex.D4, Flick.Direction.DOWN,  AsciiKeyInfo.SMALL_T)
            put(KeyIndex.D4, Flick.Direction.LEFT,  AsciiKeyInfo.LARGE_U)
            put(KeyIndex.D4, Flick.Direction.LEFT,  AsciiKeyInfo.SMALL_U)
            put(KeyIndex.D4, Flick.Direction.UP,    AsciiKeyInfo.LARGE_V)
            put(KeyIndex.D4, Flick.Direction.UP,    AsciiKeyInfo.SMALL_V)
            put(KeyIndex.D4, Flick.Direction.RIGHT, AsciiKeyInfo.TILDE)
            put(KeyIndex.D4, Flick.Direction.RIGHT, AsciiKeyInfo.GRAVE)
            clear(KeyIndex.E4)
            put(KeyIndex.E4, Flick.Direction.NONE,  AsciiKeyInfo.NUM_9)
            put(KeyIndex.E4, Flick.Direction.DOWN,  AsciiKeyInfo.LARGE_W)
            put(KeyIndex.E4, Flick.Direction.DOWN,  AsciiKeyInfo.SMALL_W)
            put(KeyIndex.E4, Flick.Direction.LEFT,  AsciiKeyInfo.LARGE_X)
            put(KeyIndex.E4, Flick.Direction.LEFT,  AsciiKeyInfo.SMALL_X)
            put(KeyIndex.E4, Flick.Direction.UP,    AsciiKeyInfo.LARGE_Y)
            put(KeyIndex.E4, Flick.Direction.UP,    AsciiKeyInfo.SMALL_Y)
            put(KeyIndex.E4, Flick.Direction.RIGHT, AsciiKeyInfo.LARGE_Z)
            put(KeyIndex.E4, Flick.Direction.RIGHT, AsciiKeyInfo.SMALL_Z)
            clear(KeyIndex.F4)
            put(KeyIndex.F4, Flick.Direction.NONE,  AsciiKeyInfo.BRACKET1_RIGHT)
            put(KeyIndex.F4, Flick.Direction.LEFT,  AsciiKeyInfo.BRACKET2_RIGHT)
            put(KeyIndex.F4, Flick.Direction.LEFT,  AsciiKeyInfo.BRACKET3_RIGHT)
            put(KeyIndex.F4, Flick.Direction.LEFT,  AsciiKeyInfo.BRACKET4_RIGHT)
        }
    }

    /**
     * Defines AsciiKeyInfo.UnCharKey(except directions, MOVE_HOME/END, PAGE_UP/END) and ModKeyInfo to keymap.
     */
    private fun generateUnCharKeymap() {
        keymap.apply {
            clear(KeyIndex.B1)
            put(KeyIndex.B1, Flick.Direction.NONE,  AsciiKeyInfo.ESCAPE)
            clear(KeyIndex.F1)
            put(KeyIndex.F1, Flick.Direction.NONE,  AsciiKeyInfo.TAB)
            put(KeyIndex.F1, Flick.Direction.LEFT,  AsciiKeyInfo.SHIFT_TAB)
            put(KeyIndex.F1, Flick.Direction.DOWN,  AsciiKeyInfo.ENTER)
            put(KeyIndex.F1, Flick.Direction.UP,    AsciiKeyInfo.SHIFT_ENTER)
            clear(KeyIndex.B2)
            put(KeyIndex.B2, Flick.Direction.NONE,  ModKeyInfo.META)
            put(KeyIndex.B2, Flick.Direction.RIGHT, ModKeyInfo.META_LOCK)
            put(KeyIndex.B2, Flick.Direction.DOWN,  ModKeyInfo.ALT)
            put(KeyIndex.B2, Flick.Direction.UP,    ModKeyInfo.ALT_LOCK)
            clear(KeyIndex.F2)
            put(KeyIndex.F2, Flick.Direction.NONE,  AsciiKeyInfo.FORWARD_DEL)
            clear(KeyIndex.B3)
            put(KeyIndex.B3, Flick.Direction.NONE,  ModKeyInfo.CTRL)
            put(KeyIndex.B3, Flick.Direction.RIGHT, ModKeyInfo.CTRL_LOCK)
            put(KeyIndex.B3, Flick.Direction.DOWN,  ModKeyInfo.ALT)
            put(KeyIndex.B3, Flick.Direction.UP,    ModKeyInfo.ALT_LOCK)
            clear(KeyIndex.F3)
            put(KeyIndex.F3, Flick.Direction.NONE,  AsciiKeyInfo.BACK_DEL)
        }
    }

    /**
     * Defines LEFT, RIGHT, UP, DOWN, MOVE_HOME/END, and PAGE_UP/END KeyInfo to keymap.
     * @param mode current arrow key mode
     */
    private fun generateArrowKeymap(mode: ArrowKey.Mode) {
        keymap.apply {
            clear(KeyIndex.C1)
            put(KeyIndex.C1, Flick.Direction.NONE, TriggerKeyInfo.ARROWKEY_MODE)
            when (mode) {
                ArrowKey.Mode.DEFAULT -> {
                    put(KeyIndex.C1, Flick.Direction.DOWN,  AsciiKeyInfo.DOWN)
                    put(KeyIndex.C1, Flick.Direction.LEFT,  AsciiKeyInfo.LEFT)
                    put(KeyIndex.C1, Flick.Direction.UP,    AsciiKeyInfo.UP)
                    put(KeyIndex.C1, Flick.Direction.RIGHT, AsciiKeyInfo.RIGHT)
                }
                ArrowKey.Mode.PAGE_MOVE -> {
                    put(KeyIndex.C1, Flick.Direction.DOWN,  AsciiKeyInfo.PAGE_DOWN)
                    put(KeyIndex.C1, Flick.Direction.LEFT,  AsciiKeyInfo.MOVE_HOME)
                    put(KeyIndex.C1, Flick.Direction.UP,    AsciiKeyInfo.PAGE_UP)
                    put(KeyIndex.C1, Flick.Direction.RIGHT, AsciiKeyInfo.MOVE_END)
                }
            }
        }
    }

    /**
     * Defines F1-F12 KeyInfo and TriggerKeyInfo to keymap.
     */
    private fun generateFnKeymap() {
        keymap.apply {
            clear(KeyIndex.A1)
            put(KeyIndex.A1, Flick.Direction.RIGHT, AsciiKeyInfo.F1)
            put(KeyIndex.A1, Flick.Direction.RIGHT, AsciiKeyInfo.F2)
            put(KeyIndex.A1, Flick.Direction.RIGHT, AsciiKeyInfo.F3)
            put(KeyIndex.A1, Flick.Direction.UP,    TriggerKeyInfo.KEYBOARD_LAYOUT)
            put(KeyIndex.A1, Flick.Direction.DOWN,  TriggerKeyInfo.KEYBOARD_LAYOUT)
            clear(KeyIndex.G1)
            put(KeyIndex.G1, Flick.Direction.LEFT,  AsciiKeyInfo.F1)
            put(KeyIndex.G1, Flick.Direction.LEFT,  AsciiKeyInfo.F2)
            put(KeyIndex.G1, Flick.Direction.LEFT,  AsciiKeyInfo.F3)
            put(KeyIndex.G1, Flick.Direction.UP,    TriggerKeyInfo.KEYBOARD_LAYOUT)
            put(KeyIndex.G1, Flick.Direction.DOWN,  TriggerKeyInfo.KEYBOARD_LAYOUT)
            clear(KeyIndex.A2)
            put(KeyIndex.A2, Flick.Direction.RIGHT, AsciiKeyInfo.F4)
            put(KeyIndex.A2, Flick.Direction.RIGHT, AsciiKeyInfo.F5)
            put(KeyIndex.A2, Flick.Direction.RIGHT, AsciiKeyInfo.F6)
            put(KeyIndex.A2, Flick.Direction.UP,    TriggerKeyInfo.KEYBOARD_LAYOUT)
            put(KeyIndex.A2, Flick.Direction.DOWN,  TriggerKeyInfo.KEYBOARD_LAYOUT)
            clear(KeyIndex.G2)
            put(KeyIndex.G2, Flick.Direction.LEFT,  AsciiKeyInfo.F4)
            put(KeyIndex.G2, Flick.Direction.LEFT,  AsciiKeyInfo.F5)
            put(KeyIndex.G2, Flick.Direction.LEFT,  AsciiKeyInfo.F6)
            put(KeyIndex.G2, Flick.Direction.UP,    TriggerKeyInfo.KEYBOARD_LAYOUT)
            put(KeyIndex.G2, Flick.Direction.DOWN,  TriggerKeyInfo.KEYBOARD_LAYOUT)
            clear(KeyIndex.A3)
            put(KeyIndex.A3, Flick.Direction.RIGHT, AsciiKeyInfo.F7)
            put(KeyIndex.A3, Flick.Direction.RIGHT, AsciiKeyInfo.F8)
            put(KeyIndex.A3, Flick.Direction.RIGHT, AsciiKeyInfo.F9)
            put(KeyIndex.A3, Flick.Direction.UP,    TriggerKeyInfo.KEYBOARD_LAYOUT)
            put(KeyIndex.A3, Flick.Direction.DOWN,  TriggerKeyInfo.KEYBOARD_LAYOUT)
            clear(KeyIndex.G3)
            put(KeyIndex.G3, Flick.Direction.LEFT,  AsciiKeyInfo.F7)
            put(KeyIndex.G3, Flick.Direction.LEFT,  AsciiKeyInfo.F8)
            put(KeyIndex.G3, Flick.Direction.LEFT,  AsciiKeyInfo.F9)
            put(KeyIndex.G3, Flick.Direction.UP,    TriggerKeyInfo.KEYBOARD_LAYOUT)
            put(KeyIndex.G3, Flick.Direction.DOWN,  TriggerKeyInfo.KEYBOARD_LAYOUT)
            clear(KeyIndex.A4)
            put(KeyIndex.A4, Flick.Direction.RIGHT, AsciiKeyInfo.F10)
            put(KeyIndex.A4, Flick.Direction.RIGHT, AsciiKeyInfo.F11)
            put(KeyIndex.A4, Flick.Direction.RIGHT, AsciiKeyInfo.F12)
            put(KeyIndex.A4, Flick.Direction.UP,    TriggerKeyInfo.KEYBOARD_LAYOUT)
            put(KeyIndex.A4, Flick.Direction.DOWN,  TriggerKeyInfo.KEYBOARD_LAYOUT)
            clear(KeyIndex.G4)
            put(KeyIndex.G4, Flick.Direction.LEFT,  AsciiKeyInfo.F10)
            put(KeyIndex.G4, Flick.Direction.LEFT,  AsciiKeyInfo.F11)
            put(KeyIndex.G4, Flick.Direction.LEFT,  AsciiKeyInfo.F12)
            put(KeyIndex.G4, Flick.Direction.UP,    TriggerKeyInfo.KEYBOARD_LAYOUT)
            put(KeyIndex.G4, Flick.Direction.DOWN,  TriggerKeyInfo.KEYBOARD_LAYOUT)
        }
    }
}
