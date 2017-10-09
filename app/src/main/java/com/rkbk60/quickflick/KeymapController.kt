package com.rkbk60.quickflick

/**
 * 
 */

internal class KeymapController {

    private val keymap = Keymap()

    private val none  = Flick.Direction.NONE
    private val left  = Flick.Direction.LEFT
    private val up    = Flick.Direction.UP
    private val down  = Flick.Direction.DOWN
    private val right = Flick.Direction.RIGHT

    fun createInitialKeymap(): Keymap {
        generateBasicKeymap()
        generateArrowKeymap()
        generateCharKeymap()
        generateFnKeymap()
        return keymap
    }

    fun updateArrowKeymap(state: ArrowKey.State) {
        generateArrowKeymap(state)
    }

    private fun generateBasicKeymap() {
        keymap.apply {
            clear(2)
            put(2, SpecialKeyCode.ESCAPE, none)
            clear(6)
            put(6, SpecialKeyCode.TAB, none)
            put(6, SpecialKeyCode.SHIFT_TAB, left)
            put(6, SpecialKeyCode.ENTER, down)
            put(6, SpecialKeyCode.SHIFT_ENTER, up)
            clear(9)
            put(9, SpecialKeyCode.META, none)
            put(9, SpecialKeyCode.META_LOCK, right)
            put(9, SpecialKeyCode.ALT, down)
            put(9, SpecialKeyCode.ALT_LOCK, up)
            clear(13)
            put(13, SpecialKeyCode.DELETE, none)
            clear(16)
            put(16, SpecialKeyCode.CTRL, none)
            put(16, SpecialKeyCode.CTRL_LOCK, right)
            put(16, SpecialKeyCode.ALT, down)
            put(16, SpecialKeyCode.ALT_LOCK, up)
            clear(20)
            put(20, SpecialKeyCode.BACKSPACE, none)
        }
    }

    private fun generateArrowKeymap(state: ArrowKey.State = ArrowKey.State.DEFAULT) {
        keymap.apply {
            clear(3)
            put(3, SpecialKeyCode.TOGGLE_ARROWKEY_MODES, none)
            when (state) {
               ArrowKey.State.PAGE_MOVE -> {
                   put(3, SpecialKeyCode.PAGE_DOWN, down)
                   put(3, SpecialKeyCode.HOME, left)
                   put(3, SpecialKeyCode.PAGE_UP, up)
                   put(3, SpecialKeyCode.END, right)
               }
               else -> {
                   put(3, SpecialKeyCode.DOWN, down)
                   put(3, SpecialKeyCode.LEFT, left)
                   put(3, SpecialKeyCode.UP, up)
                   put(3, SpecialKeyCode.RIGHT, right)
               }
            }
        }
    }

    private fun generateCharKeymap() {
        keymap.apply {
            clear(4)
            put(4, '0', none)
            put(4, '!', down)
            put(4, '@', left)
            put(4, '#', up)
            put(4, '$', right)
            clear(5)
            put(5, ' ', none)
            put(5, '%', down)
            put(5, '^', left)
            put(5, '&', up)
            put(5, '*', right)
            clear(10)
            put(10, '1', none)
            put(10, '.', down)
            put(10, ',', left)
            put(10, '?', up)
            put(10, '/', right)
            clear(11)
            put(11, '2', none)
            put(11, 'A', down)
            put(11, 'a', down)
            put(11, 'B', left)
            put(11, 'b', left)
            put(11, 'C', up)
            put(11, 'c', up)
            put(11, '_', right)
            put(11, '-', right)
            clear(12)
            put(12, '3', none)
            put(12, 'D', down)
            put(12, 'd', down)
            put(12, 'E', left)
            put(12, 'e', left)
            put(12, 'F', up)
            put(12, 'f', up)
            put(12, '+', right)
            put(12, '=', right)
            clear(17)
            put(17, '4', none)
            put(17, 'G', down)
            put(17, 'g', down)
            put(17, 'H', left)
            put(17, 'h', left)
            put(17, 'I', up)
            put(17, 'i', up)
            put(17, ':', right)
            put(17, ';', right)
            clear(18)
            put(18, '5', none)
            put(18, 'J', down)
            put(18, 'j', down)
            put(18, 'K', left)
            put(18, 'k', left)
            put(18, 'L', up)
            put(18, 'l', up)
            put(18, '\'', right)
            put(18, '"', right)
            clear(19)
            put(19, '6', none)
            put(19, 'M', down)
            put(19, 'm', down)
            put(19, 'N', left)
            put(19, 'n', left)
            put(19, 'O', up)
            put(19, 'o', up)
            put(19, '|', right)
            put(19, '\\', right)
            clear(23)
            put(23, '(', none)
            put(23, '{', right)
            put(23, '[', right)
            put(23, '<', right)
            clear(24)
            put(24, '7', none)
            put(24, 'P', down)
            put(24, 'p', down)
            put(24, 'Q', left)
            put(24, 'q', left)
            put(24, 'R', up)
            put(24, 'r', up)
            put(24, 'S', right)
            put(24, 's', right)
            clear(25)
            put(25, '8', none)
            put(25, 'T', down)
            put(25, 't', down)
            put(25, 'U', left)
            put(25, 'u', left)
            put(25, 'V', up)
            put(25, 'v', up)
            put(25, '~', right)
            put(25, '`', right)
            clear(26)
            put(26, '9', none)
            put(26, 'W', down)
            put(26, 'w', down)
            put(26, 'X', left)
            put(26, 'x', left)
            put(26, 'Y', up)
            put(26, 'y', up)
            put(26, 'Z', right)
            put(26, 'z', right)
            clear(27)
            put(27, ')', none)
            put(27, '}', left)
            put(27, ']', left)
            put(27, '>', left)
        }
    }

