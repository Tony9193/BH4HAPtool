package com.example.bh4haptool.feature.minesweeper.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bh4haptool.core.toolkit.data.MinesweeperDifficulty
import com.example.bh4haptool.feature.minesweeper.R
import com.example.bh4haptool.feature.minesweeper.domain.CellMark
import com.example.bh4haptool.feature.minesweeper.domain.MinesweeperBoardConfig
import com.example.bh4haptool.feature.minesweeper.domain.MinesweeperCell
import com.example.bh4haptool.feature.minesweeper.domain.MinesweeperStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MinesweeperRoute(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MinesweeperViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.minesweeper_title)) },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text(text = stringResource(R.string.minesweeper_back))
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            BoardArea(
                uiState = uiState,
                onCellClick = viewModel::onCellClicked,
                onCellLongPress = viewModel::onCellLongPressed,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            ActionRow(
                uiState = uiState,
                onNewGame = viewModel::onNewGameClicked,
                onPauseResume = viewModel::onPauseResumeClicked,
                onToggleSettings = viewModel::onSettingsPanelToggle
            )

            StatusCard(uiState = uiState)

            Text(
                text = stringResource(R.string.minesweeper_help),
                style = MaterialTheme.typography.bodySmall
            )

            if (uiState.showSettingsPanel) {
                SettingsPanel(
                    modifier = Modifier.weight(1f, fill = false),
                    draft = uiState.settingsDraft,
                    onDifficultyChanged = viewModel::onDifficultyChanged,
                    onWidthChanged = viewModel::onDraftWidthChanged,
                    onHeightChanged = viewModel::onDraftHeightChanged,
                    onMineCountChanged = viewModel::onDraftMineCountChanged,
                    onFirstClickSafeChanged = viewModel::onFirstClickSafeChanged,
                    onQuestionMarkChanged = viewModel::onQuestionMarkChanged,
                    onAutoExpandChanged = viewModel::onAutoExpandChanged,
                    onSoundChanged = viewModel::onSoundChanged,
                    onVibrationChanged = viewModel::onVibrationChanged,
                    onApply = viewModel::onApplySettings,
                    onClose = viewModel::onSettingsPanelToggle
                )
            }
        }
    }
}

