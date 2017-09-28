package com.rkbk60.quickflick

/**
 * data list for convert (primaryCode, Flick) -> actualCode
 */

class Keymap {
    private var keymap: MutableMap<Int, KeyOptionMap> = mutableMapOf()

    fun put(initialCode: Int, actualCode: Int, direction: Flick.Direction): Keymap {
        if (keymap.isEmpty() || !keymap.containsKey(initialCode)) {
            keymap.put(initialCode, KeyOptionMap())
        }
        keymap[initialCode]!!.put(actualCode, direction)
        return this
    }

    fun put(initialCode: Int, char: Char, direction: Flick.Direction): Keymap =
            put(initialCode, char.toInt(), direction)

    fun searchKeycode(initialCode: Int, flick: Flick): Int {
        return if (keymap.isEmpty() || !keymap.containsKey(initialCode)) {
            SpecialKeyCode.NULL
        } else {
            keymap[initialCode]?.searchKeycode(flick.direction, flick.distance) ?: SpecialKeyCode.NULL
        }
    }

    fun reset(initialCode: Int) {
        if (keymap.isEmpty() || !keymap.containsKey(initialCode)) return
        keymap[initialCode] = KeyOptionMap()
    }

    fun getMaxDistance(initialCode: Int, direction: Flick.Direction): Int =
            keymap[initialCode]?.getMaxDistance(direction) ?: 0

    val maxDistance: Int
        get() = keymap.keys.map{ keymap[it]?.maxDistance ?: 0 }.max() ?: 0

    inner class KeyOptionMap {

        private var keyOptions: MutableMap<Flick.Direction, MutableList<Int>> = mutableMapOf()
        private var noFlickKeycode = SpecialKeyCode.NULL

        fun put(keycode: Int, direction: Flick.Direction) {
            if (direction == Flick.Direction.NONE) {
                if (noFlickKeycode == SpecialKeyCode.NULL)
                    noFlickKeycode = keycode
            } else {
                if (keyOptions.isEmpty() || !keyOptions.containsKey(direction)) {
                    keyOptions.put(direction, mutableListOf())
                }
                keyOptions[direction]?.add(keycode)
            }
        }

        fun searchKeycode(direction: Flick.Direction, distance: Int): Int {
            if (direction == Flick.Direction.NONE || distance <= 0) return noFlickKeycode
            val list = keyOptions[direction] ?: return noFlickKeycode
            val index = list.run { if (lastIndex < distance) lastIndex else distance - 1 }
            return list[index]
        }

        val maxDistance: Int
            get() = keyOptions.keys.map { getMaxDistance(it) }.max() ?: 0

        fun getMaxDistance(direction: Flick.Direction): Int = keyOptions[direction]?.size ?: 0

        override fun toString(): String = "KeyOption:$noFlickKeycode\n   > $keyOptions"
    }
}
