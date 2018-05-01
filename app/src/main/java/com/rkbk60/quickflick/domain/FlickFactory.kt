package com.rkbk60.quickflick.domain

import com.rkbk60.quickflick.model.Flick

/**
 * Class to construct Flick object more safety and more accurately.
 * @param server object implemented FlickFactory.Preference
 */
class FlickFactory(server: PreferenceServer) {
    /**
     * Data set containing flick threshold related numbers.
     * Unit of [x1], [x2], [y1], [y2] is thou(milli inch), [inch] unit is px.
     * These values are used in Flick constructor to define true-threshold.
     * @param x1 threshold of x-direction to decide tap or flick
     * @param x2 threshold of x-direction to calculate flick distance level
     * @param y1 threshold of y-direction to decide tap or flick
     * @param y2 threshold of y-direction to calculate flick distance level
     * @param inch raw pixel size to convert from thou to px, equal to 1 inch.
     *             Use Resource.getDimensionPixelSize to set this value.
     */
    data class Preference(val x1: Int, val x2: Int, val y1: Int, val y2: Int, val inch: Int)

    /**
     * Interface to get [FlickFactory.Preference].
     */
    interface PreferenceServer {
        fun getFlickPref(): Preference
    }

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
        val pref = server.getFlickPref()
        fun valueOf(x: Int): Int = Math.max((x * 0.001 * pref.inch).toInt(), 1)
        threshold.apply {
            x1 = valueOf(pref.x1)
            x2 = valueOf(pref.x2)
            y1 = valueOf(pref.y1)
            y2 = valueOf(pref.y2)
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
}
