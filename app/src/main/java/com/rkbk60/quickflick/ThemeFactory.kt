package com.rkbk60.quickflick

import android.content.Context
import android.preference.PreferenceManager

/**
 * Provide ColorTheme to CustomKeyboardView
 */
class ThemeFactory(private val context: Context): IndicatorThemeFactory(context) {

    private val keynameBase = context.getString(R.string.theme_base)
    private val keynameLime = context.getString(R.string.theme_lime)

    private lateinit var factory: IndicatorThemeFactory

    init {
        changeTheme()
    }

    override fun getBackgroundColor(): Int = factory.getBackgroundColor()
    override fun getLeftColor(): Int = factory.getLeftColor()
    override fun getRightColor(): Int = factory.getRightColor()
    override fun getUpColor(): Int = factory.getUpColor()
    override fun getDownColor(): Int = factory.getDownColor()

    fun changeTheme() {
        val prefTheme = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.preferences_theme_indicator), "")
        factory = when (prefTheme) {
            keynameBase -> BaseThemeFactory(context)
            keynameLime -> LimeThemeFactory(context)
            else -> BaseThemeFactory(context)
        }
    }

    inner class BaseThemeFactory(context: Context): IndicatorThemeFactory(context) {
        override fun getBackgroundColor(): Int = getColor(R.color.themeBaseBackgroundIndicator)
        override fun getLeftColor(): Int = getColor(R.color.themeBaseLeft)
        override fun getRightColor(): Int = getColor(R.color.themeBaseRight)
        override fun getUpColor(): Int = getColor(R.color.themeBaseUp)
        override fun getDownColor(): Int = getColor(R.color.themeBaseDown)
    }

    inner class LimeThemeFactory(context: Context): IndicatorThemeFactory(context) {
        override fun getBackgroundColor(): Int = getColor(R.color.themeLimeBackgroundIndicator)
        override fun getLeftColor(): Int = getColor(R.color.themeLimeLeft)
        override fun getRightColor(): Int = getColor(R.color.themeLimeRight)
        override fun getUpColor(): Int = getColor(R.color.themeLimeUp)
        override fun getDownColor(): Int = getColor(R.color.themeLimeDown)
    }

}
