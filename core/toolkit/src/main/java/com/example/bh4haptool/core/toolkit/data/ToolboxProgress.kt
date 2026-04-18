package com.example.bh4haptool.core.toolkit.data

enum class ToolboxAchievementId {
    FIRST_GAME_COMPLETE,
    MINESWEEPER_FIRST_WIN,
    TETRIS_SCORE_1000,
    SOKOBAN_COMPLETE_10,
    POMODORO_DAILY_4,
    USE_FIVE_TOOLS,
    AA_PREPAYMENT_FIRST,
    AA_PREPAYMENT_10,
    AA_PREPAYMENT_50
}

object ToolboxProgressCodec {
    fun decodeIdList(encoded: String): List<String> {
        if (encoded.isBlank()) {
            return emptyList()
        }

        return encoded
            .split(',')
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .distinct()
    }

    fun encodeIdList(values: List<String>): String {
        return values
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .distinct()
            .joinToString(separator = ",")
    }

    fun decodeUsageStats(encoded: String): Map<String, Int> {
        if (encoded.isBlank()) {
            return emptyMap()
        }

        return encoded
            .split(',')
            .mapNotNull { token ->
                val pair = token.split(':')
                if (pair.size != 2) {
                    return@mapNotNull null
                }

                val toolId = pair[0].trim()
                val count = pair[1].trim().toIntOrNull() ?: return@mapNotNull null
                if (toolId.isBlank()) {
                    return@mapNotNull null
                }

                toolId to count
            }
            .toMap()
    }

    fun encodeUsageStats(values: Map<String, Int>): String {
        return values
            .filterValues { it > 0 }
            .toSortedMap()
            .entries
            .joinToString(separator = ",") { (toolId, count) -> "$toolId:$count" }
    }

    fun decodeAchievements(encoded: String): Set<ToolboxAchievementId> {
        if (encoded.isBlank()) {
            return emptySet()
        }

        return encoded
            .split(',')
            .mapNotNull { token ->
                runCatching { ToolboxAchievementId.valueOf(token.trim()) }.getOrNull()
            }
            .toSet()
    }

    fun encodeAchievements(values: Set<ToolboxAchievementId>): String {
        return values
            .map { it.name }
            .sorted()
            .joinToString(separator = ",")
    }

    fun decodeOpaqueList(encoded: String): List<String> {
        if (encoded.isBlank()) {
            return emptyList()
        }

        return encoded.split(',')
            .mapNotNull { token ->
                runCatching {
                    String(java.util.Base64.getDecoder().decode(token), Charsets.UTF_8)
                }.getOrNull()
            }
    }

    fun encodeOpaqueList(values: List<String>): String {
        return values
            .filter { it.isNotBlank() }
            .joinToString(separator = ",") { value ->
                java.util.Base64.getEncoder().encodeToString(value.toByteArray(Charsets.UTF_8))
            }
    }
}

object ToolboxAchievements {
    fun computeUnlocked(settings: ToolboxSettings): Set<ToolboxAchievementId> {
        val unlocked = mutableSetOf<ToolboxAchievementId>()
        val usageStats = ToolboxProgressCodec.decodeUsageStats(settings.toolUsageStatsEncoded)

        if (settings.minesweeperWins > 0 ||
            settings.tetrisHighScore > 0 ||
            settings.sokobanCompletedLevels > 0
        ) {
            unlocked += ToolboxAchievementId.FIRST_GAME_COMPLETE
        }

        if (settings.minesweeperWins > 0) {
            unlocked += ToolboxAchievementId.MINESWEEPER_FIRST_WIN
        }

        if (settings.tetrisHighScore >= 1000) {
            unlocked += ToolboxAchievementId.TETRIS_SCORE_1000
        }

        if (settings.sokobanCompletedLevels >= 10) {
            unlocked += ToolboxAchievementId.SOKOBAN_COMPLETE_10
        }

        if (settings.pomodoroDailyCompletedCount >= 4) {
            unlocked += ToolboxAchievementId.POMODORO_DAILY_4
        }

        val usedTools = usageStats.keys.filter { it.isNotBlank() }.toSet()
        if (usedTools.size >= 5) {
            unlocked += ToolboxAchievementId.USE_FIVE_TOOLS
        }

        if (settings.aaPrepaymentSettlementCount >= 1) {
            unlocked += ToolboxAchievementId.AA_PREPAYMENT_FIRST
        }

        if (settings.aaPrepaymentSettlementCount >= 10) {
            unlocked += ToolboxAchievementId.AA_PREPAYMENT_10
        }

        if (settings.aaPrepaymentSettlementCount >= 50) {
            unlocked += ToolboxAchievementId.AA_PREPAYMENT_50
        }

        return unlocked
    }
}
