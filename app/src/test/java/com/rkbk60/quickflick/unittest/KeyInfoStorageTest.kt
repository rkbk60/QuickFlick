package com.rkbk60.quickflick.unittest

import com.rkbk60.quickflick.domain.KeyInfoStorage
import com.rkbk60.quickflick.model.AsciiKeyInfo
import com.rkbk60.quickflick.model.KeyInfo
import com.rkbk60.quickflick.model.ModKeyInfo
import com.rkbk60.quickflick.model.TriggerKeyInfo
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class KeyInfoStorageTest {
    private val storage = KeyInfoStorage()

    @Before fun beforeEachTest() {
        storage.reset()
    }

    @Test fun `checks when do nothing`() {
        storage.output() shouldEqual Pair(KeyInfo.NULL, setOf())
    }

    @Test fun `checks SMALL_C`() {
        val c = AsciiKeyInfo.SMALL_C
        storage.input(c)
        storage.output() shouldEqual Pair(c, setOf())
    }

    @Test fun `checks SMALL_B, CTRL_LOCK, ALT_LOCK`() {
        val b = AsciiKeyInfo.SMALL_B
        val c = ModKeyInfo.CTRL_LOCK
        val a = ModKeyInfo.ALT_LOCK
        storage.input(b)
        storage.input(c)
        storage.input(a)
        storage.output() shouldEqual Pair(b, setOf(a, c))
    }

    @Test fun `checks LARGE_A(contains SHIFT)`() {
        val a = AsciiKeyInfo.LARGE_A
        storage.input(a)
        storage.output() shouldEqual Pair(a, setOf(ModKeyInfo.SHIFT))
    }

    @Test fun `checks using a few times`() {
        storage.input(KeyInfo.NULL)
        storage.input(TriggerKeyInfo.KEYBOARD_LAYOUT)
        storage.output()
        storage.resetUnlessLock()
        val sh = ModKeyInfo.SHIFT
        val sp = AsciiKeyInfo.SPACE
        storage.input(sh)
        storage.input(sp)
        storage.output() shouldEqual Pair(sp, setOf(sh))
    }
}
