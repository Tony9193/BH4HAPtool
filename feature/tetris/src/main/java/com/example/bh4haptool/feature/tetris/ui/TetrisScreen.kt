package com.example.bh4haptool.feature.tetris.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
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
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bh4haptool.feature.tetris.R
import com.example.bh4haptool.feature.tetris.domain.TetrisStatus
import com.example.bh4haptool.feature.tetris.domain.TetrominoType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TetrisRoute(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TetrisViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val haptic = LocalHapticFeedback.current

    fun trigger(action: () -> Boolean, strong: Boolean = false) {
        val success = action()
        if (success && uiState.settingsDraft.vibrationEnabled) {
            haptic.performHapticFeedback(
                if (strong) HapticFeedbackType.LongPress else HapticFeedbackType.TextHandleMove
            )
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.tetris_title)) },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text(text = stringResource(R.string.tetris_back))
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
            TetrisBoard(
                board = uiState.board,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            ControlPad(
                onLeft = { trigger(viewModel::onMoveLeft) },
                onRight = { trigger(viewModel::onMoveRight) },
                onRotate = { trigger(viewModel::onRotate) },
                onSoftDrop = { trigger(viewModel::onSoftDrop) },
                onHardDrop = { trigger(viewModel::onHardDrop, strong = true) }
            )

            ActionRow(
                status = uiState.status,
                onNewGame = viewModel::onNewGameClicked,
                onPauseResume = viewModel::onPauseResumeClicked,
                onToggleSettings = viewModel::onSettingsPanelToggle,
                showSettings = uiState.showSettingsPanel
            )

            StatusCard(uiState = uiState)

            if (uiState.showSettingsPanel) {
                SettingsPanel(
                    draft = uiState.settingsDraft,
                    onStartLevelChanged = viewModel::onStartLevelChanged,
                    onVibrationChanged = viewModel::onVibrationChanged,
                    onApply = viewModel::onApplySettings,
                    onClose = viewModel::onSettingsPanelToggle
                )
            }
        }
    }
}

@Composable
private fun TetrisBoard(
    board: List<List<Int>>,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.heightIn(min = 320.dp)) {
        if (board.isEmpty() || board.firstOrNull().isNullOrEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "加载中…")
            }
        } else {
            val rows = board.size
            val cols = board.first().size

            val ratio = if (cols > 0 && rows > 0) cols.toFloat() / rows.toFloat() else 0.5f

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(ratio, matchHeightConstraintsFirst = true)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                val cellWidth = size.width / cols
                val cellHeight = size.height / rows

                for (row in 0 until rows) {
                    for (col in 0 until cols) {
                        val color = cellColor(board[row][col])
                        drawRect(
                            color = color,
                            topLeft = Offset(col * cellWidth, row * cellHeight),
                            size = Size(cellWidth - 1f, cellHeight - 1f)
                        )
                    }
                }
            }
            }
        }
    }
}

@Composable
private fun ControlPad(
    onLeft: () -> Unit,
    onRight: () -> Unit,
    onRotate: () -> Unit,
    onSoftDrop: () -> Unit,
    onHardDrop: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = onLeft, modifier = Modifier.weight(1f)) {
                Text(text = stringResource(R.string.tetris_move_left))
            }
            Button(onClick = onRotate, modifier = Modifier.weight(1f)) {
                Text(text = stringResource(R.string.tetris_rotate))
            }
            Button(onClick = onRight, modifier = Modifier.weight(1f)) {
                Text(text = stringResource(R.string.tetris_move_right))
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(onClick = onSoftDrop, modifier = Modifier.weight(1f)) {
                Text(text = stringResource(R.string.tetris_soft_drop))
            }
            OutlinedButton(onClick = onHardDrop, modifier = Modifier.weight(1f)) {
                Text(text = stringResource(R.string.tetris_hard_drop))
            }
        }
    }
}

