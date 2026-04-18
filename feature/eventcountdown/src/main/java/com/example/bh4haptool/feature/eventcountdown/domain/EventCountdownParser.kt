package com.example.bh4haptool.feature.eventcountdown.domain

import javax.inject.Inject

data class CountdownStage(
    val title: String,
    val durationMinutes: Int
)

class EventCountdownParser @Inject constructor() {
    fun parse(rawInput: String): List<CountdownStage> {
        return rawInput
            .lines()
            .mapNotNull { line ->
                val normalized = line.trim()
                if (normalized.isBlank()) {
                    return@mapNotNull null
                }

                val parts = normalized.split('|')
                val title = parts.first().trim()
                val duration = parts.getOrNull(1)?.trim()?.toIntOrNull() ?: return@mapNotNull null
                if (title.isBlank() || duration <= 0) {
                    return@mapNotNull null
                }

                CountdownStage(title, duration)
            }
    }
}
