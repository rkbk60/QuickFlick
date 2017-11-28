package com.rkbk60.quickflick

import android.content.Context
import android.preference.PreferenceManager

/**
 * simple class to memory multi tap count and preferences
 */

class MultiTapSetting {
    private var tapCount = 0

    private var cancelFlick = false
    private var cancelInput = false

    fun updateSettings(context: Context) {
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        cancelFlick = pref.getBoolean("cancel_flick", false)
        cancelInput = pref.getBoolean("cancel_input", false)
    }

    fun addCount() {
        tapCount++
    }

    fun resetCount() {
        tapCount = 0
    }

    fun canCancelFlick(): Boolean = cancelFlick && tapCount >= 1

    fun canCancelInput(): Boolean = cancelInput && tapCount == 2
}
