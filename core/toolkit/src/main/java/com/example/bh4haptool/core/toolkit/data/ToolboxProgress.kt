package com.example.bh4haptool.core.toolkit.data

enum class ToolboxAchievementId {
    FIRST_GAME_COMPLETE,
    MINESWEEPER_FIRST_WIN,
    TETRIS_SCORE_1000,
    SOKOBAN_COMPLETE_10,
    POMODORO_DAILY_4,
    USE_FIVE_TOOLS
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

        return unlocked
    }
}
