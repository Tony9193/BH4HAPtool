package com.example.bh4haptool.feature.luckywheel.domain

import javax.inject.Inject
import kotlin.random.Random

data class LuckyWheelOption(
    val label: String,
    val weight: Int = 1
)

class LuckyWheelEngine @Inject constructor() {
    fun parseOptions(rawInput: String): List<LuckyWheelOption> {
        return rawInput
            .lines()
            .mapNotNull { line ->
                val normalized = line.trim()
                if (normalized.isBlank()) {
                    return@mapNotNull null
                }

                val parts = normalized.split('|')
                val label = parts.first().trim()
                val weight = parts.getOrNull(1)?.trim()?.toIntOrNull()?.coerceAtLeast(1) ?: 1
                if (label.isBlank()) {
                    return@mapNotNull null
                }

                LuckyWheelOption(label = label, weight = weight)
            }
    }

    fun spin(
        options: List<LuckyWheelOption>,
        random: Random = Random.Default
    ): LuckyWheelOption? {
        if (options.isEmpty()) {
            return null
        }

        val totalWeight = options.sumOf { it.weight.coerceAtLeast(1) }
        var threshold = random.nextInt(totalWeight)
        options.forEach { option ->
            threshold -= option.weight.coerceAtLeast(1)
            if (threshold < 0) {
                return option
            }
        }

        return options.last()
    }
}
