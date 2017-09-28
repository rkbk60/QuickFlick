package com.rkbk60.quickflick

import android.content.Context
import android.inputmethodservice.KeyboardView
import android.preference.PreferenceManager
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager

/**
 * detect Flick value
 */

class Flick internal constructor(direction: Direction = Direction.NONE) {
    enum class Direction {
        NONE, LEFT, RIGHT, UP, DOWN;

        override fun toString(): String = when (this) {
            NONE  -> "None"
            LEFT  -> "Left"
            RIGHT -> "Right"
            UP    -> "Up"
            DOWN  -> "Down"
        }

        companion object {

            fun pointToDirection(x: Int, y: Int): Direction {
                if (x != 0 || y != 0) {
                    return if (Math.abs(x) > Math.abs(y))
                        if (x > 0) RIGHT else LEFT
                    else
                        if (y > 0) DOWN  else UP
                }
                return NONE
            }

        }

    }

    var direction: Direction
        private set
    var distance: Int = 0
        private set

    companion object {
        private val minimalThreshold = 10
        private var thresholdX1: Int = minimalThreshold
        private var thresholdY1: Int = minimalThreshold
        private var thresholdX2: Int = minimalThreshold
        private var thresholdY2: Int = minimalThreshold
    }

    init {
        this.direction = direction
        distance = 0
    }

    fun reset() {
        direction = Direction.NONE
        distance = 0
    }

    fun update(beforeX: Int, beforeY: Int, afterX: Int, afterY: Int) {
        val fixedX = afterX - beforeX
        val fixedY = afterY - beforeY
        if (Math.abs(fixedX) < thresholdX1 && Math.abs(fixedY) < thresholdY1) {
            direction = Direction.NONE
            distance = 0
        } else {
            direction = Direction.pointToDirection(fixedX, fixedY)
            distance = detectDistance(fixedX, fixedY, direction)
        }
    }

    private fun detectDistance(fixedX: Int, fixedY: Int, direction: Direction): Int {
        val distance: Int
        val threshold1: Int
        val threshold2: Int
        when (direction) {
            Direction.LEFT,
            Direction.RIGHT -> {
                distance = Math.abs(fixedX)
                threshold1 = thresholdX1
                threshold2 = thresholdX2
            }
            Direction.UP,
            Direction.DOWN -> {
                distance = Math.abs(fixedY)
                threshold1 = thresholdY1
                threshold2 = thresholdY2
            }
            Direction.NONE -> return 0
        }
        return Math.ceil(((distance - threshold1) / threshold2).toDouble()).toInt() + 1
    }

    fun updateDistanceThreshold(context: Context) {
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val thou = (context.resources.getDimensionPixelSize(R.dimen.inch_unit)) * 0.001
        thresholdX1 = (pref.getString("threshold_x1", "50" ).toInt() * thou).toInt()
        thresholdX2 = (pref.getString("threshold_x2", "300").toInt() * thou).toInt()
        thresholdY1 = (pref.getString("threshold_y1", "50" ).toInt() * thou).toInt()
        thresholdY2 = (pref.getString("threshold_y2", "150").toInt() * thou).toInt()
    }

    override fun toString(): String = "$direction.$distance"

}
