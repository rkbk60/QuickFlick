package com.rkbk60.quickflick

import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.util.Log

/**
 * Created by s-iwamoto on 9/29/17.
 */

class KeyboardManager(ime: InputMethodService, private val keyboardView: KeyboardView) {

    val keyboard = Keyboard(ime, R.xml.keyboard)

    enum class Adjustment { NONE, LEFT, RIGHT }
    private var adjustment = Adjustment.NONE

    companion object {
        var INDEX_INDICATOR = 0
            private set
        private var INDEX_ARROW = 3
        private var INDEX_META_ALT = 9
        private var INDEX_CTRL_ALT = 16
    }

    private lateinit var keyArrow: Keyboard.Key
    private lateinit var keyMetaAlt: Keyboard.Key
    private lateinit var keyCtrlAlt: Keyboard.Key

    init {
        changeKeyWidth(true)
    }

    fun changeKeyAdjustment(adjustment: Adjustment) {
        this.adjustment = adjustment
        changeKeyWidth()
        keyboardView.invalidateAllKeys()
    }

    fun updateArrowKeyFace(state: ArrowKey.State) {
        keyArrow.label = when (state) {
            ArrowKey.State.DEFAULT -> "arw"
            ArrowKey.State.REPEATING -> "ARW"
            ArrowKey.State.PAGE_MOVE -> "pmv"
        }
        keyboardView.invalidateKey(INDEX_ARROW)
    }

    fun updateArrowKeyRepeatable(state: ArrowKey.State) {
        keyArrow.repeatable = state == ArrowKey.State.REPEATING
    }

    fun updateMetaAltKeyFace(enableMeta: Boolean, enableAlt: Boolean) {
        val faceCode = (if (enableMeta) 0b10 else 0).or(if (enableAlt) 0b01 else 0)
        keyMetaAlt.label = when (faceCode) {
            0b11 -> "M/A"
            0b10 -> "M/a"
            0b01 -> "m/A"
            else -> "m/a"
        }
        keyboardView.invalidateKey(INDEX_META_ALT)
    }

    fun updateCtrlAltKeyFace(enableCtrl: Boolean, enableAlt: Boolean) {
        val faceCode = (if (enableCtrl) 0b10 else 0).or(if (enableAlt) 0b01 else 0)
        keyCtrlAlt.label = when (faceCode) {
            0b11 -> "C/A"
            0b10 -> "C/a"
            0b01 -> "c/A"
            else -> "c/a"
        }
        keyboardView.invalidateKey(INDEX_CTRL_ALT)
    }

    private fun changeKeyWidth(runKeyRecorder: Boolean = false) {
        var x = 0
        var sum = 0
        val screenWidth = keyboard.keys[0]!!.width
        // set key width and record key index
        keyboard.keys.forEachIndexed { index, key ->
            val code = key.codes[0]
            val width = when (adjustment) {
                Adjustment.NONE  -> getWidthInNone(screenWidth, code)
                Adjustment.LEFT  -> getWidthInLeft(screenWidth, code)
                Adjustment.RIGHT -> getWidthInRight(screenWidth, code)
            }
            Log.d("LocalLog", "$index: $width")
            key.width = width
            key.x = x
            if (code in KeyNumbers.LIST_LOCATED_RIGHT_EDGE) x = 0 else x += width
            if (code != KeyNumbers.INDICATOR) sum += width

            if (runKeyRecorder) when (code) {
                KeyNumbers.INDICATOR -> INDEX_INDICATOR = index
                KeyNumbers.ALLOW -> {
                    INDEX_ARROW = index
                    keyArrow = key
                }
                KeyNumbers.META_ALT -> {
                    INDEX_META_ALT = index
                    keyMetaAlt = key
                }
                KeyNumbers.CTRL_ALT -> {
                    INDEX_CTRL_ALT = index
                    keyCtrlAlt = key
                }
            }
        }
        // fix width
        val correction = ((4 * screenWidth) - sum) / 8
        keyboard.keys.forEach {
            val code = it.codes[0]
            if (code in KeyNumbers.LIST_LOCATED_SIDE) it.width += correction
            it.x = x
            if (code in KeyNumbers.LIST_LOCATED_RIGHT_EDGE) x = 0 else x += it.width
            Log.d("LocalLog", "c$code w${it.width} x${it.x}")
        }
    }

    private fun getWidthInNone(screenWidth: Int, code: Int): Int = when (code) {
        KeyNumbers.INDICATOR -> screenWidth
        in KeyNumbers.LIST_LOCATED_SIDE -> (0.23 * screenWidth).toInt()
        in KeyNumbers.LIST_INPUTTABLE -> (0.18 * screenWidth).toInt()
        else -> 0
    }

    private fun getWidthInLeft(screenWidth: Int, code: Int): Int = when (code) {
        KeyNumbers.INDICATOR -> screenWidth
        KeyNumbers.RIGHT_SPACER,
        KeyNumbers.RIGHT_VIEWER,
        KeyNumbers.RIGHT_SWITCHER -> (0.15 * screenWidth).toInt()
        in KeyNumbers.LIST_INPUTTABLE -> (0.18 * screenWidth).toInt()
        else -> 0
    }

    private fun getWidthInRight(screenWidth: Int, code: Int): Int = when (code) {
        KeyNumbers.INDICATOR -> screenWidth
        KeyNumbers.LEFT_SPACER,
        KeyNumbers.LEFT_VIEWER,
        KeyNumbers.LEFT_SWITCHER -> (0.15 * screenWidth).toInt()
        in KeyNumbers.LIST_INPUTTABLE -> (0.18 * screenWidth).toInt()
        else -> 0
    }

}