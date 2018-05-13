package com.rkbk60.quickflick

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ShapeDrawable
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.Log
import com.rkbk60.quickflick.model.KeyIndex

class CustomKeyboardView(context: Context, attrs: AttributeSet) : KeyboardView(context, attrs) {
    var indicator: BitmapDrawable? = null

    private val borderColor = ContextCompat.getColor(context, R.color.backgroundIndicator)
    private val borderLeft  = ShapeDrawable().apply { paint.color = borderColor }
    private val borderRight = ShapeDrawable().apply { paint.color = borderColor }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        indicator?.draw(canvas)

        // draw borderlines
//        val keys = keyboard?.keys
//                ?.filter { KeyIndex.isNextToFunction(it.codes[0]) }
//                ?.take(2)
//                ?: return
//        val borderWidth = 1
//        val top    = { key: Keyboard.Key -> (key.y + 0.05 * keyboard.height).toInt() }
//        val bottom = { key: Keyboard.Key -> (key.y + 0.95 * keyboard.height).toInt() }
//        keys[0]?.also {
//            borderLeft.apply {
//                Log.d("LocalLog", "make left border")
//                setBounds(it.x, top(it), it.x + borderWidth, bottom(it))
//                draw(canvas)
//            }
//        }
//        keys[1]?.also {
//            borderRight.apply {
//                Log.d("LocalLog", "make right border")
//                setBounds(it.x + it.width - borderWidth, top(it), it.x, bottom(it))
//                draw(canvas)
//            }
//        }
    }

    @Suppress("RedundantOverride")
    override fun performClick(): Boolean {
        return super.performClick()
    }
}