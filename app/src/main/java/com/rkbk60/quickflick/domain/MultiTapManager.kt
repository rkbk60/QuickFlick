package com.rkbk60.quickflick.domain

/**
 * simple class to memory multi tap count and preferences.
 * @param cancelFlick can or cannot cancel flick with multi tap
 * @param cancelInput can or cannot cancel input only with multi tap twice
 */
class MultiTapManager(private val cancelFlick: Boolean, private val cancelInput: Boolean) {
    /**
     * Counter to decide to effect.
     */
    private var tapCount = 0

    /**
     * Add tap count.
     */
    fun addTapCount() {
        tapCount++
    }

    /**
     * Reset tap count.
     */
    fun resetTapCount() {
        tapCount = 0
    }

    /**
     * Check whether can cancel current flick.
     * @return if can cancel now, then true.
     */
    fun canCancelFlick(): Boolean =
            cancelFlick && tapCount >= 1

    /**
     * Check whether can cancel current input.
     * @return if can cancel now, then true.
     */
    fun canCancelInput(): Boolean =
            cancelInput && tapCount >= 2

}
