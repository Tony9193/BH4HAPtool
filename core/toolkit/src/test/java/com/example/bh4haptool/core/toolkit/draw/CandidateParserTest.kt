package com.example.bh4haptool.core.toolkit.draw

import org.junit.Assert.assertEquals
import org.junit.Test

class CandidateParserTest {
    @Test
    fun parse_filtersBlankAndDeduplicates() {
        val parsed = CandidateParser.parse("A, B, A\n\nC；B")

        assertEquals(listOf("A", "B", "C"), parsed)
    }
}
