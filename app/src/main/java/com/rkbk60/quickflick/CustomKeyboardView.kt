package com.rkbk60.quickflick

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ShapeDrawable
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.support.v4.content.ContextCompat
import android.util.AttributeSet

/**
 * KeyboardView having Flick Indicator.
 */

class CustomKeyboardView(context: Context, attrs: AttributeSet) : KeyboardView(context, attrs) {

    private var indicatorKey: Keyboard.Key? = null
    private lateinit var indicatorFactory: IndicatorFactory
    private lateinit var keymap: Keymap
    private val separatorColor = ContextCompat.getColor(context, R.color.backgroundIndicator)

    fun setKeyboard(keyboard: Keyboard, keymap: Keymap) {
        super.setKeyboard(keyboard)
        indicatorKey = keyboard.keys.find { it.codes[0] == KeyNumbers.INDICATOR}
        this.keymap = keymap
        indicatorFactory = IndicatorFactory(this, keymap)
        indicatorFactory.updateInfo(indicatorKey, Flick(), SpecialKeyCode.NULL)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        indicatorFactory.draw(canvas)
        drawKeySeparator(canvas)
    }

    fun indicate(flick: Flick, initialCode: Int) {
        indicatorFactory.updateInfo(indicatorKey, flick, initialCode)
        invalidateKey(KeyboardManager.INDEX_INDICATOR)
    }

    fun updateTheme() {
        indicatorFactory.changeTheme()
    }

    private fun drawKeySeparator(canvas: Canvas) {
        val keys = keyboard?.keys
                ?.filter { it.codes[0] in KeyNumbers.LIST_NEXT_TO_FUNCTIONS }
                ?.take(2)
                ?:return
        val sizeX = 1
        val top = { key: Keyboard.Key -> (key.y + 0.05 * keyboard.height).toInt() }
        val bottom = { key: Keyboard.Key -> (key.y + 0.95 * keyboard.height).toInt() }
        val color = separatorColor
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

    override fun performClick(): Boolean {
        val void = null
        return super.performClick()
    }

}
