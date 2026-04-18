package com.example.bh4haptool.feature.eventcountdown.domain

import org.junit.Assert.assertEquals
import org.junit.Test

class EventCountdownParserTest {
    private val parser = EventCountdownParser()

    @Test
    fun parse_returnsStagesUsingTitleAndMinutesFormat() {
        val stages = parser.parse("集合|5\n游戏|20")

        assertEquals(2, stages.size)
        assertEquals("集合", stages[0].title)
        assertEquals(20, stages[1].durationMinutes)
    }
}
