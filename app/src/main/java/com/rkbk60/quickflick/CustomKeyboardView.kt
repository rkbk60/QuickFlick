package com.rkbk60.quickflick

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ShapeDrawable
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.util.AttributeSet

/**
 * KeyboardView having Flick Indicator.
 */

class CustomKeyboardView(context: Context, attrs: AttributeSet) : KeyboardView(context, attrs) {

    private var indicatorKey: Keyboard.Key? = null
    private var drawable: ShapeDrawable? = null
    private val themeFactory = ThemeFactory(context)

    private var flickDefaultColor = 0
    private var flickLeftColor    = 0
    private var flickRightColor   = 0
    private var flickUpColor      = 0
    private var flickDownColor    = 0

    private lateinit var keymap: Keymap
    private var maxFlickDistance = 1

    init {
        applyColors()
    }

    fun setKeyboard(keyboard: Keyboard, keymap: Keymap) {
        super.setKeyboard(keyboard)
        indicatorKey = keyboard.keys.find { it.codes[0] == KeyNumbers.INDICATOR}
        this.keymap = keymap
        maxFlickDistance = keymap.maxDistance
        updateDrawable(Flick(), 0)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawable?.draw(canvas)
        drawKeySeparator(canvas)
    }

    fun indicate(flick: Flick, initialCode: Int) {
        updateDrawable(flick, initialCode)
        invalidateKey(KeyboardManager.INDEX_INDICATOR)
    }

    fun updateTheme() {
        themeFactory.changeTheme()
        applyColors()
    }

    private fun updateDrawable(flick: Flick, initialCode: Int) {
        if (indicatorKey == null) return
        val shape = ShapeDrawable()
        val x = indicatorKey!!.x + x.toInt()
        val y = indicatorKey!!.y + y.toInt()
        val width = indicatorKey!!.width
        val height = indicatorKey!!.height
        val color = when (flick.direction) {
            Flick.Direction.NONE  -> flickDefaultColor
            Flick.Direction.LEFT  -> flickLeftColor
            Flick.Direction.RIGHT -> flickRightColor
            Flick.Direction.UP    -> flickUpColor
            Flick.Direction.DOWN  -> flickDownColor
        }
        val maxDistance = keymap.getMaxDistance(initialCode, flick.direction)
        val distance = Math.min(flick.distance, maxDistance)
        shape.apply {
            setBounds(x, y, x + width, y + height)
            paint.color = if (maxDistance == 0)
                flickDefaultColor else color
            alpha = if ((flick.direction == Flick.Direction.NONE) or (maxDistance == 0))
                255 else 255 * distance / maxDistance
        }
        drawable = shape
    }

    private fun drawKeySeparator(canvas: Canvas) {
        val keys = keyboard?.keys
                ?.filter { it.codes[0] in KeyNumbers.LIST_NEXT_TO_FUNCTIONS }
                ?.take(2)
                ?:return
        val sizeX = 1
        val top = { key: Keyboard.Key -> (key.y + 0.05 * keyboard.height).toInt() }
        val bottom = { key: Keyboard.Key -> (key.y + 0.95 * keyboard.height).toInt() }
        val color = flickDefaultColor
        keys.forEach { it ->
            ShapeDrawable().apply {
                setBounds(it.x, top(it), it.x + sizeX, bottom(it)) // left border
                paint.color = color
                draw(canvas)
            }
            ShapeDrawable().apply {
                setBounds(it.x + it.width - sizeX, top(it), it.x + it.width, bottom(it)) // right border
                paint.color = color
                draw(canvas)
            }
        }
    }

    private fun applyColors() {
        flickDefaultColor = themeFactory.getBackgroundColor()
        flickLeftColor    = themeFactory.getLeftColor()
        flickRightColor   = themeFactory.getRightColor()
        flickUpColor      = themeFactory.getUpColor()
        flickDownColor    = themeFactory.getDownColor()
    }

    override fun performClick(): Boolean {
        val void = null
        return super.performClick()
    }

}
