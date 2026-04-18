package com.example.bh4haptool.feature.turnqueue.domain

import kotlin.random.Random
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class TurnQueueEngineTest {
    private val engine = TurnQueueEngine()

    @Test
    fun parseParticipants_filtersBlankAndDuplicateValues() {
        val participants = engine.parseParticipants("阿明\n\n阿明\nTony")

        assertEquals(listOf("阿明", "Tony"), participants)
    }

    @Test
    fun buildInitialQueue_shufflesWhenRequested() {
        val source = listOf("A", "B", "C", "D")
        val queue = engine.buildInitialQueue(source, randomFirst = true, random = Random(1))

        assertEquals(source.size, queue.size)
        assertTrue(queue.containsAll(source))
    }
}
