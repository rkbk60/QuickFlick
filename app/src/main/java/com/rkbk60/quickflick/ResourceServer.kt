package com.rkbk60.quickflick

import android.content.Context
import android.content.SharedPreferences
import android.graphics.PorterDuff
import android.graphics.drawable.BitmapDrawable
import android.preference.PreferenceManager
import android.support.annotation.*
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import com.rkbk60.quickflick.domain.IndicatorFactory

class ResourceServer(private val context: Context) {
    private val preference = PreferenceManager.getDefaultSharedPreferences(context)
    private val editor = preference.edit()

    /**
     * Preference object that value corresponds kotlin's primitive type.
     * @param keyId preference key name id
     * @param default default value
     */
    abstract inner class PreferenceData <T: Any> (@StringRes keyId: Int, private val default: T) {
        protected val key: String = context.getString(keyId) ?: throw Error("failed to detect key name id")
        protected abstract val getT: ()  -> T?
        protected abstract val setT: (T) -> SharedPreferences.Editor
        /**
         * Current preference value.
         */
        var current: T
            get() = getT() ?: default
            set(value) { setT(value).commit() }

        override fun toString(): String = "$key: $current(default: $default)"
    }

    /**
     * Preference object that value is Int but internal type is String.
     * @param keyId preference key name id
     * @param default default value
     */
    inner class PreferenceIntText(@StringRes keyId: Int, default: Int) : PreferenceData<Int>(keyId, default) {
        override val getT = { preference.getString(key, default.toString()).toInt() }
        override val setT = { value: Int -> editor.putString(key, value.toString()) }
    }

    /**
     * Preference object that value corresponds kotlin's Boolean type.
     * @param keyId preference key name id
     * @param default default value
     */
    inner class PreferenceBool(@StringRes keyId: Int, default: Boolean) : PreferenceData<Boolean>(keyId, default) {
        override val getT = { preference.getBoolean(key, default) }
        override val setT = { value: Boolean -> editor.putBoolean(key, value) }
    }

    interface ResourceEnum {
        @StringRes
        fun toResourceId(): Int
    }

    /**
     * Preference object that value corresponds kotlin's enum class.
     * @param keyId preference key name id
     * @param default default value
     * @param candidates enumValues() or values that this application may use
     */
    inner class PreferenceEnum <T>(@StringRes keyId: Int,
                                   private val default: T,
                                   private val candidates: Array<T>)
            where T : Enum<T>, T : ResourceEnum {
        private val key = context.getString(keyId) ?: throw Error("failed to detect key string id")
        private val defaultString = resStringOf(default)
        private val candidateMap = candidates.map { Pair(it, resStringOf(it)) }

        /**
         * Current preference value.
         */
        var current: T
            get() {
                val current = preference.getString(key, defaultString)
                return candidateMap.firstOrNull { it.second == current }?.first ?: default
            }
            set(value) {
                if (!candidates.contains(value)) return
                editor.putString(key, resStringOf(value)).commit()
            }

        override fun toString(): String = "$key: $current(default: $default)"

        private fun resStringOf(value: T): String = context.getString(value.toResourceId())
    }

    val thresholdX1 = PreferenceIntText(R.string.preferences_x1, 25)
    val thresholdX2 = PreferenceIntText(R.string.preferences_x2, 150)
    val thresholdY1 = PreferenceIntText(R.string.preferences_y1, 25)
    val thresholdY2 = PreferenceIntText(R.string.preferences_y2, 120)
    val flickCanceler = PreferenceBool(R.string.preferences_cancel_flick, true)
    val inputCanceler = PreferenceBool(R.string.preferences_cancel_input, false)
    val keyboardIsRight = PreferenceBool(R.string.preferences_keys_adjustment, false)

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
                    onDown  = bitmapResourceOf(R.drawable.morse_down))
        } else {
            IndicatorFactory.BackgroundDrawables(
                    default = bitmapResourceOf(R.drawable.empty),
                    onTap   = bitmapResourceOf(R.drawable.empty),
                    onLeft  = bitmapResourceOf(R.drawable.empty),
                    onRight = bitmapResourceOf(R.drawable.empty),
                    onUp    = bitmapResourceOf(R.drawable.empty),
                    onDown  = bitmapResourceOf(R.drawable.empty))
        }
        infix fun BitmapDrawable.colorIs(@ColorRes id: Int) {
            DrawableCompat.setTint(this, colorResourceOf(id))
            DrawableCompat.setTintMode(this, PorterDuff.Mode.SRC_IN)
        }
        backgrounds.default colorIs R.color.backgroundIndicator
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

    // sugar-syntax methods to access resources

    private fun pixelResourceOf(@DimenRes id: Int) =
            context.resources.getDimensionPixelSize(id)

    @ColorInt
    private fun colorResourceOf(@ColorRes id: Int) =
            ContextCompat.getColor(context, id)

    private fun bitmapResourceOf(@DrawableRes id: Int): BitmapDrawable =
            ContextCompat.getDrawable(context, id)/*.mutate()*/ as BitmapDrawable
}