package com.rkbk60.quickflick.unittest

import com.rkbk60.quickflick.domain.KeyEventOrder
import com.rkbk60.quickflick.model.AsciiKeyInfo
import com.rkbk60.quickflick.model.ModKeyInfo
import org.amshove.kluent.shouldEqual
import org.junit.Test

private typealias Unit = KeyEventOrder.Unit

class KeyEventOrderTest {
    private val num1 = AsciiKeyInfo.NUM_1
    private val meta = ModKeyInfo.META
    private val ctrl = ModKeyInfo.CTRL

    @Test
    fun `set only AsciiKeyInfo`() {
        KeyEventOrder(num1, setOf()).toList() shouldEqual listOf(
                Unit(true,  num1.code, 0),
                Unit(false, num1.code, 0)
        )
    }

    @Test
    fun `test when change main key`() {
        KeyEventOrder(AsciiKeyInfo.ENTER, setOf()).apply {
            changeMainKey(num1)
        }.toList() shouldEqual listOf(
                Unit(true,  num1.code, 0),
                Unit(false, num1.code, 0)
        )
    }

    @Test
    fun `test order which set a modifier key`() {
        KeyEventOrder(num1, setOf(meta)).toList() shouldEqual listOf(
                Unit(true,  meta.code, meta.meta),
                Unit(true,  num1.code, meta.meta),
                Unit(false, num1.code, meta.meta),
                Unit(false, meta.code, 0)
        )

    }

    @Test
    fun `test order which set some modifier keys`() {
        val list = KeyEventOrder(num1, setOf(meta, ctrl)).toList()
        if (list.contains(Unit(true, meta.code, meta.meta))) {
            list shouldEqual listOf(
                    Unit(true,  meta.code, meta.meta),
                    Unit(true,  ctrl.code, meta.meta or ctrl.meta),
                    Unit(true,  num1.code, meta.meta or ctrl.meta),
                    Unit(false, num1.code, meta.meta or ctrl.meta),
                    Unit(false, ctrl.code, meta.meta),
                    Unit(false, meta.code, 0)
            )
        } else {
            list shouldEqual listOf(
                    Unit(true,  ctrl.code, ctrl.meta),
                    Unit(true,  meta.code, ctrl.meta or meta.meta),
                    Unit(true,  num1.code, ctrl.meta or meta.meta),
                    Unit(false, num1.code, ctrl.meta or meta.meta),
                    Unit(false, meta.code, ctrl.meta),
                    Unit(false, ctrl.code, 0)
            )
        }
    }

    @Test
    fun `test when change modifier key`() {
        KeyEventOrder(num1, setOf(ctrl)).apply {
            changeModKeys(setOf(meta))
        }.toList() shouldEqual listOf(
                Unit(true,  meta.code, meta.meta),
                Unit(true,  num1.code, meta.meta),
                Unit(false, num1.code, meta.meta),
                Unit(false, meta.code, 0)
        )
    }


    @Test
    fun `test when change main & modifier keys`() {
        KeyEventOrder(ctrl, setOf()).apply {
            changeKeys(num1, setOf(meta))
        }.toList() shouldEqual listOf(
                Unit(true,  meta.code, meta.meta),
                Unit(true,  num1.code, meta.meta),
                Unit(false, num1.code, meta.meta),
                Unit(false, meta.code, 0)
        )
    }
}