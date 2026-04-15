package com.example.bh4haptool.core.toolkit.draw

/**
 * Parses user input into a de-duplicated candidate list.
 * Supports both English and Chinese separators.
 */
object CandidateParser {
    private val separators = Regex("[,，;；\\n\\r\\t]+")

    fun parse(rawInput: String): List<String> {
        return rawInput
            .split(separators)
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .distinct()
    }
}
