package com.rkbk60.quickflick


/**
 * Created by s-iwamoto on 9/30/17.
 */
object KeyNumbers {

    // check matching with keyboard.xml and integers.xml
    val INDICATOR = 0

    val ARROW = 2
    val META_ALT = 6
    val CTRL_ALT = 11

    val LEFT_SPACER = -1
    val LEFT_VIEWER = -2
    val LEFT_SWITCHER = -3
    val RIGHT_SPACER = -4
    val RIGHT_VIEWER = -5
    val RIGHT_SWITCHER = -6

    val LIST_INPUTTABLE = (1 .. 20).toList()
    val LIST_LOCATED_SIDE = listOf(1, 5, 6, 10, 11, 15, 16, 20)
    val LIST_LOCATED_RIGHT_EDGE =
            listOf(INDICATOR, RIGHT_SPACER, RIGHT_VIEWER, RIGHT_SWITCHER)
}