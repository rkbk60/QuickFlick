package com.rkbk60.quickflick.domain

import android.graphics.drawable.BitmapDrawable
import com.rkbk60.quickflick.model.Flick

class IndicatorFactory(var backgrounds: BackgroundDrawables) {
    /**
     * Set of indicator background drawables.
     */
    class BackgroundDrawables(
            val default: BitmapDrawable,
            val onLeft:  BitmapDrawable,
            val onRight: BitmapDrawable,
            val onUp:    BitmapDrawable,
            val onDown:  BitmapDrawable)

    /**
     * Size of indicator.
     */
    private val size = object {
        var left   = 0
        var right  = 0
        var top    = 0
        var bottom = 0

        /**
         * Sets indicator size.
         * This function arguments matches Drawable.setBounds().
         */
        fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
            this.left   = left
            this.right  = right
            this.top    = top
            this.bottom = bottom
        }
    }

    /**
     * Flag whether or not show indicator.
     */
    var enable = true
        set(value) {
            field = value
            if (!value) {
                direction = Flick.Direction.NONE
                currentDistance = 0
                maxDistance = 0
            }
        }

    /**
     * Current flick direction.
     */
    var direction = Flick.Direction.NONE
        set(value) {
            if (enable) field = value
        }

    /**
     * Current flick distance.
     */
    var currentDistance = 0
        set(value) {
            if (enable && value >= 0 && direction != Flick.Direction.NONE) field = value
        }

    /**
     * Max of flick distance in tapping key/direction.
     */
    var maxDistance = 0
        set(value) {
            if (enable && value >= 0 && direction != Flick.Direction.NONE) field = value
        }

    /**
     * Generates BitmapDrawable showing current flick state.
     */
    fun makeIndicator(): BitmapDrawable {
        return with(backgrounds) {
            when (direction) {
                Flick.Direction.NONE  -> default
                Flick.Direction.LEFT  -> onLeft
                Flick.Direction.RIGHT -> onRight
                Flick.Direction.UP    -> onUp
                Flick.Direction.DOWN  -> onDown
            }
        }.apply {
            setBounds(size.left, size.top, size.right, size.bottom)
            alpha = when {
                !enable -> 0
                direction == Flick.Direction.NONE -> 255
                else -> 255 * currentDistance / maxDistance
            }
        }
    }
}