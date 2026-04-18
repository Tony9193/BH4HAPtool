package com.example.bh4haptool.core.toolkit.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ToolboxProgressTest {

    @Test
    fun usageStatsCodec_roundTripsCounts() {
        val encoded = ToolboxProgressCodec.encodeUsageStats(
            mapOf("quick_decide" to 2, "lucky_wheel" to 5)
        )

        val decoded = ToolboxProgressCodec.decodeUsageStats(encoded)

        assertEquals(2, decoded["quick_decide"])
        assertEquals(5, decoded["lucky_wheel"])
    }

    @Test
    fun achievements_computeUnlockedFromCurrentSettings() {
        val settings = ToolboxSettings(
            minesweeperWins = 1,
            tetrisHighScore = 1200,
            sokobanCompletedLevels = 10,
            pomodoroDailyCompletedCount = 4,
            toolUsageStatsEncoded = ToolboxProgressCodec.encodeUsageStats(
                mapOf(
                    "shake_draw" to 1,
                    "quick_decide" to 1,
                    "lucky_wheel" to 1,
                    "scoreboard" to 1,
                    "turn_queue" to 1
                )
            )
        )

        val unlocked = ToolboxAchievements.computeUnlocked(settings)

        assertTrue(unlocked.contains(ToolboxAchievementId.MINESWEEPER_FIRST_WIN))
        assertTrue(unlocked.contains(ToolboxAchievementId.TETRIS_SCORE_1000))
        assertTrue(unlocked.contains(ToolboxAchievementId.SOKOBAN_COMPLETE_10))
        assertTrue(unlocked.contains(ToolboxAchievementId.POMODORO_DAILY_4))
        assertTrue(unlocked.contains(ToolboxAchievementId.USE_FIVE_TOOLS))
    }
}
