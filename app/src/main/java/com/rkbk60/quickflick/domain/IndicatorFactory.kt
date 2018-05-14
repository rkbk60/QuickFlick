package com.rkbk60.quickflick.domain

import android.graphics.drawable.BitmapDrawable
import com.rkbk60.quickflick.model.Flick

class IndicatorFactory(var backgrounds: BackgroundDrawables) {
    /**
     * Set of indicator background drawables.
     */
    class BackgroundDrawables(
            val default: BitmapDrawable,
            val onTap:   BitmapDrawable,
            val onLeft:  BitmapDrawable,
            val onRight: BitmapDrawable,
            val onUp:    BitmapDrawable,
            val onDown:  BitmapDrawable,
            val disable: BitmapDrawable)

    /**
     * Size of indicator.
     */
    var left   = 0
    var right  = 0
    var top    = 0
    var bottom = 0

    /**
     * Flag whether or not show indicator.
     */
    var enable = true

    /**
     * Flag of whether or not to press any key.
     */
    var isDuringInput = false

    /**
     * Current flick direction.
     */
    var direction = Flick.Direction.NONE

    /**
     * Current flick distance.
     */
    var currentDistance = 0

    /**
     * Max of flick distance in tapping key/direction.
     */
    var maxDistance = 0

    /**
     * Generates BitmapDrawable showing current flick state.
     */
    fun makeIndicator(): BitmapDrawable {
        return with(backgrounds) {
            if (!enable) {
                disable
            } else if (!isDuringInput) {
                default
            } else if (maxDistance <= 0) {
                onTap
            } else {
                when (direction) {
                    Flick.Direction.NONE  -> onTap
                    Flick.Direction.LEFT  -> onLeft
                    Flick.Direction.RIGHT -> onRight
                    Flick.Direction.UP    -> onUp
                    Flick.Direction.DOWN  -> onDown
                }
            }
        }.apply {
            setBounds(left, top, right, bottom)
            alpha = when {
                !enable -> 255
                !isDuringInput -> 255
                direction == Flick.Direction.NONE -> 255
                maxDistance <= 0 -> 255
                currentDistance >= maxDistance -> 255
                else -> 255 * currentDistance / maxDistance
            }
        }
    }
}