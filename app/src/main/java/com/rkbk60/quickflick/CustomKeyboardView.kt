package com.rkbk60.quickflick

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ShapeDrawable
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import com.rkbk60.quickflick.model.KeyIndex

class CustomKeyboardView(context: Context, attrs: AttributeSet) : KeyboardView(context, attrs) {
    var indicator: BitmapDrawable? = null

    private val borderLeft  = ShapeDrawable()
    private val borderRight = ShapeDrawable()

    private lateinit var onCloseListener: () -> Unit

    private var isLayoutForRightHand = false

    init {
        val borderColor = ContextCompat.getColor(context, R.color.separator)
        borderLeft.paint.color = borderColor
        borderRight.paint.color = borderColor
    }

    override fun closing() {
        onCloseListener()
        super.closing()
    }

    fun setOnCloseListener(lambda: () -> Unit) {
        onCloseListener = lambda
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        indicator?.draw(canvas)

        // draw borderlines
        val keys = keyboard?.keys
                ?.filter { KeyIndex.isNextToFunction(it.codes[0]) }
                ?.take(2)
                ?: return
        val borderWidth = 2
        val top    = { key: Keyboard.Key -> (key.y + 0.05 * keyboard.height).toInt() }
        val bottom = { key: Keyboard.Key -> (key.y + 4 * key.height + 4) }
        if (isLayoutForRightHand) {
            keys[0]?.also { // Esc key
                borderLeft.apply {
                    setBounds(it.x, top(it), it.x + borderWidth, bottom(it))
                    draw(canvas)
                }
            }
        } else {
            keys[1]?.also { // Tab/Enter key
                borderRight.apply {
                    setBounds(it.x + it.width - borderWidth, top(it), it.x + it.width, bottom(it))
                    draw(canvas)
                }
            }
        }
    }

    fun setKeyboardWith(controller:  KeyboardController,
                        isRight:     Boolean,
                        useFooter:   Boolean,
                        heightLevel: Int) {
        keyboard = controller.inflateKeyboard(isRight, useFooter, heightLevel)
        isLayoutForRightHand = isRight
    }

    @Suppress("RedundantOverride")
    override fun performClick(): Boolean {
        return super.performClick()
    }
}