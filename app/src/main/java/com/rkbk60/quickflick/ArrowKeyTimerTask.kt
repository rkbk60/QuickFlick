package com.rkbk60.quickflick

import android.view.inputmethod.InputConnection
import java.util.*

/**
 * Created by s-iwamoto on 9/30/17.
 */
class ArrowKeyTimerTask(private val ime: CustomIME): TimerTask() {

    private var ic = ime.currentInputConnection

    var code = SpecialKeyCode.NULL
    set(value) {
        field = when (value) {
            SpecialKeyCode.LEFT,
            SpecialKeyCode.RIGHT,
            SpecialKeyCode.DOWN,
            SpecialKeyCode.UP -> value
            else -> SpecialKeyCode.NULL
        }
    }

    override fun run() {
        if ((ic == null) or (code == SpecialKeyCode.NULL)) return
        ime.sendSpecialKeyEvent(ic, code)
    }

}