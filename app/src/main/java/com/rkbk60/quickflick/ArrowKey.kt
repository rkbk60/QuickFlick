package com.rkbk60.quickflick

import android.view.ViewConfiguration
import java.util.*

/**
 * Arrow key
 */
class ArrowKey(private val ime: CustomIME) {

    enum class State { DEFAULT, PAGE_MOVE, REPEATING }
    var state = State.DEFAULT
        private set

    var toggleable = true

    private var timer = Timer()
    private var task = ArrowKeyTimerTask(ime)
    private var repeatTime = ViewConfiguration.getKeyRepeatDelay().toLong()
    private var running = false

    fun toggle() {
        if (!toggleable) return
        state = when (state) {
            State.DEFAULT -> State.PAGE_MOVE
            State.PAGE_MOVE -> State.REPEATING
            State.REPEATING -> State.DEFAULT
        }
        toggleable = true
    }

    fun isRepeatingMode(): Boolean = state == State.REPEATING

    fun execRepeatingInput(code: Int) {
        if (!running) {
            timer = Timer()
            task  = ArrowKeyTimerTask(ime)
            task.code = code
            timer.scheduleAtFixedRate(task, 0, repeatTime)
            running = true
        }
    }

    fun stopRepeatingInput() {
        if (running) {
            timer.cancel()
            running = false
        }
    }

    fun reset() {
        stopRepeatingInput()
        state = State.DEFAULT
    }

    inner class ArrowKeyTimerTask(private val ime: CustomIME): TimerTask() {

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

}