package com.rkbk60.quickflick

import java.util.*

/**
 * Created by s-iwamoto on 9/30/17.
 */
class ArrowKey(private val ime: CustomIME) {

    enum class State { DEFAULT, PAGE_MOVE, REPEATING }
    var state = State.DEFAULT
        private set

    var toggleable = true

    private lateinit var timer: Timer
    private lateinit var task:  ArrowKeyTimerTask
    private var repeatTime = 50L
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

}