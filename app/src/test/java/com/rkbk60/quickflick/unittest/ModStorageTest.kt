package com.rkbk60.quickflick.unittest

import com.rkbk60.quickflick.domain.ModKeyStorage
import com.rkbk60.quickflick.model.*
import org.amshove.kluent.*
import org.junit.Before
import org.junit.Test

private typealias M = ModKeyInfo

class ModStorageTest {
    private val storage = ModKeyStorage()

    private val off  = null
    private val on   = ModKeyInfo.ALT
    private val lock = ModKeyInfo.ALT_LOCK

    @Before fun beforeEachTest() {
        storage.reset()
    }

    @Test fun `checks when do nothing`() {
        storage.toSet().shouldBeEmpty()
    }

    @Test fun `checks when stock main mod keys`() {
        checkMain(off,  on,   on)
        checkMain(off,  lock, lock)
        checkMain(on,   on,   off)
        checkMain(on,   lock, lock)
        checkMain(lock, on,   off)
        checkMain(lock, lock, off)
    }

    @Test fun `checks when stock sub mod keys`() {
        checkSub(off,  on,   on)
        checkSub(off,  lock, lock)
        checkSub(on,   on,   on)
        checkSub(on,   lock, on)
        checkSub(lock, on,   lock)
        checkSub(lock, lock, lock)
    }

    @Test fun `checks resetUnlessLock`() {
        storage.update(ModKeyInfo.SHIFT)
        storage.update(ModKeyInfo.ALT_LOCK)
        storage.update(ModKeyInfo.CTRL_LOCK)
        storage.resetUnlessLock()
        storage.toSet() shouldEqual setOf(ModKeyInfo.ALT_LOCK, ModKeyInfo.CTRL_LOCK)
    }

    @Test fun `checks using a few times`() {
        storage.update(ModKeyInfo.SHIFT, false)
        storage.resetUnlessLock()
        val m1 = ModKeyInfo.ALT
        val m2 = ModKeyInfo.CTRL_LOCK
        storage.apply {
            update(m1, false)
            update(m2, false)
            toSet() shouldEqual setOf(m1, m2)
            resetUnlessLock()
        }
        val m3 = ModKeyInfo.SHIFT_LOCK
        storage.update(m3, true)
        storage.toSet() shouldEqual setOf(m2, m3)
    }

    private fun checkUpdate(current: M?, input: M, isSubMod: Boolean, willBe: M?) {
        with(storage) {
            if (current is M) {
                update(current)
            }
            update(input, isSubMod)
            if (willBe is M) {
                toSet() shouldEqual setOf(willBe)
            } else {
                toSet().shouldBeEmpty()
            }
            reset()
            reset()
        }
    }

    private fun checkMain(current: M?, input: M, willBe: M?) {
        return checkUpdate(current, input, false, willBe)
    }

    private fun checkSub(current: M?, input: M, willBe: M?) {
        return checkUpdate(current, input, true, willBe)
    }
}
