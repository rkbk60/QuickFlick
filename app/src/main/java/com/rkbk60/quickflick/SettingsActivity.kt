package com.rkbk60.quickflick

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity

/**
 * Settings
 */

class SettingsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager.beginTransaction()
                .replace(android.R.id.content, SettingsFragment())
                .commit()
    }

    class SettingsFragment:
            PreferenceFragment(),
            SharedPreferences.OnSharedPreferenceChangeListener {

        private val TYPE_THRESHOLD = 0

        private val minimalThreshold = 10

        private data class PreferenceTuple(val keyName: String, val default: Any, val type: Int)
        private lateinit var thresholdX1: PreferenceTuple
        private lateinit var thresholdX2: PreferenceTuple
        private lateinit var thresholdY1: PreferenceTuple
        private lateinit var thresholdY2: PreferenceTuple


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
//            PreferenceManager.getDefaultSharedPreferences(context)?.edit()?.clear()?.commit() // for debug
            addPreferencesFromResource(R.xml.preferences)
            lateinit()
        }

        override fun onResume() {
            super.onResume()
            preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
            updateAllThresholdSummary()
        }

        override fun onPause() {
            super.onPause()
            preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        }

        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
            val target = when (key) {
                thresholdX1.keyName -> thresholdX1
                thresholdX2.keyName -> thresholdX2
                thresholdY1.keyName -> thresholdY1
                thresholdY2.keyName -> thresholdY2
                else -> return
            }
            validateThreshold(sharedPreferences, target)
            updateThresholdSummary(sharedPreferences, target)
        }

        private fun lateinit() {
            thresholdX1 = PreferenceTuple(
                    resources.getString(R.string.preferences_x1),
                    resources.getInteger(R.integer.preferences_x1_default),
                    TYPE_THRESHOLD
            )
            thresholdX2 = PreferenceTuple(
                    resources.getString(R.string.preferences_x2),
                    resources.getInteger(R.integer.preferences_x2_default),
                    TYPE_THRESHOLD
            )
            thresholdY1 = PreferenceTuple(
                    resources.getString(R.string.preferences_y1),
                    resources.getInteger(R.integer.preferences_y1_default),
                    TYPE_THRESHOLD
            )
            thresholdY2 = PreferenceTuple(
                    resources.getString(R.string.preferences_y2),
                    resources.getInteger(R.integer.preferences_y2_default),
                    TYPE_THRESHOLD
            )
        }

        private fun validateThreshold(sharedPreferences: SharedPreferences?, target: PreferenceTuple) {
            val newValue = sharedPreferences?.getString(target.keyName, "0") ?: return
            if (newValue.toInt() < minimalThreshold) {
                preferenceScreen.sharedPreferences.edit()
                        ?.putString(target.keyName, "$minimalThreshold")
                        ?.commit()
                        ?: return
            }
        }

        private fun updateThresholdSummary(sharedPreferences: SharedPreferences?, target: PreferenceTuple) {
            if (target.type != TYPE_THRESHOLD) return
            val default = target.default
            val newValue = sharedPreferences?.getString(target.keyName, default.toString()) ?: return
            findPreference(target.keyName).summary = "$newValue thou (Default:$default)"
        }

        private fun updateAllThresholdSummary() {
            val targets = listOf(thresholdX1, thresholdX2, thresholdY1, thresholdY2)
            for (target in targets) updateThresholdSummary(preferenceScreen.sharedPreferences, target)
        }

    }

}

