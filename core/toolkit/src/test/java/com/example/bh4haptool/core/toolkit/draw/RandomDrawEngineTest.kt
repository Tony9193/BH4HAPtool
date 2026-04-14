package com.example.bh4haptool.core.toolkit.draw

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class RandomDrawEngineTest {
    private val engine = RandomDrawEngine()

    @Test
    fun draw_returnsNullForEmptyList() {
        val result = engine.draw(emptyList())

        assertNull(result)
    }

    @Test
    fun draw_returnsMemberFromCandidateList() {
        val candidates = listOf("Alice", "Bob", "Charlie")
        val result = engine.draw(candidates)

        assertEquals(candidates.size, result?.poolSize)
        assertTrue(candidates.contains(result?.winner))
    }
}