@Composable
private fun StatusCard(uiState: MinesweeperUiState) {
    val noBest = stringResource(R.string.minesweeper_no_best)
    val totalSafeCells = uiState.boardConfig.width * uiState.boardConfig.height - uiState.boardConfig.mineCount

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = stringResource(
                    R.string.minesweeper_status_label,
                    statusLabel(uiState.status)
                ),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = stringResource(R.string.minesweeper_mines_left, uiState.minesLeft),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = stringResource(R.string.minesweeper_elapsed, uiState.elapsedSeconds),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = stringResource(
                    R.string.minesweeper_revealed,
                    uiState.revealedCount,
                    totalSafeCells
                ),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = stringResource(R.string.minesweeper_wins, uiState.wins),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = stringResource(R.string.minesweeper_losses, uiState.losses),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = stringResource(
                    R.string.minesweeper_best_easy,
                    formatBestTime(uiState.bestEasySec, noBest)
                ),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = stringResource(
                    R.string.minesweeper_best_normal,
                    formatBestTime(uiState.bestNormalSec, noBest)
                ),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = stringResource(
                    R.string.minesweeper_best_hard,
                    formatBestTime(uiState.bestHardSec, noBest)
                ),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = uiState.statusMessage,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun ActionRow(
    uiState: MinesweeperUiState,
    onNewGame: () -> Unit,
    onPauseResume: () -> Unit,
    onToggleSettings: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = onNewGame,
            modifier = Modifier.weight(1f)
        ) {
            Text(text = stringResource(R.string.minesweeper_action_new_game))
        }

        OutlinedButton(
            onClick = onPauseResume,
            modifier = Modifier.weight(1f),
            enabled = uiState.status == MinesweeperStatus.PLAYING || uiState.status == MinesweeperStatus.PAUSED
        ) {
            Text(
                text = if (uiState.status == MinesweeperStatus.PAUSED) {
                    stringResource(R.string.minesweeper_action_resume)
                } else {
                    stringResource(R.string.minesweeper_action_pause)
                }
            )
        }

        OutlinedButton(
            onClick = onToggleSettings,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = if (uiState.showSettingsPanel) {
                    stringResource(R.string.minesweeper_action_hide_settings)
                } else {
                    stringResource(R.string.minesweeper_action_show_settings)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsPanel(
    modifier: Modifier = Modifier,
    draft: MinesweeperSettingsDraft,
    onDifficultyChanged: (MinesweeperDifficulty) -> Unit,
    onWidthChanged: (Int) -> Unit,
    onHeightChanged: (Int) -> Unit,
    onMineCountChanged: (Int) -> Unit,
    onFirstClickSafeChanged: (Boolean) -> Unit,
    onQuestionMarkChanged: (Boolean) -> Unit,
    onAutoExpandChanged: (Boolean) -> Unit,
    onSoundChanged: (Boolean) -> Unit,
    onVibrationChanged: (Boolean) -> Unit,
    onApply: () -> Unit,
    onClose: () -> Unit
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = stringResource(R.string.minesweeper_settings_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = stringResource(R.string.minesweeper_difficulty_label),
                style = MaterialTheme.typography.labelLarge
            )
            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                val options = listOf(
                    MinesweeperDifficulty.EASY,
                    MinesweeperDifficulty.NORMAL,
                    MinesweeperDifficulty.HARD,
                    MinesweeperDifficulty.CUSTOM
                )
                options.forEachIndexed { index, item ->
                    SegmentedButton(
                        selected = draft.difficulty == item,
                        onClick = { onDifficultyChanged(item) },
                        shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size)
                    ) {
                        Text(text = difficultyLabel(item))
                    }
                }
            }

            IntAdjuster(
                label = stringResource(R.string.minesweeper_width),
                value = draft.width,
                minValue = MinesweeperBoardConfig.MIN_WIDTH,
                maxValue = MinesweeperBoardConfig.MAX_WIDTH,
                onValueChange = onWidthChanged
            )
            IntAdjuster(
                label = stringResource(R.string.minesweeper_height),
                value = draft.height,
                minValue = MinesweeperBoardConfig.MIN_HEIGHT,
                maxValue = MinesweeperBoardConfig.MAX_HEIGHT,
                onValueChange = onHeightChanged
            )
            IntAdjuster(
                label = stringResource(R.string.minesweeper_mine_count),
                value = draft.mineCount,
                minValue = MinesweeperBoardConfig.MIN_MINES,
                maxValue = draft.width * draft.height - 1,
                onValueChange = onMineCountChanged
            )

            ToggleRow(
                title = stringResource(R.string.minesweeper_first_click_safe),
                checked = draft.firstClickSafe,
                onCheckedChange = onFirstClickSafeChanged
            )
            ToggleRow(
                title = stringResource(R.string.minesweeper_question_mark),
                checked = draft.questionMarkEnabled,
                onCheckedChange = onQuestionMarkChanged
            )
            ToggleRow(
                title = stringResource(R.string.minesweeper_auto_expand),
                checked = draft.autoExpandEnabled,
                onCheckedChange = onAutoExpandChanged
            )
            ToggleRow(
                title = stringResource(R.string.minesweeper_sound),
                checked = draft.soundEnabled,
                onCheckedChange = onSoundChanged
            )
            ToggleRow(
                title = stringResource(R.string.minesweeper_vibration),
                checked = draft.vibrationEnabled,
                onCheckedChange = onVibrationChanged
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = onApply, modifier = Modifier.weight(1f)) {
                    Text(text = stringResource(R.string.minesweeper_apply_settings))
                }
                OutlinedButton(onClick = onClose, modifier = Modifier.weight(1f)) {
                    Text(text = stringResource(R.string.minesweeper_close_settings))
                }
            }
        }
    }
}

@Composable
private fun IntAdjuster(
    label: String,
    value: Int,
    minValue: Int,
    maxValue: Int,
    onValueChange: (Int) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(text = label, style = MaterialTheme.typography.labelLarge)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = { onValueChange((value - 1).coerceAtLeast(minValue)) },
                modifier = Modifier.width(56.dp)
            ) {
                Text(text = "-")
            }

            Text(
                text = value.toString(),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )

            OutlinedButton(
                onClick = { onValueChange((value + 1).coerceAtMost(maxValue)) },
                modifier = Modifier.width(56.dp)
            ) {
                Text(text = "+")
            }
        }
    }
}

