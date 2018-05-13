package com.rkbk60.quickflick.domain

import com.rkbk60.quickflick.model.Flick

/**
 * Class to construct Flick object more safety and more accurately.
 * @param server object implemented FlickFactory.Preference
 */
class FlickFactory(
        thresholdX1: Int, thresholdX2: Int, thresholdY1: Int, thresholdY2: Int) {
    companion object {
        /**
         * Set of numbers used in [FlickFactory.makeWith] as threshold.
         * These values have to update with secondary constructor.
         */
        private val threshold = object {
            var x1 = 1
            var x2 = 1
            var y1 = 1
            var y2 = 1
        }
    }

    init {
        with(threshold) {
            x1 = thresholdX1
            x2 = thresholdX2
            y1 = thresholdY1
            y2 = thresholdY2
        }
    }

    /**
     * Generates Flick with 2 points.
     * @param beforeX X of point before action
     * @param beforeY Y of point before action
     * @param afterX  X of point after action
     * @param afterY  Y of point after action
     * @return Flick object guaranteed that direction and distance are valid
     */
    fun makeWith(beforeX: Int, beforeY: Int, afterX: Int, afterY: Int): Flick {
        val deltaX = afterX - beforeX
        val deltaY = afterY - beforeY
        val absX = Math.abs(deltaX)
        val absY = Math.abs(deltaY)

        // update direction
        val direction = if (absX < threshold.x1 && absY < threshold.y1) {
            Flick.Direction.NONE
        } else {
            // Y-directions have more priority than X-directions when absX == absY
            if (absX > absY) {
                if (deltaX > 0) Flick.Direction.RIGHT else Flick.Direction.LEFT
            } else {
                if (deltaY > 0) Flick.Direction.DOWN  else Flick.Direction.UP
            }
        }

        // update distance
        fun getDistance(threshold1: Int, threshold2: Int, abs: Int) =
                Math.ceil(((abs - threshold1) / threshold2).toDouble()).toInt() + 1
        val distance = when (direction) {
            Flick.Direction.LEFT, Flick.Direction.RIGHT->
                getDistance(threshold.x1, threshold.x2, absX)
            Flick.Direction.UP, Flick.Direction.DOWN ->
                getDistance(threshold.y1, threshold.y2, absY)
            else -> 0
        }

        return Flick(direction, distance)
    }

    fun makeEmptyFlick(): Flick {
        return Flick(Flick.Direction.NONE, 0)
    }
}
