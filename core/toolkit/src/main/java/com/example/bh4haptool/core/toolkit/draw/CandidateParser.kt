package com.example.bh4haptool.core.toolkit.draw

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
