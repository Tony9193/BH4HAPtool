package com.example.bh4haptool.ui.records

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bh4haptool.R
import com.example.bh4haptool.core.toolkit.data.ToolboxAchievementId
import com.example.bh4haptool.core.toolkit.data.ToolboxAchievements
import com.example.bh4haptool.core.toolkit.data.ToolboxPreferencesRepository
import com.example.bh4haptool.core.toolkit.data.ToolboxSettings
import com.example.bh4haptool.tool.ToolRegistry
import com.example.bh4haptool.tool.recentToolEntries
import com.example.bh4haptool.tool.toolUsageStats

@Composable
fun RecordsRoute(
    repository: ToolboxPreferencesRepository,
    onBack: () -> Unit
) {
    val settings by repository.settingsFlow.collectAsState(initial = ToolboxSettings())
    RecordsScreen(
        settings = settings,
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordsScreen(
    settings: ToolboxSettings,
    onBack: () -> Unit
) {
    val usageStats = toolUsageStats(settings)
    val achievements = ToolboxAchievements.computeUnlocked(settings)
    val topUsedEntry = usageStats.maxByOrNull { it.value }?.key?.let(ToolRegistry::entryById)
    val recentEntries = recentToolEntries(settings)
    val minesweeperTotal = settings.minesweeperWins + settings.minesweeperLosses
    val minesweeperWinRate = if (minesweeperTotal == 0) {
        0
    } else {
        settings.minesweeperWins * 100 / minesweeperTotal
    }
    val sokobanBestMaps = decodeBestMoves(settings.sokobanBestMovesEncoded)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.records_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.records_back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.padding(top = 8.dp))
            }
            item {
                SummaryCard(
                    title = stringResource(R.string.records_summary_title),
                    lines = listOf(
                        topUsedEntry?.let { stringResource(R.string.records_summary_top_tool, stringResource(it.titleRes)) }
                            ?: stringResource(R.string.records_summary_top_tool_empty),
                        stringResource(R.string.records_summary_recent_count, recentEntries.size),
                        stringResource(R.string.records_summary_tool_count, usageStats.size)
                    )
                )
            }
            item {
                SummaryCard(
                    title = stringResource(R.string.records_minesweeper_title),
                    lines = listOf(
                        stringResource(R.string.records_minesweeper_wins_losses, settings.minesweeperWins, settings.minesweeperLosses),
                        stringResource(R.string.records_minesweeper_win_rate, minesweeperWinRate),
                        stringResource(
                            R.string.records_minesweeper_best_times,
                            formatBestTime(settings.minesweeperBestTimeEasySec),
                            formatBestTime(settings.minesweeperBestTimeNormalSec),
                            formatBestTime(settings.minesweeperBestTimeHardSec)
                        )
                    )
                )
            }
            item {
                SummaryCard(
                    title = stringResource(R.string.records_tetris_title),
                    lines = listOf(
                        stringResource(R.string.records_tetris_high_score, settings.tetrisHighScore),
                        stringResource(R.string.records_tetris_best_level, settings.tetrisBestLevel)
                    )
                )
            }
            item {
                SummaryCard(
                    title = stringResource(R.string.records_sokoban_title),
                    lines = listOf(
                        stringResource(R.string.records_sokoban_completed, settings.sokobanCompletedLevels),
                        stringResource(R.string.records_sokoban_best_levels, sokobanBestMaps.size)
                    )
                )
            }
            item {
                SummaryCard(
                    title = stringResource(R.string.records_pomodoro_title),
                    lines = listOf(
                        stringResource(R.string.records_pomodoro_daily, settings.pomodoroDailyCompletedCount),
                        stringResource(
                            R.string.records_pomodoro_duration,
                            settings.pomodoroWorkDurationMin,
                            settings.pomodoroBreakDurationMin
                        )
                    )
                )
            }
            item {
                Text(
                    text = stringResource(R.string.records_achievements_title),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            items(ToolboxAchievementId.entries) { achievement ->
                AchievementCard(
                    achievement = achievement,
                    unlocked = achievements.contains(achievement)
                )
            }
            item {
                Spacer(modifier = Modifier.padding(bottom = 24.dp))
            }
        }
    }
}

@Composable
private fun SummaryCard(
    title: String,
    lines: List<String>
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            lines.forEach { line ->
                Text(
                    text = line,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun AchievementCard(
    achievement: ToolboxAchievementId,
    unlocked: Boolean
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(achievement.titleRes()),
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = stringResource(achievement.descriptionRes()),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = stringResource(
                    if (unlocked) R.string.records_achievement_unlocked
                    else R.string.records_achievement_locked
                ),
                style = MaterialTheme.typography.labelLarge,
                color = if (unlocked) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        }
    }
}

private fun ToolboxAchievementId.titleRes(): Int = when (this) {
    ToolboxAchievementId.FIRST_GAME_COMPLETE -> R.string.achievement_first_game_complete_title
    ToolboxAchievementId.MINESWEEPER_FIRST_WIN -> R.string.achievement_minesweeper_first_win_title
    ToolboxAchievementId.TETRIS_SCORE_1000 -> R.string.achievement_tetris_score_1000_title
    ToolboxAchievementId.SOKOBAN_COMPLETE_10 -> R.string.achievement_sokoban_complete_10_title
    ToolboxAchievementId.POMODORO_DAILY_4 -> R.string.achievement_pomodoro_daily_4_title
    ToolboxAchievementId.USE_FIVE_TOOLS -> R.string.achievement_use_five_tools_title
}

private fun ToolboxAchievementId.descriptionRes(): Int = when (this) {
    ToolboxAchievementId.FIRST_GAME_COMPLETE -> R.string.achievement_first_game_complete_desc
    ToolboxAchievementId.MINESWEEPER_FIRST_WIN -> R.string.achievement_minesweeper_first_win_desc
    ToolboxAchievementId.TETRIS_SCORE_1000 -> R.string.achievement_tetris_score_1000_desc
    ToolboxAchievementId.SOKOBAN_COMPLETE_10 -> R.string.achievement_sokoban_complete_10_desc
    ToolboxAchievementId.POMODORO_DAILY_4 -> R.string.achievement_pomodoro_daily_4_desc
    ToolboxAchievementId.USE_FIVE_TOOLS -> R.string.achievement_use_five_tools_desc
}

private fun formatBestTime(seconds: Int): String {
    if (seconds <= 0) {
        return "--"
    }

    val min = seconds / 60
    val sec = seconds % 60
    return "%02d:%02d".format(min, sec)
}

private fun decodeBestMoves(encoded: String): Map<Int, Int> {
    if (encoded.isBlank()) {
        return emptyMap()
    }

    return encoded
        .split(',')
        .mapNotNull { token ->
            val parts = token.split(':')
            if (parts.size != 2) {
                return@mapNotNull null
            }
            val level = parts[0].trim().toIntOrNull() ?: return@mapNotNull null
            val moves = parts[1].trim().toIntOrNull() ?: return@mapNotNull null
            level to moves
        }
        .toMap()
}
