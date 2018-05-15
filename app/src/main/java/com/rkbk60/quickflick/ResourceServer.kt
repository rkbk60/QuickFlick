package com.rkbk60.quickflick

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.BitmapDrawable
import android.support.annotation.*
import android.support.v4.graphics.drawable.DrawableCompat
import com.rkbk60.quickflick.domain.IndicatorFactory

class ResourceServer(context: Context) : ResourceServerBase(context) {
    val thresholdX1 = PreferenceIntText(R.string.preferences_x1, 25)
    val thresholdX2 = PreferenceIntText(R.string.preferences_x2, 150)
    val thresholdY1 = PreferenceIntText(R.string.preferences_y1, 25)
    val thresholdY2 = PreferenceIntText(R.string.preferences_y2, 120)
    val canCancelFlick = PreferenceBool(R.string.preferences_cancel_flick, true)
    val canCancelInput = PreferenceBool(R.string.preferences_cancel_input, false)
    val keyboardIsRight = PreferenceBool(R.string.preferences_keys_adjustment, false)
    val keyboardUseFooter = PreferenceBool(R.string.preferences_keyboard_footer, false)

    val keyboardHeight = PreferenceEnum(R.string.preferences_keyboard_height, KeyboardHeight.Lv2, enumValues())
    enum class KeyboardHeight : ResourceEnum {
        Lv1, Lv2, Lv3, Lv4, Lv5;

        override fun toResourceId(): Int = when (this) {
            Lv1 -> R.string.keyboard_height_1
            Lv2 -> R.string.keyboard_height_2
            Lv3 -> R.string.keyboard_height_3
            Lv4 -> R.string.keyboard_height_4
            Lv5 -> R.string.keyboard_height_5
        }

        fun toInt(): Int = when (this) {
            Lv1 -> 1
            Lv2 -> 2
            Lv3 -> 3
            Lv4 -> 4
            Lv5 -> 5
        }
    }

    private val indicatorTheme = PreferenceEnum(R.string.preferences_theme_indicator,
                                                IndicatorTheme.Base,
                                                enumValues())
    private enum class IndicatorTheme : ResourceEnum {
        Base, Lime, Morse;

        override fun toResourceId(): Int = when (this) {
            Base -> R.string.theme_base
            Lime -> R.string.theme_lime
            Morse -> R.string.theme_morse
        }
    }

    fun supplyIndicatorBackground(): IndicatorFactory.BackgroundDrawables {
        val current = indicatorTheme.current
        val backgrounds = if (current == IndicatorTheme.Morse) {
            IndicatorFactory.BackgroundDrawables(
                    default = bitmapResourceOf(R.drawable.empty),
                    onTap   = bitmapResourceOf(R.drawable.empty),
                    onLeft  = bitmapResourceOf(R.drawable.morse_left),
                    onRight = bitmapResourceOf(R.drawable.morse_right),
                    onUp    = bitmapResourceOf(R.drawable.morse_up),
                    onDown  = bitmapResourceOf(R.drawable.morse_down),
                    disable = bitmapResourceOf(R.drawable.empty))
        } else {
            IndicatorFactory.BackgroundDrawables(
                    default = bitmapResourceOf(R.drawable.empty),
                    onTap   = bitmapResourceOf(R.drawable.empty),
                    onLeft  = bitmapResourceOf(R.drawable.empty),
                    onRight = bitmapResourceOf(R.drawable.empty),
                    onUp    = bitmapResourceOf(R.drawable.empty),
                    onDown  = bitmapResourceOf(R.drawable.empty),
                    disable = bitmapResourceOf(R.drawable.empty))
        }
        infix fun BitmapDrawable.colorIs(@ColorRes id: Int) {
            DrawableCompat.setTint(this, colorResourceOf(id))
            DrawableCompat.setTintMode(this, PorterDuff.Mode.SRC_IN)
        }
        backgrounds.default colorIs R.color.backgroundIndicator
        backgrounds.disable colorIs R.color.backgroundKey
        when (current) {
            IndicatorTheme.Morse -> Unit
            IndicatorTheme.Base -> {
                backgrounds.onTap   colorIs R.color.themeBaseCenter
                backgrounds.onLeft  colorIs R.color.themeBaseLeft
                backgrounds.onRight colorIs R.color.themeBaseRight
                backgrounds.onUp    colorIs R.color.themeBaseUp
                backgrounds.onDown  colorIs R.color.themeBaseDown
            }
            IndicatorTheme.Lime -> {
                backgrounds.onTap   colorIs R.color.themeLimeCenter
                backgrounds.onLeft  colorIs R.color.themeLimeLeft
                backgrounds.onRight colorIs R.color.themeLimeRight
                backgrounds.onUp    colorIs R.color.themeLimeUp
                backgrounds.onDown  colorIs R.color.themeLimeDown
            }
        }
        return backgrounds
    }
}