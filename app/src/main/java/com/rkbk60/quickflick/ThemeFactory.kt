package com.rkbk60.quickflick

import android.content.Context
import android.support.v4.content.ContextCompat

/**
 * Provide ColorTheme to CustomKeyboardView
 */
class ThemeFactory(private val context: Context, theme: ThemeFactory.Theme): IndicatorThemeFactory(context) {

    enum class Theme { BASE, LIME, CUSTOM }

    private lateinit var factory: IndicatorThemeFactory

    init {
        changeTheme(theme)
    }

    override fun getBackgroundColor(): Int = factory.getBackgroundColor()
    override fun getLeftColor(): Int = factory.getLeftColor()
    override fun getRightColor(): Int = factory.getRightColor()
    override fun getUpColor(): Int = factory.getUpColor()
    override fun getDownColor(): Int = factory.getDownColor()

    fun changeTheme(theme: ThemeFactory.Theme) {
        factory = when (theme) {
            Theme.BASE   -> BaseThemeFactory(context)
            Theme.LIME   -> LimeThemeFactory(context)
            Theme.CUSTOM -> BaseThemeFactory(context)
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