@Composable
private fun ToggleRow(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, style = MaterialTheme.typography.bodyMedium)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BoardArea(
    uiState: MinesweeperUiState,
    onCellClick: (Int, Int) -> Unit,
    onCellLongPress: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val board = uiState.cells
    val colorScheme = MaterialTheme.colorScheme
    val haptic = LocalHapticFeedback.current

    val longestSide = maxOf(uiState.boardConfig.width, uiState.boardConfig.height)
    val cellSize = when {
        longestSide <= 10 -> 34.dp
        longestSide <= 14 -> 30.dp
        longestSide <= 18 -> 26.dp
        else -> 22.dp
    }

    val canInteract = !uiState.isLoading && uiState.status != MinesweeperStatus.PAUSED

    Card(
        modifier = modifier.heightIn(min = 280.dp)
    ) {
        if (board.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "加载中…")
            }
        } else {
            val horizontalScroll = rememberScrollState()
            val verticalScroll = rememberScrollState()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorScheme.surfaceVariant)
                    .padding(8.dp)
                    .horizontalScroll(horizontalScroll)
                    .verticalScroll(verticalScroll),
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                board.forEach { row ->
                    Row(horizontalArrangement = Arrangement.spacedBy(1.dp)) {
                        row.forEach { cell ->
                            CellTile(
                                cell = cell,
                                exploded = uiState.explodedCell?.row == cell.row &&
                                    uiState.explodedCell.col == cell.col,
                                canInteract = canInteract,
                                cellSize = cellSize,
                                onClick = {
                                    if (uiState.settingsDraft.vibrationEnabled) {
                                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                    }
                                    onCellClick(cell.row, cell.col)
                                },
                                onLongClick = {
                                    if (uiState.settingsDraft.vibrationEnabled) {
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    }
                                    onCellLongPress(cell.row, cell.col)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CellTile(
    cell: MinesweeperCell,
    exploded: Boolean,
    canInteract: Boolean,
    cellSize: androidx.compose.ui.unit.Dp,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    val backgroundColor = when {
        cell.isRevealed && cell.isMine && exploded -> colorScheme.errorContainer
        cell.isRevealed -> colorScheme.surface
        else -> colorScheme.primaryContainer
    }

    val content = when {
        cell.isRevealed && cell.isMine -> "💣"
        cell.isRevealed && cell.adjacentMines > 0 -> cell.adjacentMines.toString()
        !cell.isRevealed && cell.mark == CellMark.FLAG -> "🚩"
        !cell.isRevealed && cell.mark == CellMark.QUESTION -> "?"
        else -> ""
    }

    val contentColor = when {
        cell.isRevealed && cell.isMine -> colorScheme.onErrorContainer
        cell.isRevealed && cell.adjacentMines > 0 -> adjacentColor(cell.adjacentMines)
        !cell.isRevealed && cell.mark == CellMark.FLAG -> Color(0xFFD32F2F)
        !cell.isRevealed && cell.mark == CellMark.QUESTION -> colorScheme.onPrimaryContainer
        else -> colorScheme.onSurface
    }

    Box(
        modifier = Modifier
            .size(cellSize)
            .border(
                width = 1.dp,
                color = colorScheme.outlineVariant,
                shape = RoundedCornerShape(4.dp)
            )
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(4.dp)
            )
            .combinedClickable(
                enabled = canInteract,
                onClick = onClick,
                onLongClick = onLongClick
            ),
        contentAlignment = Alignment.Center
    ) {
        if (content.isNotEmpty()) {
            Text(
                text = content,
                color = contentColor,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun statusLabel(status: MinesweeperStatus): String {
    return when (status) {
        MinesweeperStatus.READY -> stringResource(R.string.minesweeper_status_ready)
        MinesweeperStatus.PLAYING -> stringResource(R.string.minesweeper_status_playing)
        MinesweeperStatus.PAUSED -> stringResource(R.string.minesweeper_status_paused)
        MinesweeperStatus.WON -> stringResource(R.string.minesweeper_status_won)
        MinesweeperStatus.LOST -> stringResource(R.string.minesweeper_status_lost)
    }
}

@Composable
private fun difficultyLabel(difficulty: MinesweeperDifficulty): String {
    return when (difficulty) {
        MinesweeperDifficulty.EASY -> stringResource(R.string.minesweeper_difficulty_easy)
        MinesweeperDifficulty.NORMAL -> stringResource(R.string.minesweeper_difficulty_normal)
        MinesweeperDifficulty.HARD -> stringResource(R.string.minesweeper_difficulty_hard)
        MinesweeperDifficulty.CUSTOM -> stringResource(R.string.minesweeper_difficulty_custom)
    }
}

private fun formatBestTime(bestTimeSec: Int, emptyText: String): String {
    return if (bestTimeSec <= 0) {
        emptyText
    } else {
        "${bestTimeSec}s"
    }
}

private fun adjacentColor(value: Int): Color {
    return when (value) {
        1 -> Color(0xFF1976D2)
        2 -> Color(0xFF388E3C)
        3 -> Color(0xFFD32F2F)
        4 -> Color(0xFF512DA8)
        5 -> Color(0xFF6D4C41)
        6 -> Color(0xFF00838F)
        7 -> Color(0xFF455A64)
        8 -> Color(0xFF263238)
        else -> Color.Unspecified
    }
}
