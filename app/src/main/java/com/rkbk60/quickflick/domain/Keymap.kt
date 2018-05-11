package com.rkbk60.quickflick.domain

import com.rkbk60.quickflick.model.Flick
import com.rkbk60.quickflick.model.KeyInfo

/**
 * Keyboard keymap class.
 * This class can store KeyInfo, and pick out one with Flick information.
 */
class Keymap {
    /**
     * Entity of Keymap.
     */
    private val keymap: MutableMap<Int, KeymapElement> = mutableMapOf()
    /**
     * Unless you set any KeyInfo, [getKey] will return this value.
     */
    private val primaryKeyInfo: KeyInfo = KeyInfo.NULL

    /**
     * Sets KeyInfo to keymap.
     * This method can't assign Flick.distance because this method store KeyInfo as 'stack'.
     * So do call this in a sequential order to stock accurately.
     * @param index index of keymap (equal to Keyboard.Key.codes)
     * @param direction index direction
     * @param key key that you want to store
     */
    fun put(index: Int, direction: Flick.Direction, key: KeyInfo) {
        if (keymap.uncontainsKey(index)) {
            keymap[index] = KeymapElement()
        }
        keymap[index]?.put(direction, key)
    }

    /**
     * Finds stored KeyInfo with index and Flick.
     * When keymap have not defined or can't find matching KeyInfo,
     * this method returns [primaryKeyInfo].
     * @param index index code of keymap (equal to Keyboard.Key.codes)
     * @param flick flick object that has current flick information
     * @return KeyInfo matching [index] and [flick], or [primaryKeyInfo]
     */
    fun getKey(index: Int, flick: Flick): KeyInfo {
        return if (keymap.uncontainsKey(index))
            primaryKeyInfo
        else
            keymap[index]?.getKey(flick) ?: primaryKeyInfo
    }

    /**
     * Clears keymap that has assigned index.
     * You can also call this function when keymap-object haven't created at [keymap], then
     * this method does nothing, not throws any error.
     */
    fun clear(index: Int) {
        keymap[index]?.clear()
    }

    /**
     * Returns max distance that can find KeyInfo in keymap matching parameters.
     * If there aren't matching keymap or KeyInfo, it will be return 0.
     * @param index index code of keymap (equal to Keyboard.Key.codes)
     * @param direction index direction
     * @return max distance
     */
    fun getMaxDistance(index: Int, direction: Flick.Direction): Int {
        return keymap[index]?.getMaxDistance(direction) ?: 0
    }

    /**
     * Returns max distance that can find any KeyInfo in all keymap.
     * If you haven't call [put] before, this method returns 0.
     * @return max distance
     */
    fun getMaxDistance(): Int {
        return keymap.keys.map { keymap[it]?.getMaxDistance() ?: 0 }.max() ?: 0
    }

    private fun <K, V> MutableMap<K, V>.uncontainsKey(key: K): Boolean {
        return isEmpty() || !containsKey(key)
    }

    /**
     * Element of keymap class.
     */
    private inner class KeymapElement {
        /**
         * Entity of keymap.
         * Key of map is Flick.Direction, Key of list is Flick.direction.
         */
        private val listMap: MutableMap<Flick.Direction, MutableList<KeyInfo>> = mutableMapOf()
        /**
         * Primary KeyInfo.
         * Its value equals to [Keymap.primaryKeyInfo].
         */
        private val primaryKeyInfo: KeyInfo = this@Keymap.primaryKeyInfo
        /**
         * KeyInfo when Flick.Direction is NONE, or Flick.direction is 0.
         */
        private var onTapKeyInfo: KeyInfo = primaryKeyInfo

        /**
         * Sets KeyInfo to listMap.
         * @param direction index direction
         * @param key key that you want to store
         */
        fun put(direction: Flick.Direction, key: KeyInfo) {
            if (direction == Flick.Direction.NONE) {
                if (onTapKeyInfo == primaryKeyInfo) {
                    onTapKeyInfo = key
                }
            } else {
                if (listMap.uncontainsKey(direction)) {
                    listMap[direction] = mutableListOf()
                }
                listMap[direction]?.add(key)
            }
        }

        /**
         * Finds stored KeyInfo with index and Flick.
         * When keymap have not defined or can't find matching KeyInfo,
         * this method returns [onTapKeyInfo].
         * @param flick flick object that has current flick information
         * @return KeyInfo matching [flick], or [onTapKeyInfo]
         */
        fun getKey(flick: Flick): KeyInfo {
            return (listMap[flick.direction] ?: return onTapKeyInfo)
                    .filterIndexed { i, _ -> i < flick.distance }
                    .lastOrNull()
                    ?: onTapKeyInfo
        }

        /**
         * Clears [listMap] and reset [onTapKeyInfo].
         */
        fun clear() {
            onTapKeyInfo = primaryKeyInfo
            listMap.clear()
        }

        /**
         * Returns max distance from listMap[[direction]].
         * @param direction flick direction
         * @returns max distance
         */
        fun getMaxDistance(direction: Flick.Direction): Int {
            return listMap[direction]?.size ?: 0
        }

        /**
         * Returns max distance from listMap.
         * @returns max distance
         */
        fun getMaxDistance(): Int {
            return listMap.keys.map { getMaxDistance(it) }.max() ?: 0
        }
    }
}
