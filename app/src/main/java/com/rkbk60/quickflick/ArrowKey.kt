package com.rkbk60.quickflick

/**
 * Created by s-iwamoto on 9/30/17.
 */
class ArrowKey {

    enum class State { DEFAULT, PAGE_MOVE, REPEATING }
    var state = State.DEFAULT
        private set

    var toggleable = true

    fun toggle() {
        if (!toggleable) return
        state = when (state) {
            State.DEFAULT -> State.PAGE_MOVE
            State.PAGE_MOVE -> State.REPEATING
            State.REPEATING -> State.DEFAULT
        }
    }

    fun isArrowMode(): Boolean = state != State.PAGE_MOVE

    fun isPageMoveMode(): Boolean = state == State.PAGE_MOVE

    fun isDefault(): Boolean = state == State.DEFAULT

    fun isRepeatingMode(): Boolean = state == State.REPEATING

}