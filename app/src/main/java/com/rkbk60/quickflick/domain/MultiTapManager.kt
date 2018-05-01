package com.rkbk60.quickflick.domain

/**
 * simple class to memory multi tap count and preferences.
 * @param server object which can get current multi tap settings
 */
class MultiTapManager(server: PreferenceServer) {
    /**
     * Data set containing multi tap settings.
     * @param cancelFlick can or cannot cancel flick with multi tap
     * @param cancelInput can or cannot cancel input only with multi tap twice
     */
    data class Preference(val cancelFlick: Boolean, val cancelInput: Boolean)

    /**
     * Interface to get MultiTapManager.Preference.
     */
    interface PreferenceServer {
        fun getMultiTapPref(): Preference
    }

    /**
     * Counter to decide to effect.
     */
    private var tapCount = 0

    private var cancelFlick = false
    private var cancelInput = false

    init {
        server.getMultiTapPref().also {
            cancelFlick = it.cancelFlick
            cancelInput = it.cancelInput
        }
    }

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
