package com.rkbk60.quickflick.unittest

import com.rkbk60.quickflick.domain.FlickFactory
import com.rkbk60.quickflick.model.Flick
import org.amshove.kluent.shouldBeIn
import org.amshove.kluent.shouldEqual
import org.junit.Test

class FlickFactoryTest {
    private val factory =
            FlickFactory(thresholdX1 = 10, thresholdX2 = 10, thresholdY1 = 10, thresholdY2 = 10)

    @Test fun `on nothing`() {
        factory.moveTo(0, 0) shouldEqual onTap()
    }

    @Test fun `on moving to (9, 7)`() {
        factory.moveTo(9, 7) shouldEqual onTap()
    }

    @Test fun `on moving to (-25, 10)`() {
        factory.moveTo(-25, 10) shouldEqual onLeft(2)
    }

    @Test fun `on moving to (25, 35)`() {
        factory.moveTo(25, 35) shouldEqual onDown(3)
    }

    @Test fun `on moving to (44, -44)`() {
        factory.moveTo(44, -44) shouldBeIn setOf(onRight(4), onUp(4))
    }

    private fun FlickFactory.moveTo(x: Int, y: Int): Flick {
        return factory.makeWith(0, 0, x, y)
    }
}
