package com.rkbk60.quickflick

import android.content.Context
import android.support.v4.content.ContextCompat

/**
 * abstract class to define Indicator color
 */

abstract class IndicatorThemeFactory(private val context: Context) {
    protected fun getColor(id: Int): Int = ContextCompat.getColor(context, id)
    abstract fun getBackgroundColor(): Int
    abstract fun getLeftColor(): Int
    abstract fun getRightColor(): Int
    abstract fun getUpColor(): Int
    abstract fun getDownColor(): Int
}
