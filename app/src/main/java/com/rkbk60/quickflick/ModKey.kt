package com.rkbk60.quickflick

/**
 * Modifier key Object.
 * use in CustomIME class.
 */

class ModKey (val action: Int, val meta: Int) {
    enum class State { OFF, ON, LOCK }

    private var state: State = State.OFF

    fun isEnabled(): Boolean = state != State.OFF

    fun toggleOnOff() {
        state = if (isEnabled()) State.OFF else State.ON
    }

    fun toggleLock() {
        state = if (state == State.LOCK) State.OFF else State.LOCK
    }

    fun turnOff() {
        state = State.OFF
    }

    fun turnOn() {
        if (state == State.OFF) state = State.ON
    }

    fun turnOffUnlessLock() {
        if (state != State.LOCK) state = State.OFF
    }

    override fun toString(): String = when (state) {
        State.OFF  -> "StateOFF"
        State.ON   -> "StateON"
        State.LOCK -> "StateLock"
    }
}
