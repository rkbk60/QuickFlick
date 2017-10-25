package com.rkbk60.quickflick

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.EditTextPreference
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

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
        private val TYPE_THEME = 1
        private val TYPE_HEIGHT = 2

        private val minimalThreshold = 10

        private data class PreferenceTuple(val keyName: String, val default: Any, val type: Int)
        private lateinit var thresholdX1: PreferenceTuple
        private lateinit var thresholdX2: PreferenceTuple
        private lateinit var thresholdY1: PreferenceTuple
        private lateinit var thresholdY2: PreferenceTuple
        private lateinit var keysHeight: PreferenceTuple
        private lateinit var themeIndicator: PreferenceTuple

        private lateinit var localContext: Context


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
            updateKeysHeightSummary()
            updateThemeSummary()
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
                keysHeight.keyName -> keysHeight
                themeIndicator.keyName -> themeIndicator
                else -> return
            }
            when (target.type) {
                TYPE_THRESHOLD -> {
                    validateThreshold(sharedPreferences, target)
                    updateThresholdSummary(sharedPreferences, target)
                }
                TYPE_HEIGHT -> {
                    validateKeysHeight(sharedPreferences)
                    updateKeysHeightSummary(sharedPreferences)
                }
                TYPE_THEME -> {
                    updateThemeSummary(sharedPreferences)
                }
            }
        }

        private fun lateinit() {
            localContext = activity.applicationContext
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
            keysHeight = PreferenceTuple(
                    resources.getString(R.string.preferences_keys_height),
                    resources.getInteger(R.integer.preferences_keys_height_default),
                    TYPE_HEIGHT
            )
            themeIndicator = PreferenceTuple(
                    resources.getString(R.string.preferences_theme_indicator),
                    resources.getString(R.string.theme_base),
                    TYPE_THEME
            )
        }

        private fun validateThreshold(sharedPreferences: SharedPreferences?, target: PreferenceTuple) {
            val newValue = sharedPreferences?.getString(target.keyName, "") ?: return
            if (newValue.trim() == "") {
                toast("Set default value.")
                preferenceScreen.sharedPreferences.edit()
                        ?.putString(target.keyName, target.default.toString())
                        ?.commit()
                        ?: return
            } else if (newValue.toInt() < minimalThreshold) {
                toast("Minimal threshold is $minimalThreshold thou.")
                preferenceScreen.sharedPreferences.edit()
                        ?.putString(target.keyName, minimalThreshold.toString())
                        ?.commit()
                        ?: return
            }
        }

        private fun validateKeysHeight(sharedPreferences: SharedPreferences?) {
            val newValue = sharedPreferences?.getString(keysHeight.keyName, "") ?: return
            var fix = 0
            val min = 32
            val max = 48
            when {
                (newValue.trim() == "") -> {
                    toast("Set default value.")
                    fix = keysHeight.default.toString().toInt()
                }
                (newValue.toInt() < min) -> {
                    toast("Minimal key height value is ${min}dp.")
                    fix = min
                }
                (newValue.toInt() > max) -> {
                    toast("Maximal key height value is ${max}dp.")
                    fix = max
                }
            }
            if (fix > 0) {
                preferenceScreen.sharedPreferences.edit()
                        .putString(keysHeight.keyName, fix.toString())
                        .commit()
            }
        }

        private fun updateThresholdSummary(sharedPreferences: SharedPreferences?, target: PreferenceTuple) {
            if (target.type != TYPE_THRESHOLD) return
            val preference = sharedPreferences ?: preferenceScreen.sharedPreferences
            val default = target.default
            val newValue = preference.getString(target.keyName, default.toString())
            findPreference(target.keyName).summary = "$newValue thou (Default:$default)"
        }

        private fun updateAllThresholdSummary() {
            val targets = listOf(thresholdX1, thresholdX2, thresholdY1, thresholdY2)
            for (target in targets) updateThresholdSummary(preferenceScreen.sharedPreferences, target)
        }

        private fun updateKeysHeightSummary(sharedPreferences: SharedPreferences? = null) {
            val preference = sharedPreferences ?: preferenceScreen.sharedPreferences
            if (preference.contains(keysHeight.keyName)) {
                val default = keysHeight.default
                val newValue = preference.getString(keysHeight.keyName, default.toString())
                findPreference(keysHeight.keyName).summary = "${newValue}dp (Default:$default)"
            }
        }

        private fun updateThemeSummary(sharedPreferences: SharedPreferences? = null) {
            val preference = sharedPreferences ?: preferenceScreen.sharedPreferences
            val default = themeIndicator.default
            val newValue = preference.getString(themeIndicator.keyName, default.toString())
            findPreference(themeIndicator.keyName).summary = newValue
        }

        private fun toast(s: String) {
            Toast.makeText(localContext, s, Toast.LENGTH_LONG).show()
        }

    }

}

