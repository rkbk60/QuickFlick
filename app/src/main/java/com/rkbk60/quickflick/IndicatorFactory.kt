package com.rkbk60.quickflick

import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ShapeDrawable
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.preference.PreferenceManager
import android.support.v4.content.ContextCompat

/**
 * Provide Indicator theme
 */
class IndicatorFactory(private val keyboardView: KeyboardView, private val keymap: Keymap) {
    private val context = keyboardView.context!!
    private var left = 0
    private var right = 0
    private var top = 0
    private var bottom = 0

    private var direction: Flick.Direction = Flick.Direction.NONE
    private var maxDistance = 0
    private var distance = 0

    private val keynameBase  = context.getString(R.string.theme_base)
    private val keynameLime  = context.getString(R.string.theme_lime)
    private val keynameMorse = context.getString(R.string.theme_morse)
    private var theme = ""

    private var background = 0
    private var leftColor = 0
    private var rightColor = 0
    private var upColor = 0
    private var downColor = 0

    init {
        changeTheme()
    }

    fun changeTheme() {
        theme = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.preferences_theme_indicator), keynameBase)
        when(theme) {
            keynameBase -> applyBaseTheme()
            keynameLime -> applyLimeTheme()
            keynameMorse -> Unit
            else -> applyBaseTheme()
        }
    }

    fun updateInfo(key: Keyboard.Key?, flick: Flick, onTapKeycode: Int) {
        if (key is Keyboard.Key) {
            left = key.x + keyboardView.x.toInt()
            top  = key.y + keyboardView.y.toInt()
            right  = left + key.width
            bottom = top  + key.height
        }
        maxDistance = keymap.getMaxDistance(onTapKeycode, flick.direction)
        distance = Math.min(flick.distance, maxDistance)
        direction = if (maxDistance == 0) Flick.Direction.NONE else flick.direction
    }

    fun draw(canvas: Canvas) = when(theme) {
        keynameBase, keynameLime -> drawShape(canvas)
        keynameMorse -> drawBitmap(canvas)
        else -> drawShape(canvas)
    }

    private fun drawShape(canvas: Canvas) {
        val color = when (direction) {
            Flick.Direction.NONE  -> background
            Flick.Direction.LEFT  -> leftColor
            Flick.Direction.RIGHT -> rightColor
            Flick.Direction.UP    -> upColor
            Flick.Direction.DOWN  -> downColor
        }
        ShapeDrawable().apply {
            setBounds(left, top, right, bottom)
            paint.color = if (maxDistance == 0) background else color
            alpha = if ((direction == Flick.Direction.NONE)) 255 else 255 * distance / maxDistance
            draw(canvas)
        }
    }

    private fun applyColor(id: Int): Int = ContextCompat.getColor(context, id)

    private fun applyBaseTheme() {
        background = applyColor(R.color.themeBaseBackgroundIndicator)
        leftColor  = applyColor(R.color.themeBaseLeft)
        rightColor = applyColor(R.color.themeBaseRight)
        upColor    = applyColor(R.color.themeBaseUp)
        downColor  = applyColor(R.color.themeBaseDown)
    }

    private fun applyLimeTheme() {
        background = applyColor(R.color.themeLimeBackgroundIndicator)
        leftColor  = applyColor(R.color.themeLimeLeft)
        rightColor = applyColor(R.color.themeLimeRight)
        upColor    = applyColor(R.color.themeLimeUp)
        downColor  = applyColor(R.color.themeLimeDown)
    }

    private fun drawBitmap(canvas: Canvas) {
        if (direction == Flick.Direction.NONE) {
            ShapeDrawable().apply {
                setBounds(left, top, right, bottom)
                paint.color = applyColor(R.color.backgroundIndicator)
                draw(canvas)
            }
            return
        }
        val id = when (direction) {
            Flick.Direction.LEFT  -> R.drawable.morse_left
            Flick.Direction.RIGHT -> R.drawable.morse_right
            Flick.Direction.UP    -> R.drawable.morse_up
            Flick.Direction.DOWN  -> R.drawable.morse_down
            else -> return
        }
        val bitmap = ContextCompat.getDrawable(context, id) as BitmapDrawable
        bitmap.apply {
            setBounds(left, top, right, bottom)
            alpha = if ((direction == Flick.Direction.NONE)) 255 else 255 * distance / maxDistance
            draw(canvas)
        }
    }
}
