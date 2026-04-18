package com.example.bh4haptool.feature.luckywheel.domain

import kotlin.random.Random
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class LuckyWheelEngineTest {
    private val engine = LuckyWheelEngine()

    @Test
    fun parseOptions_supportsWeightSyntax() {
        val options = engine.parseOptions("火锅|2\n烧烤|3")

        assertEquals(2, options.size)
        assertEquals(2, options[0].weight)
        assertEquals(3, options[1].weight)
    }

    @Test
    fun spin_returnsNullForEmptyInput() {
        val result = engine.spin(emptyList())

        assertNull(result)
    }

    @Test
    fun spin_returnsOptionFromPool() {
        val options = listOf(
            LuckyWheelOption("A", 1),
            LuckyWheelOption("B", 1)
        )

        val result = engine.spin(options, Random(1))

        assertTrue(result in options)
    }
}