@Composable
private fun ActionRow(
    status: TetrisStatus,
    onNewGame: () -> Unit,
    onPauseResume: () -> Unit,
    onToggleSettings: () -> Unit,
    showSettings: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = onNewGame,
            modifier = Modifier.weight(1f)
        ) {
            Text(text = stringResource(R.string.tetris_new_game))
        }

        OutlinedButton(
            onClick = onPauseResume,
            modifier = Modifier.weight(1f),
            enabled = status == TetrisStatus.PLAYING || status == TetrisStatus.PAUSED
        ) {
            Text(
                text = if (status == TetrisStatus.PAUSED) {
                    stringResource(R.string.tetris_resume)
                } else {
                    stringResource(R.string.tetris_pause)
                }
            )
        }

        OutlinedButton(
            onClick = onToggleSettings,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = if (showSettings) {
                    stringResource(R.string.tetris_hide_settings)
                } else {
                    stringResource(R.string.tetris_show_settings)
                }
            )
        }
    }
}

@Composable
private fun StatusCard(uiState: TetrisUiState) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = stringResource(
                    R.string.tetris_status,
                    statusLabel(uiState.status)
                ),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(text = stringResource(R.string.tetris_score, uiState.score))
            Text(text = stringResource(R.string.tetris_level, uiState.level))
            Text(text = stringResource(R.string.tetris_lines, uiState.linesCleared))
            Text(text = stringResource(R.string.tetris_interval, uiState.dropIntervalMs))
            Text(text = stringResource(R.string.tetris_next_piece, pieceLabel(uiState.nextType)))
            Text(text = stringResource(R.string.tetris_best_score, uiState.highScore))
            Text(text = stringResource(R.string.tetris_best_level, uiState.bestLevel))
            Text(
                text = uiState.statusMessage,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun SettingsPanel(
    draft: TetrisSettingsDraft,
    onStartLevelChanged: (Int) -> Unit,
    onVibrationChanged: (Boolean) -> Unit,
    onApply: () -> Unit,
    onClose: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = stringResource(R.string.tetris_settings_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            IntAdjuster(
                label = stringResource(R.string.tetris_start_level),
                value = draft.startLevel,
                minValue = 1,
                maxValue = 10,
                onValueChange = onStartLevelChanged
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string.tetris_vibration))
                Switch(
                    checked = draft.vibrationEnabled,
                    onCheckedChange = onVibrationChanged
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = onApply, modifier = Modifier.weight(1f)) {
                    Text(text = stringResource(R.string.tetris_apply_settings))
                }
                OutlinedButton(onClick = onClose, modifier = Modifier.weight(1f)) {
                    Text(text = stringResource(R.string.tetris_close_settings))
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
        Text(text = label)
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
private fun statusLabel(status: TetrisStatus): String {
    return when (status) {
        TetrisStatus.READY -> stringResource(R.string.tetris_status_ready)
        TetrisStatus.PLAYING -> stringResource(R.string.tetris_status_playing)
        TetrisStatus.PAUSED -> stringResource(R.string.tetris_status_paused)
        TetrisStatus.GAME_OVER -> stringResource(R.string.tetris_status_over)
    }
}

@Composable
private fun pieceLabel(type: TetrominoType): String {
    return when (type) {
        TetrominoType.I -> "I"
        TetrominoType.O -> "O"
        TetrominoType.T -> "T"
        TetrominoType.S -> "S"
        TetrominoType.Z -> "Z"
        TetrominoType.J -> "J"
        TetrominoType.L -> "L"
    }
}

private fun cellColor(value: Int): Color {
    return when (value) {
        1 -> Color(0xFF26C6DA)
        2 -> Color(0xFFFDD835)
        3 -> Color(0xFFAB47BC)
        4 -> Color(0xFF66BB6A)
        5 -> Color(0xFFEF5350)
        6 -> Color(0xFF5C6BC0)
        7 -> Color(0xFFFFA726)
        else -> Color(0xFFE0E0E0)
    }
}
