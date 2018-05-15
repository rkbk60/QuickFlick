package com.rkbk60.quickflick

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.EditTextPreference
import android.preference.PreferenceFragment
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
        private lateinit var rServer: ResourceServer

        override fun onCreate(savedInstanceState: Bundle?) {
            rServer = ResourceServer(activity.applicationContext)
            super.onCreate(savedInstanceState)
//            PreferenceManager.getDefaultSharedPreferences(context)?.edit()?.clear()?.commit() // for debug
            addPreferencesFromResource(R.xml.preferences)
        }

        override fun onResume() {
            super.onResume()
            preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
            rServer.run { setOf(thresholdX1, thresholdX2, thresholdY1, thresholdY2) }.map {
                updateThresholdSummary(it)
            }
            updateThemeSummary()
            updateKeysHeightSummary()
        }

        override fun onPause() {
            super.onPause()
            preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        }

        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
            with (rServer) {
                when (key) {
                    thresholdX1.key -> {
                        validateThreshold(thresholdX1)
                        updateThresholdSummary(thresholdX1)
                    }
                    thresholdX2.key -> {
                        validateThreshold(thresholdX2)
                        updateThresholdSummary(thresholdX2)
                    }
                    thresholdY1.key -> {
                        validateThreshold(thresholdY1)
                        updateThresholdSummary(thresholdY1)
                    }
                    thresholdY2.key -> {
                        validateThreshold(thresholdY2)
                        updateThresholdSummary(thresholdY2)
                    }
                    keyboardHeight.key -> {
                        updateKeysHeightSummary()
                    }
                    indicatorTheme.key -> {
                        updateThemeSummary()
                    }
                }
            }
        }

        private fun validateThreshold(target: ResourceServerBase.PreferenceIntText) {
            try {
                val newValue = target.getCurrentAsString().toInt()
                val minimal  = 10
                if (newValue < minimal) {
                    toast("Minimal value is $minimal thou.")
                    target.also { it.current = minimal }
                    (findPreference(target.key) as? EditTextPreference)?.text = minimal.toString()
                }
            } catch (_: java.lang.Exception) {
                toast("Set default value.")
                target.also { it.current = it.default }
                (findPreference(target.key) as? EditTextPreference)?.text = target.default.toString()
            }
        }

        private fun updateThresholdSummary(target: ResourceServerBase.PreferenceIntText) {
            findPreference(target.key).summary =
                    "${target.current} thou (Default:${target.default})"
        }

        private fun updateKeysHeightSummary() {
            val current = rServer.keyboardHeight.current
            val subContent = when (current) {
                ResourceServer.KeyboardHeight.Lv1 -> "smallest"
                ResourceServer.KeyboardHeight.Lv2 -> "small"
                ResourceServer.KeyboardHeight.Lv3 -> "medium"
                ResourceServer.KeyboardHeight.Lv4 -> "large"
                ResourceServer.KeyboardHeight.Lv5 -> "largest"
            }
            findPreference(rServer.keyboardHeight.key).summary =
                    "Level ${current.toInt()} ($subContent)"
        }

        private fun updateThemeSummary() {
            findPreference(rServer.indicatorTheme.key).summary =
                    rServer.indicatorTheme.current.toString()
        }

        private fun toast(s: String) {
            Toast.makeText(activity.applicationContext, s, Toast.LENGTH_LONG).show()
        }

    }

}

