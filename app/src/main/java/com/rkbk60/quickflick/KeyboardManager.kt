package com.rkbk60.quickflick

import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView

/**
 * Created by s-iwamoto on 9/29/17.
 */

class KeyboardManager(ime: InputMethodService, private val keyboardView: KeyboardView) {

    val keyboard = Keyboard(ime, R.xml.keyboard)

    enum class Adjustment { NONE, LEFT, RIGHT }
    private var adjustment = Adjustment.LEFT

    companion object {
        val KEY_NUMBER_META_ALT = 6
        val KEY_NUMBER_CTRL_ALT = 11
        val KEY_NUMBER_INDICATOR = 0
        val KEY_NUMBER_LEFT_SPACER = -1
        val KEY_NUMBER_LEFT_VIEWER = -2
        val KEY_NUMBER_LEFT_SWITCHER = -3
        val KEY_NUMBER_RIGHT_SPACER = -4
        val KEY_NUMBER_RIGHT_VIEWER = -5
        val KEY_NUMBER_RIGHT_SWITCHER = -6

        val KEY_NUMBERS = (1 .. 20)
        val KEY_NUMBERS_VERTICAL_EDGE = listOf(1, 5, 6, 10, 11, 15, 16, 20)
        val KEY_NUMBERS_RIGHT_EDGE =
                listOf(KEY_NUMBER_RIGHT_SPACER, KEY_NUMBER_RIGHT_VIEWER, KEY_NUMBER_RIGHT_SWITCHER)
    }

    init {
        changeKeyWidth()
    }

    fun changeKeyAdjustment(adjustment: Adjustment) {
        this.adjustment = adjustment
        changeKeyWidth()
        keyboardView.invalidateAllKeys()
    }

    private fun changeKeyWidth() {
        var x = 0
        var sum = 0
        val screenWidth = keyboard.keys[0]!!.width
        // set key width
        keyboard.keys.forEach {
            val code = it.codes[0]
            val width = when (adjustment) {
                Adjustment.NONE  -> getWidthInNone(screenWidth, code)
                Adjustment.LEFT  -> getWidthInLeft(screenWidth, code)
                Adjustment.RIGHT -> getWidthInRight(screenWidth, code)
            }
            it.width = width
            it.x = x
            if (code in KeyNumbers.LIST_LOCATED_RIGHT_EDGE) x = 0 else x += width
            if (code != KeyNumbers.INDICATOR) sum += width
        }
        // fix width
        val correction = ((4 * screenWidth) - sum) / 8
        keyboard.keys.forEach {
            val code = it.codes[0]
            if (code in KeyNumbers.LIST_LOCATED_SIDE) it.width += correction
            it.x = x
            if (code in KeyNumbers.LIST_LOCATED_RIGHT_EDGE) x = 0 else x += it.width
        }
    }

    private fun getWidthInNone(screenWidth: Int, code: Int): Int = when (code) {
        KEY_NUMBER_INDICATOR -> screenWidth
        in KEY_NUMBERS_VERTICAL_EDGE -> (0.23 * screenWidth).toInt()
        in KEY_NUMBERS -> (0.18 * screenWidth).toInt()
        else -> 0
    }

    private fun getWidthInLeft(screenWidth: Int, code: Int): Int = when (code) {
        KEY_NUMBER_INDICATOR -> screenWidth
        KEY_NUMBER_RIGHT_SPACER,
        KEY_NUMBER_RIGHT_VIEWER,
        KEY_NUMBER_RIGHT_SWITCHER -> (0.15 * screenWidth).toInt()
        in KEY_NUMBERS -> (0.18 * screenWidth).toInt()
        else -> 0
    }

    private fun getWidthInRight(screenWidth: Int, code: Int): Int = when (code) {
        KEY_NUMBER_INDICATOR -> screenWidth
        KEY_NUMBER_LEFT_SPACER,
        KEY_NUMBER_LEFT_VIEWER,
        KEY_NUMBER_LEFT_SWITCHER -> (0.15 * screenWidth).toInt()
        in KEY_NUMBERS -> (0.18 * screenWidth).toInt()
        else -> 0
    }

}