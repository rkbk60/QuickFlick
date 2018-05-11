package com.rkbk60.quickflick.unittest

import com.rkbk60.quickflick.domain.Keymap
import com.rkbk60.quickflick.model.*
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldEqual
import org.junit.Test

private typealias D = Flick.Direction

class KeymapLogicTest {
    private val nil = KeyInfo.NULL
    private val lv0 = AsciiKeyInfo.NUM_0
    private val lv1 = AsciiKeyInfo.NUM_1
    private val lv2 = AsciiKeyInfo.NUM_2
    private val lv3 = AsciiKeyInfo.NUM_3
    private val keymap = Keymap().apply {
        put(0, D.NONE, lv0)
        put(0, D.LEFT, lv1)
        put(0, D.LEFT, lv2)
        put(0, D.LEFT, lv3)
        put(0, D.DOWN, lv0)
        put(1, D.DOWN, lv0)
        put(1, D.LEFT, lv0)
    }

    // getKey()

    @Test
    fun `checks getKey() on tap`() {
        keymap.getKey(0, onTap()) shouldEqual lv0
    }

    @Test fun `checks getKey() on fling`() {
        keymap.getKey(0, onLeft(1)) shouldEqual lv1
    }

    @Test fun `checks getKey() if distance is minus`() {
        keymap.getKey(0, onLeft(Int.MAX_VALUE)) shouldEqual lv3
    }

    @Test fun `checks getKey() if direction is undefined one`() {
        keymap.getKey(0, onDown(1)) shouldEqual lv0
    }

    @Test fun `checks getKey() if index is undefined one`() {
        keymap.getKey(Int.MAX_VALUE, onLeft(1)) shouldEqual nil
    }

    // getMaxDistance()

    @Test fun `checks getMaxDistance(0, None)`() {
        keymap.getMaxDistance(0, D.NONE) shouldBe 0
    }

    @Test fun `checks getMaxDistance(0, Left)`() {
        keymap.getMaxDistance(0, D.LEFT) shouldBe 3
    }

    @Test fun `checks getMaxDistance(0, DOWN)`() {
        keymap.getMaxDistance(0, D.DOWN) shouldBe 1
    }

    @Test fun `checks getMaxDistance(1, Right)`() {
        keymap.getMaxDistance(1, D.RIGHT) shouldBe 0
    }

    @Test fun `checks getMaxDistance()`() {
        keymap.getMaxDistance() shouldBe 3
    }
}
