package com.example.bh4haptool.update

object ReleaseVersionComparator {

    fun isNewer(candidate: String, current: String): Boolean {
        return compare(candidate, current) > 0
    }

    fun compare(left: String, right: String): Int {
        val leftSegments = parseSegments(left)
        val rightSegments = parseSegments(right)
        val maxSize = maxOf(leftSegments.size, rightSegments.size)

        for (index in 0 until maxSize) {
            val leftValue = leftSegments.getOrElse(index) { 0 }
            val rightValue = rightSegments.getOrElse(index) { 0 }
            if (leftValue != rightValue) {
                return leftValue.compareTo(rightValue)
            }
        }

        return 0
    }

    private fun parseSegments(version: String): List<Int> {
        val normalized = version
            .trim()
            .removePrefix("v")
            .removePrefix("V")
            .substringBefore('-')

        if (normalized.isBlank()) {
            return emptyList()
        }

        return normalized.split('.').map { segment ->
            segment.takeWhile(Char::isDigit).toIntOrNull() ?: 0
        }
    }
}
