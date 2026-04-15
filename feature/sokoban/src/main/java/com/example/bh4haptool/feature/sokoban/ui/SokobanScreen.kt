package com.example.bh4haptool.feature.sokoban.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.TextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TopAppBar
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bh4haptool.core.toolkit.ui.TabletTwoPane
import com.example.bh4haptool.core.toolkit.ui.rememberToolPaneMode
import com.example.bh4haptool.feature.sokoban.R
import com.example.bh4haptool.feature.sokoban.domain.MoveDirection
import com.example.bh4haptool.feature.sokoban.domain.SokobanCoord
import com.example.bh4haptool.feature.sokoban.domain.SokobanStatus
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SokobanRoute(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SokobanViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val haptic = LocalHapticFeedback.current
    val paneMode = rememberToolPaneMode()
    var showLevelSelect by remember { mutableStateOf(false) }

    fun triggerMove(direction: MoveDirection) {
        val moved = viewModel.onMove(direction)
        if (moved && uiState.vibrationEnabled) {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.sokoban_title)) },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text(text = stringResource(R.string.sokoban_back))
                    }
                }
            )
        }
    ) { innerPadding ->
        if (paneMode.isTabletMode) {
            TabletTwoPane(
                contentPadding = innerPadding,
                primary = {
                SokobanBoard(
                    uiState = uiState,
                    onBoardTapped = { row, col ->
                        val dRow = row - uiState.player.row
                        val dCol = col - uiState.player.col

                        val direction = when {
                            abs(dRow) >= abs(dCol) && dRow != 0 -> {
                                if (dRow < 0) MoveDirection.UP else MoveDirection.DOWN
                            }

                            dCol != 0 -> {
                                if (dCol < 0) MoveDirection.LEFT else MoveDirection.RIGHT
                            }

                            else -> null
                        }

                        if (direction != null) {
                            triggerMove(direction)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            },
                secondary = {
                DirectionPad(
                    onUp = { triggerMove(MoveDirection.UP) },
                    onDown = { triggerMove(MoveDirection.DOWN) },
                    onLeft = { triggerMove(MoveDirection.LEFT) },
                    onRight = { triggerMove(MoveDirection.RIGHT) }
                )

                UtilityRow(
                    onUndo = viewModel::onUndo,
                    onReset = viewModel::onReset,
                    onPrev = viewModel::onPreviousLevel,
                    onNext = viewModel::onNextLevel
                )

                ExtraUtilityRow(
                    onPickLevel = { showLevelSelect = true },
                    onRandomLevel = viewModel::onRandomLevel
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(R.string.sokoban_vibration))
                    Switch(
                        checked = uiState.vibrationEnabled,
                        onCheckedChange = viewModel::onVibrationChanged
                    )
                }

                StatusCard(uiState = uiState)
            })
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(12.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SokobanBoard(
                    uiState = uiState,
                    onBoardTapped = { row, col ->
                        val dRow = row - uiState.player.row
                        val dCol = col - uiState.player.col

                        val direction = when {
                            abs(dRow) >= abs(dCol) && dRow != 0 -> {
                                if (dRow < 0) MoveDirection.UP else MoveDirection.DOWN
                            }

                            dCol != 0 -> {
                                if (dCol < 0) MoveDirection.LEFT else MoveDirection.RIGHT
                            }

                            else -> null
                        }

                        if (direction != null) {
                            triggerMove(direction)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 220.dp, max = 420.dp)
                )

                DirectionPad(
                    onUp = { triggerMove(MoveDirection.UP) },
                    onDown = { triggerMove(MoveDirection.DOWN) },
                    onLeft = { triggerMove(MoveDirection.LEFT) },
                    onRight = { triggerMove(MoveDirection.RIGHT) }
                )

                UtilityRow(
                    onUndo = viewModel::onUndo,
                    onReset = viewModel::onReset,
                    onPrev = viewModel::onPreviousLevel,
                    onNext = viewModel::onNextLevel
                )

                ExtraUtilityRow(
                    onPickLevel = { showLevelSelect = true },
                    onRandomLevel = viewModel::onRandomLevel
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(R.string.sokoban_vibration))
                    Switch(
                        checked = uiState.vibrationEnabled,
                        onCheckedChange = viewModel::onVibrationChanged
                    )
                }

                StatusCard(uiState = uiState)
            }
        }

        if (showLevelSelect) {
            LevelSelectionDialog(
                currentLevel = uiState.levelIndex + 1,
                totalLevels = uiState.totalLevels,
                onDismiss = { showLevelSelect = false },
                onConfirm = { levelTarget ->
                    showLevelSelect = false
                    viewModel.onJumpToLevel(levelTarget - 1)
                }
            )
        }
    }
}

@Composable
private fun SokobanBoard(
    uiState: SokobanUiState,
    onBoardTapped: (row: Int, col: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        if (uiState.boardWidth <= 0 || uiState.boardHeight <= 0) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "加载中…")
            }
        } else {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                val boardRatio = uiState.boardWidth.toFloat() / uiState.boardHeight.toFloat()
                val containerRatio = if (maxHeight > 0.dp) {
                    maxWidth / maxHeight
                } else {
                    boardRatio
                }
                val canvasWidth: Dp
                val canvasHeight: Dp

                if (boardRatio >= containerRatio) {
                    canvasWidth = maxWidth
                    canvasHeight = maxWidth / boardRatio
                } else {
                    canvasHeight = maxHeight
                    canvasWidth = maxHeight * boardRatio
                }

                Canvas(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .width(canvasWidth)
                        .height(canvasHeight)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .pointerInput(uiState.boardWidth, uiState.boardHeight, uiState.player) {
                            detectTapGestures { tap ->
                                val cellWidth = size.width / uiState.boardWidth
                                val cellHeight = size.height / uiState.boardHeight
                                if (cellWidth <= 0f || cellHeight <= 0f) {
                                    return@detectTapGestures
                                }

                                val col = (tap.x / cellWidth).toInt().coerceIn(0, uiState.boardWidth - 1)
                                val row = (tap.y / cellHeight).toInt().coerceIn(0, uiState.boardHeight - 1)
                                onBoardTapped(row, col)
                            }
                        }
                ) {
                    val cellWidth = size.width / uiState.boardWidth
                    val cellHeight = size.height / uiState.boardHeight

                    for (row in 0 until uiState.boardHeight) {
                        for (col in 0 until uiState.boardWidth) {
                            val coord = SokobanCoord(row, col)
                            val topLeft = Offset(col * cellWidth, row * cellHeight)
                            val cellSize = Size(cellWidth - 1f, cellHeight - 1f)

                            drawRect(
                                color = Color(0xFFF2EFE9),
                                topLeft = topLeft,
                                size = cellSize
                            )

                            if (coord in uiState.walls) {
                                drawRect(
                                    color = Color(0xFF5D6D7E),
                                    topLeft = topLeft,
                                    size = cellSize
                                )
                                continue
                            }

                            if (coord in uiState.targets) {
                                drawCircle(
                                    color = Color(0xFFE57373),
                                    radius = minOf(cellWidth, cellHeight) * 0.16f,
                                    center = Offset(
                                        x = topLeft.x + cellWidth / 2f,
                                        y = topLeft.y + cellHeight / 2f
                                    )
                                )
                            }

                            if (coord in uiState.boxes) {
                                drawRect(
                                    color = Color(0xFFB07B3F),
                                    topLeft = Offset(topLeft.x + cellWidth * 0.14f, topLeft.y + cellHeight * 0.14f),
                                    size = Size(cellWidth * 0.72f, cellHeight * 0.72f)
                                )
                            }

                            if (coord == uiState.player) {
                                drawCircle(
                                    color = Color(0xFF3F51B5),
                                    radius = minOf(cellWidth, cellHeight) * 0.26f,
                                    center = Offset(
                                        x = topLeft.x + cellWidth / 2f,
                                        y = topLeft.y + cellHeight / 2f
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DirectionPad(
    onUp: () -> Unit,
    onDown: () -> Unit,
    onLeft: () -> Unit,
    onRight: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(modifier = Modifier.weight(1f))
            Button(onClick = onUp, modifier = Modifier.weight(1f)) {
                Text(text = stringResource(R.string.sokoban_up))
            }
            Box(modifier = Modifier.weight(1f))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = onLeft, modifier = Modifier.weight(1f)) {
                Text(text = stringResource(R.string.sokoban_left))
            }
            Button(onClick = onDown, modifier = Modifier.weight(1f)) {
                Text(text = stringResource(R.string.sokoban_down))
            }
            Button(onClick = onRight, modifier = Modifier.weight(1f)) {
                Text(text = stringResource(R.string.sokoban_right))
            }
        }
    }
}

@Composable
private fun UtilityRow(
    onUndo: () -> Boolean,
    onReset: () -> Unit,
    onPrev: () -> Boolean,
    onNext: () -> Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedButton(onClick = { onUndo() }, modifier = Modifier.weight(1f)) {
            Text(text = stringResource(R.string.sokoban_undo))
        }
        OutlinedButton(onClick = onReset, modifier = Modifier.weight(1f)) {
            Text(text = stringResource(R.string.sokoban_reset))
        }
        OutlinedButton(onClick = { onPrev() }, modifier = Modifier.weight(1f)) {
            Text(text = stringResource(R.string.sokoban_prev_level))
        }
        OutlinedButton(onClick = { onNext() }, modifier = Modifier.weight(1f)) {
            Text(text = stringResource(R.string.sokoban_next_level))
        }
    }
}

@Composable
private fun ExtraUtilityRow(
    onPickLevel: () -> Unit,
    onRandomLevel: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedButton(onClick = onPickLevel, modifier = Modifier.weight(1f)) {
            Text(text = "选关")
        }
        OutlinedButton(onClick = onRandomLevel, modifier = Modifier.weight(1f)) {
            Text(text = "随机")
        }
    }
}

@Composable
private fun LevelSelectionDialog(
    currentLevel: Int,
    totalLevels: Int,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {
    var inputText by remember { mutableStateOf(currentLevel.toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "选择关卡")
        },
        text = {
            Column {
                Text(text = "总计 $totalLevels 关", modifier = Modifier.padding(bottom = 8.dp))
                TextField(
                    value = inputText,
                    onValueChange = { inputText = it.filter { char -> char.isDigit() } },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    label = { Text("关卡序号") }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val target = inputText.toIntOrNull() ?: currentLevel
                    val clamped = target.coerceIn(1, totalLevels)
                    onConfirm(clamped)
                }
            ) {
                Text("确定")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("取消")
            }
        }
    )
}

@Composable
private fun StatusCard(uiState: SokobanUiState) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = stringResource(
                    R.string.sokoban_level_progress,
                    uiState.levelIndex + 1,
                    uiState.totalLevels,
                    uiState.levelName
                ),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(text = stringResource(R.string.sokoban_status, statusLabel(uiState.status)))
            Text(text = stringResource(R.string.sokoban_moves, uiState.moveCount))
            Text(text = stringResource(R.string.sokoban_completed, uiState.completedLevels))
            Text(
                text = stringResource(
                    R.string.sokoban_best_moves,
                    if (uiState.currentLevelBestMoves <= 0) {
                        stringResource(R.string.sokoban_no_best)
                    } else {
                        uiState.currentLevelBestMoves.toString()
                    }
                )
            )
            Text(
                text = stringResource(R.string.sokoban_help),
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
private fun statusLabel(status: SokobanStatus): String {
    return when (status) {
        SokobanStatus.PLAYING -> stringResource(R.string.sokoban_status_playing)
        SokobanStatus.WON -> stringResource(R.string.sokoban_status_won)
    }
}
