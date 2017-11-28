package com.rkbk60.quickflick

import android.view.ViewConfiguration
import java.util.*

/**
 * Arrow key
 */
class ArrowKey(private val ime: CustomIME) {

    enum class State { DEFAULT, PAGE_MOVE }
    var state = State.DEFAULT
        private set

    var toggleable = true

    private var timer = Timer()
    private var task = ArrowKeyTimerTask(ime)
    private var repeatTime = ViewConfiguration.getKeyRepeatDelay().toLong()
    private var running = false
    private var isFirstRun = true

    fun toggle() {
        if (!toggleable) return
        state = when (state) {
            State.DEFAULT -> State.PAGE_MOVE
            State.PAGE_MOVE -> State.DEFAULT
        }
        toggleable = true
    }

    fun isRepeatingMode(): Boolean = state != State.PAGE_MOVE

    fun execRepeatingInput(code: Int) {
        val checkedKeycode = filterKeycode(code)
        if ((task.code == checkedKeycode)) return
        stopRepeatingInput(false)
        if (isFirstRun) {
            ime.apply {
                sendKeycode(checkedKeycode)
                updateAllKeysFace()
            }
            isFirstRun = false
        }
        task.code = checkedKeycode
        timer.scheduleAtFixedRate(task, repeatTime * 20, repeatTime)
        running = true
    }

    fun stopRepeatingInput(resetFlag: Boolean = true) {
        if (running) {
            timer.cancel()
            timer = Timer()
            task = ArrowKeyTimerTask(ime)
            running = false
            if (resetFlag) isFirstRun = true
        }
    }

    fun reset() {
        stopRepeatingInput()
        state = State.DEFAULT
    }

    private fun filterKeycode(code: Int): Int = when (code) {
        SpecialKeyCode.LEFT,
        SpecialKeyCode.RIGHT,
        SpecialKeyCode.DOWN,
        SpecialKeyCode.UP -> code
        else -> SpecialKeyCode.NULL
    }

    inner class ArrowKeyTimerTask(private val ime: CustomIME): TimerTask() {
        var code = SpecialKeyCode.NULL

        override fun run() {
            ime.sendKeycode(code)
        }

    }

}