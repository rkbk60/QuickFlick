package com.rkbk60.quickflick

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.BitmapDrawable
import android.preference.PreferenceManager
import android.support.annotation.*
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast

/**
 * Class that defines functions/classes using in ResourceServer.
 * @param context context
 */
abstract class ResourceServerBase(protected val context: Context) {
    private val preference = PreferenceManager.getDefaultSharedPreferences(context)
    private val editor = preference.edit()

    /**
     * Preference object that value corresponds kotlin's primitive type.
     * @param keyId preference key name id
     * @param default default value
     */
    abstract inner class PreferenceData <T: Any> (@StringRes keyId: Int, val default: T) {
        val key: String = context.getString(keyId) ?: throw Error("failed to detect key name id")
        protected abstract val getT: ()  -> T?
        protected abstract val setT: (T) -> SharedPreferences.Editor
        /**
         * Current preference value.
         */
        var current: T
            get() = getT() ?: default
            set(value) { setT(value).commit() }

        override fun toString(): String = "$key: $current(default: $default)"

        protected fun toast(s: String) {
            Toast.makeText(context, s, Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Preference object that value is Int but internal type is String.
     * @param keyId preference key name id
     * @param default default value
     */
    inner class PreferenceIntText(@StringRes keyId: Int, default: Int) : PreferenceData<Int>(keyId, default) {
        override val getT = getter@ {
            try {
                return@getter preference.getString(key, default.toString()).toInt()
            } catch (e: java.lang.Exception) {
                return@getter default
            }
        }
        override val setT = { value: Int -> editor.putString(key, value.toString()) }

        fun getCurrentAsString(): String {
            return preference.getString(key, default.toString())
        }
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

    /**
     * Interface or convert enum value to resource string.
     * Classes that use in [PreferenceEnum] have to implement this.
     */
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
                                   val default: T,
                                   private val candidates: Array<T>)
            where T : Enum<T>, T : ResourceEnum {
        val key = context.getString(keyId) ?: throw Error("failed to detect key string id")
        val defaultString = resStringOf(default)
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

    // sugar-syntax methods to access resources

    protected fun pixelResourceOf(@DimenRes id: Int) =
            context.resources.getDimensionPixelSize(id)

    @ColorInt
    protected fun colorResourceOf(@ColorRes id: Int) =
            ContextCompat.getColor(context, id)

    protected fun bitmapResourceOf(@DrawableRes id: Int): BitmapDrawable =
            ContextCompat.getDrawable(context, id)?.mutate() as BitmapDrawable
}