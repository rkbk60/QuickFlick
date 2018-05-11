package com.rkbk60.quickflick.domain

import com.rkbk60.quickflick.model.AsciiKeyInfo
import com.rkbk60.quickflick.model.KeyInfo
import com.rkbk60.quickflick.model.ModKeyInfo
import java.util.*

private typealias LEFT  = AsciiKeyInfo.LEFT
private typealias RIGHT = AsciiKeyInfo.RIGHT
private typealias UP    = AsciiKeyInfo.UP
private typealias DOWN  = AsciiKeyInfo.DOWN

/**
 * Arrow key object, managing modes and repeating input.
 * @param sendKey action to send KeyEvent
 */
class ArrowKey(private val sendKey: (KeyInfo, Set<ModKeyInfo>) -> Unit) {
    /**
     * Enum class to show current arrow key modes.
     */
    enum class Mode { DEFAULT, PAGE_MOVE }

    /**
     * TimerTask for repeating input.
     * @param sendKey action to send KeyEvent
     */
    private inner class ArrowKeyTimerTask
            (private val sendKey: (KeyInfo, Set<ModKeyInfo>) -> Unit): TimerTask() {
        /**
         * KeyInfo that will be input by [run].
         */
        var key: KeyInfo = KeyInfo.NULL

        /**
         * Current enabled modifier keys.
         */
        val mods = mutableSetOf<ModKeyInfo>()

        /**
         * Flag to show whether task is running.
         */
        var running = false
            private set

        /**
         * Runs repeating key input.
         */
        override fun run() {
            sendKey(key, mods)
            running = true
        }
    }

    /**
     * Current arrow key mode.
     */
    var mode = Mode.DEFAULT
        private set

    /**
     * Timer object to repeating input.
     */
    private var timer = Timer()

    /**
     * Custom TimerTask object to repeating input.
     */
    private var task = ArrowKeyTimerTask(sendKey)

    /**
     * Interval time on repeating input.
     */
    private val repeatTime = 50L

    /**
     * Delay time from first input to next input.
     */
    private val delayTime = 500L

    /**
     * Flag to show whether task is running.
     */
    var running = false
        get() = task.running
        private set

    /**
     * Toggles [mode].
     */
    fun toggleMode() {
        mode = when (mode) {
            Mode.DEFAULT -> Mode.PAGE_MOVE
            Mode.PAGE_MOVE -> Mode.DEFAULT
        }
    }

    /**
     * Resets to primary mode, and stops repeating input.
     */
    fun reset() {
        mode = Mode.DEFAULT
        stopInput()
    }

    /**
     * Starts repeating input after input KeyInfo once.
     * To run this, it needs that [mode] is Mode.Default and [key] is AsciiKeyInfo showing directions.
     * If you call when running repeating input, this method do nothing.
     * You want to change key after call this, use [changeKeyInfo].
     * @param key  LEFT/RIGHT/UP/DOWN AsciiKeyInfo.
     *             Other AsciiKeyInfo also can assign, but they will be ignored.
     * @param mods list of current enabled modifier keys
     */
    fun startInput(key: KeyInfo, mods: Set<ModKeyInfo>) {
        if (task.running || key !in setOf(LEFT, RIGHT, UP, DOWN)) {
            return
        }
        sendKey(key, mods)
        if (mode == Mode.DEFAULT) {
            task.key = key
            task.mods.addAll(mods.filter { it.lockable })
            timer.scheduleAtFixedRate(task, delayTime, repeatTime)
        }
    }

    /**
     * Changes KeyInfo used repeating input.
     * This function can change key only when run repeating input.
     * @param key direction AsciiKeyInfo
     */
    fun changeKeyInfo(key: KeyInfo) {
        if (task.running) task.key = key
    }

    /**
     * Stops repeating input.
     * This method also can call when not run repeating input, but this will do nothing.
     */
    fun stopInput() {
        if (task.running) {
            timer.cancel()
        }
        timer = Timer()
        task = ArrowKeyTimerTask(sendKey)
    }

    @Suppress("ProtectedInFinal", "unused")
    protected fun finalize() {
        stopInput()
    }
}
