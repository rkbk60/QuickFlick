package com.rkbk60.quickflick

/**
 * provide code @ res/xml/keyboard.xml - keyboard.keys
 */
object KeyNumbers {

    // check matching with keyboard.xml and integers.xml
    val INDICATOR = 0

    val ARROW = 3
    val META_ALT = 9
    val CTRL_ALT = 16

    val LIST_VALID = (1 .. 28).toList()

    val LIST_LEFT_FUNCTIONS = listOf(1, 8, 15, 22)
    val LIST_RIGHT_FUNCTIONS = listOf(7, 14, 21, 28)
    val LIST_FUNCTIONS = mutableListOf<Int>()
            .plus(LIST_LEFT_FUNCTIONS)
            .plus(LIST_RIGHT_FUNCTIONS)
            .sorted()
            .toList()

    val LIST_NEXT_TO_LEFT_FUNCTIONS = mutableListOf<Int>()
            .plus(LIST_LEFT_FUNCTIONS)
            .map { it + 1 }
            .toList()
    val LIST_NEXT_TO_RIGHT_FUNCTIONS = mutableListOf<Int>()
            .plus(LIST_RIGHT_FUNCTIONS)
            .map { it - 1 }
            .toList()
    val LIST_NEXT_TO_FUNCTIONS = mutableListOf<Int>()
            .plus(LIST_NEXT_TO_LEFT_FUNCTIONS)
            .plus(LIST_NEXT_TO_RIGHT_FUNCTIONS)
            .sorted()
            .toList()

    val LIST_LAST_OF_ROW by lazy {
        val list = LIST_RIGHT_FUNCTIONS.toMutableList()
        list.add(INDICATOR)
        return@lazy list.toList()
    }

}