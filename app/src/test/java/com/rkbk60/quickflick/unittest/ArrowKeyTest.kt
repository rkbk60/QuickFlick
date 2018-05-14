
package com.rkbk60.quickflick.unittest

import com.rkbk60.quickflick.domain.ArrowKey
import com.rkbk60.quickflick.model.AsciiKeyInfo
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeIn
import org.amshove.kluent.shouldEqual
import org.junit.Test

private typealias AL = AsciiKeyInfo.LEFT
private typealias AR = AsciiKeyInfo.RIGHT

class ArrowKeyTest {
    @Test fun `test on nothing`() {
        counterObject {
            startInput(AL, setOf())
            stopInput()
        } shouldBe 1
    }

    @Test fun `test to input 5 sec`() {
        counterObject {
            val time = 5 * 1000L
            startInput(AL, setOf())
            Thread.sleep(time)
            stopInput()
            Thread.sleep(2500L)
        } shouldBeIn (92 - 3)..(92 + 3)
    }

    @Test fun `test to input 3 sec twice`() {
        counterObject {
            val time = 3 * 1000L
            startInput(AL, setOf())
            Thread.sleep(time)
            stopInput()
            startInput(AR, setOf())
            Thread.sleep(time)
            stopInput()
        } shouldBeIn (104 - 3)..(104 + 3)
    }

    @Test fun `test on PageMove mode`() {
        counterObject {
            toggleMode()
            mode shouldEqual ArrowKey.Mode.PAGE_MOVE
            startInput(AR, setOf())
            Thread.sleep(1500L)
            stopInput()
        } shouldBe 0
    }

    @Test fun `test to input on toggle mode twice`() {
        val willBe = 1 + 52
        counterObject {
            val timeOnPvm = 2 * 1000L
            val timeOnArw = 3 * 1000L

            toggleMode()
            mode shouldEqual ArrowKey.Mode.PAGE_MOVE
            startInput(AR, setOf())
            Thread.sleep(timeOnPvm)
            stopInput()

            toggleMode()
            mode shouldEqual ArrowKey.Mode.DEFAULT
            startInput(AL, setOf())
            Thread.sleep(timeOnArw)
            stopInput()
        } shouldBeIn (willBe - 3)..(willBe + 3)
    }

    private fun counterObject(applyInThread: ArrowKey.() -> Unit): Int {
        var c = 0
        val a = ArrowKey { _, _ -> c++ }
        try {
            a.apply(applyInThread)
        } catch (e: java.lang.Exception) {
            throw e
        } finally {
            a.stopInput()
        }
        return c
    }
}