    private fun generateFnKeymap() {
        keymap.apply {
            clear(1)
            put(1, SpecialKeyCode.F1, right)
            put(1, SpecialKeyCode.F2, right)
            put(1, SpecialKeyCode.F3, right)
            put(1, SpecialKeyCode.TOGGLE_ADJUSTMENT_ALIGN, up)
            put(1, SpecialKeyCode.TOGGLE_ADJUSTMENT_ALIGN, down)
            clear(7)
            put(7, SpecialKeyCode.F1, left)
            put(7, SpecialKeyCode.F2, left)
            put(7, SpecialKeyCode.F3, left)
            put(7, SpecialKeyCode.TOGGLE_ADJUSTMENT_ALIGN, up)
            put(7, SpecialKeyCode.TOGGLE_ADJUSTMENT_ALIGN, down)
            clear(8)
            put(8, SpecialKeyCode.F4, right)
            put(8, SpecialKeyCode.F5, right)
            put(8, SpecialKeyCode.F6, right)
            put(8, SpecialKeyCode.TOGGLE_ADJUSTMENT_ALIGN, up)
            put(8, SpecialKeyCode.TOGGLE_ADJUSTMENT_ALIGN, down)
            clear(14)
            put(14, SpecialKeyCode.F4, left)
            put(14, SpecialKeyCode.F5, left)
            put(14, SpecialKeyCode.F6, left)
            put(14, SpecialKeyCode.TOGGLE_ADJUSTMENT_ALIGN, up)
            put(14, SpecialKeyCode.TOGGLE_ADJUSTMENT_ALIGN, down)
            clear(15)
            put(15, SpecialKeyCode.F7, right)
            put(15, SpecialKeyCode.F8, right)
            put(15, SpecialKeyCode.F9, right)
            put(15, SpecialKeyCode.TOGGLE_ADJUSTMENT_ALIGN, up)
            put(15, SpecialKeyCode.TOGGLE_ADJUSTMENT_ALIGN, down)
            clear(21)
            put(21, SpecialKeyCode.F7, left)
            put(21, SpecialKeyCode.F8, left)
            put(21, SpecialKeyCode.F9, left)
            put(21, SpecialKeyCode.TOGGLE_ADJUSTMENT_ALIGN, up)
            put(21, SpecialKeyCode.TOGGLE_ADJUSTMENT_ALIGN, down)
            clear(22)
            put(22, SpecialKeyCode.F10, right)
            put(22, SpecialKeyCode.F11, right)
            put(22, SpecialKeyCode.F12, right)
            put(22, SpecialKeyCode.TOGGLE_ADJUSTMENT_ALIGN, up)
            put(22, SpecialKeyCode.TOGGLE_ADJUSTMENT_ALIGN, down)
            clear(28)
            put(28, SpecialKeyCode.F10, left)
            put(28, SpecialKeyCode.F11, left)
            put(28, SpecialKeyCode.F12, left)
            put(28, SpecialKeyCode.TOGGLE_ADJUSTMENT_ALIGN, up)
            put(28, SpecialKeyCode.TOGGLE_ADJUSTMENT_ALIGN, down)
        }
    }

}
