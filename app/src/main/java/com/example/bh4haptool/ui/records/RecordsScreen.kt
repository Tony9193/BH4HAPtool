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
import androidx.compose.material3.LinearProgressIndicator
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
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.math.max

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
    val usageRanking = usageStats.entries
        .sortedWith(compareByDescending<Map.Entry<String, Int>> { it.value }.thenBy { it.key })
        .take(6)
    val achievements = ToolboxAchievements.computeUnlocked(settings)
    val totalLaunchCount = usageStats.values.sum()
    val topUsedEntry = usageStats.maxByOrNull { it.value }?.key?.let(ToolRegistry::entryById)
    val recentEntries = recentToolEntries(settings)
    val minesweeperTotal = settings.minesweeperWins + settings.minesweeperLosses
    val minesweeperWinRate = if (minesweeperTotal == 0) {
        0
    } else {
        settings.minesweeperWins * 100 / minesweeperTotal
    }
    val sokobanBestMaps = decodeBestMoves(settings.sokobanBestMovesEncoded)
    val todayPomodoroCount = todayPomodoroCount(settings)
    val achievementProgress = rememberAchievementProgress(
        settings = settings,
        usageStats = usageStats,
        unlockedAchievements = achievements
    )

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
                        stringResource(R.string.records_summary_launch_count, totalLaunchCount),
                        stringResource(R.string.records_summary_recent_count, recentEntries.size),
                        stringResource(R.string.records_summary_tool_count, usageStats.size),
                        if (settings.lastUsedAtMillis > 0L) {
                            stringResource(
                                R.string.records_summary_last_used_time,
                                formatTimestamp(settings.lastUsedAtMillis)
                            )
                        } else {
                            stringResource(R.string.records_summary_last_used_time_empty)
                        }
                    )
                )
            }
            item {
                UsageCard(
                    usageRanking = usageRanking,
                    totalLaunchCount = totalLaunchCount
                )
            }
            item {
                RecentToolsCard(recentEntries = recentEntries)
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
                        stringResource(R.string.records_pomodoro_daily, todayPomodoroCount),
                        stringResource(
                            R.string.records_pomodoro_last_record_date,
                            settings.pomodoroLastRecordDate.ifBlank { "--" }
                        ),
                        stringResource(
                            R.string.records_pomodoro_duration,
                            settings.pomodoroWorkDurationMin,
                            settings.pomodoroBreakDurationMin
                        )
                    )
                )
            }
            item {
                SummaryCard(
                    title = stringResource(R.string.records_aa_title),
                    lines = listOf(
                        stringResource(R.string.records_aa_settlement_count, settings.aaSettlementCount),
                        stringResource(R.string.records_aa_prepayment_count, settings.aaPrepaymentSettlementCount),
                        stringResource(
                            R.string.records_aa_total_amount,
                            formatMoney(settings.aaTotalSettledAmountCents)
                        ),
                        stringResource(
                            R.string.records_aa_history_count,
                            decodeOpaqueHistoryCount(settings.aaSettlementHistoryEncoded)
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
            item {
                Text(
                    text = stringResource(
                        R.string.records_achievements_summary,
                        achievements.size,
                        ToolboxAchievementId.entries.size
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            items(achievementProgress) { progress ->
                AchievementCard(
                    achievement = progress.achievement,
                    unlocked = progress.unlocked,
                    current = progress.current,
                    target = progress.target
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
    unlocked: Boolean,
    current: Int,
    target: Int
) {
    val progressValue = if (target <= 0) 0f else current.toFloat() / target.toFloat()

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
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

            LinearProgressIndicator(
                progress = { progressValue.coerceIn(0f, 1f) },
                modifier = Modifier.fillMaxWidth(),
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
            Text(
                text = stringResource(
                    R.string.records_achievement_progress,
                    current.coerceAtMost(target),
                    target
                ),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun UsageCard(
    usageRanking: List<Map.Entry<String, Int>>,
    totalLaunchCount: Int
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = stringResource(R.string.records_usage_title),
                style = MaterialTheme.typography.titleMedium
            )
            if (usageRanking.isEmpty()) {
                Text(
                    text = stringResource(R.string.records_usage_empty),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                usageRanking.forEachIndexed { index, entry ->
                    val toolName = ToolRegistry.entryById(entry.key)?.let { stringResource(it.titleRes) }
                        ?: entry.key
                    val ratio = if (totalLaunchCount == 0) 0 else (entry.value * 100 / totalLaunchCount)
                    Text(
                        text = stringResource(
                            R.string.records_usage_item,
                            index + 1,
                            toolName,
                            entry.value,
                            ratio
                        ),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun RecentToolsCard(recentEntries: List<com.example.bh4haptool.tool.ToolEntry>) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(R.string.records_recent_tools_title),
                style = MaterialTheme.typography.titleMedium
            )
            if (recentEntries.isEmpty()) {
                Text(
                    text = stringResource(R.string.records_recent_tools_empty),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                recentEntries.take(6).forEachIndexed { index, entry ->
                    Text(
                        text = stringResource(
                            R.string.records_recent_tools_item,
                            index + 1,
                            stringResource(entry.titleRes)
                        ),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

private data class AchievementProgressUi(
    val achievement: ToolboxAchievementId,
    val current: Int,
    val target: Int,
    val unlocked: Boolean
)

private fun rememberAchievementProgress(
    settings: ToolboxSettings,
    usageStats: Map<String, Int>,
    unlockedAchievements: Set<ToolboxAchievementId>
): List<AchievementProgressUi> {
    val usedToolCount = usageStats.keys.filter { it.isNotBlank() }.toSet().size
    val todayPomodoroCount = todayPomodoroCount(settings)

    return ToolboxAchievementId.entries.map { id ->
        val (current, target) = when (id) {
            ToolboxAchievementId.FIRST_GAME_COMPLETE -> {
                val done = if (
                    settings.minesweeperWins > 0 ||
                    settings.tetrisHighScore > 0 ||
                    settings.sokobanCompletedLevels > 0
                ) {
                    1
                } else {
                    0
                }
                done to 1
            }

            ToolboxAchievementId.MINESWEEPER_FIRST_WIN -> settings.minesweeperWins to 1
            ToolboxAchievementId.TETRIS_SCORE_1000 -> settings.tetrisHighScore to 1000
            ToolboxAchievementId.SOKOBAN_COMPLETE_10 -> settings.sokobanCompletedLevels to 10
            ToolboxAchievementId.POMODORO_DAILY_4 -> todayPomodoroCount to 4
            ToolboxAchievementId.USE_FIVE_TOOLS -> usedToolCount to 5
            ToolboxAchievementId.AA_PREPAYMENT_FIRST -> settings.aaPrepaymentSettlementCount to 1
            ToolboxAchievementId.AA_PREPAYMENT_10 -> settings.aaPrepaymentSettlementCount to 10
            ToolboxAchievementId.AA_PREPAYMENT_50 -> settings.aaPrepaymentSettlementCount to 50
        }

        AchievementProgressUi(
            achievement = id,
            current = if (unlockedAchievements.contains(id)) {
                max(target, current)
            } else {
                max(0, current)
            },
            target = target,
            unlocked = unlockedAchievements.contains(id)
        )
    }
}

private fun ToolboxAchievementId.titleRes(): Int = when (this) {
    ToolboxAchievementId.FIRST_GAME_COMPLETE -> R.string.achievement_first_game_complete_title
    ToolboxAchievementId.MINESWEEPER_FIRST_WIN -> R.string.achievement_minesweeper_first_win_title
    ToolboxAchievementId.TETRIS_SCORE_1000 -> R.string.achievement_tetris_score_1000_title
    ToolboxAchievementId.SOKOBAN_COMPLETE_10 -> R.string.achievement_sokoban_complete_10_title
    ToolboxAchievementId.POMODORO_DAILY_4 -> R.string.achievement_pomodoro_daily_4_title
    ToolboxAchievementId.USE_FIVE_TOOLS -> R.string.achievement_use_five_tools_title
    ToolboxAchievementId.AA_PREPAYMENT_FIRST -> R.string.achievement_aa_prepayment_first_title
    ToolboxAchievementId.AA_PREPAYMENT_10 -> R.string.achievement_aa_prepayment_10_title
    ToolboxAchievementId.AA_PREPAYMENT_50 -> R.string.achievement_aa_prepayment_50_title
}

private fun ToolboxAchievementId.descriptionRes(): Int = when (this) {
    ToolboxAchievementId.FIRST_GAME_COMPLETE -> R.string.achievement_first_game_complete_desc
    ToolboxAchievementId.MINESWEEPER_FIRST_WIN -> R.string.achievement_minesweeper_first_win_desc
    ToolboxAchievementId.TETRIS_SCORE_1000 -> R.string.achievement_tetris_score_1000_desc
    ToolboxAchievementId.SOKOBAN_COMPLETE_10 -> R.string.achievement_sokoban_complete_10_desc
    ToolboxAchievementId.POMODORO_DAILY_4 -> R.string.achievement_pomodoro_daily_4_desc
    ToolboxAchievementId.USE_FIVE_TOOLS -> R.string.achievement_use_five_tools_desc
    ToolboxAchievementId.AA_PREPAYMENT_FIRST -> R.string.achievement_aa_prepayment_first_desc
    ToolboxAchievementId.AA_PREPAYMENT_10 -> R.string.achievement_aa_prepayment_10_desc
    ToolboxAchievementId.AA_PREPAYMENT_50 -> R.string.achievement_aa_prepayment_50_desc
}

private fun formatBestTime(seconds: Int): String {
    if (seconds <= 0) {
        return "--"
    }

    val min = seconds / 60
    val sec = seconds % 60
    return "%02d:%02d".format(min, sec)
}

private fun formatTimestamp(timestampMillis: Long): String {
    if (timestampMillis <= 0L) {
        return "--"
    }

    val zone = ZoneId.systemDefault()
    val localDateTime = Instant.ofEpochMilli(timestampMillis).atZone(zone).toLocalDateTime()
    return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
}

private fun todayPomodoroCount(settings: ToolboxSettings): Int {
    val today = LocalDate.now().toString()
    return if (settings.pomodoroLastRecordDate == today) {
        settings.pomodoroDailyCompletedCount
    } else {
        0
    }
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

private fun formatMoney(cents: Long): String {
    val abs = kotlin.math.abs(cents)
    val sign = if (cents < 0L) "-" else ""
    val yuan = abs / 100
    val remain = abs % 100
    return "$sign$yuan.${remain.toString().padStart(2, '0')}"
}

private fun decodeOpaqueHistoryCount(encoded: String): Int {
    return com.example.bh4haptool.core.toolkit.data.ToolboxProgressCodec.decodeOpaqueList(encoded).size
}
