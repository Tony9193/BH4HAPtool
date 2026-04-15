package com.example.bh4haptool.feature.pomodoro.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bh4haptool.feature.pomodoro.R

@Composable
fun PomodoroRoute(
    onBack: () -> Unit,
    viewModel: PomodoroViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    PomodoroScreen(
        uiState = uiState,
        onBack = onBack,
        onStart = viewModel::start,
        onPause = viewModel::pause,
        onReset = viewModel::reset,
        onSkip = viewModel::skip,
        onUpdateSettings = viewModel::updateSettings
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PomodoroScreen(
    uiState: PomodoroUiState,
    onBack: () -> Unit,
    onStart: () -> Unit,
    onPause: () -> Unit,
    onReset: () -> Unit,
    onSkip: () -> Unit,
    onUpdateSettings: (Int, Int, Boolean, Boolean) -> Unit
) {
    val view = LocalView.current
    DisposableEffect(uiState.isRunning) {
        view.keepScreenOn = uiState.isRunning
        onDispose { view.keepScreenOn = false }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.pomodoro_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.pomodoro_back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            val progress = if (uiState.totalSeconds > 0) {
                1f - (uiState.remainingSeconds.toFloat() / uiState.totalSeconds.toFloat())
            } else 0f

            val phaseColor = if (uiState.phase == PomodoroPhase.WORK) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.tertiary
            }

            Box(
                modifier = Modifier.size(240.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = progress,
                    modifier = Modifier.fillMaxSize(),
                    strokeWidth = 16.dp,
                    color = phaseColor,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = formatTime(uiState.remainingSeconds),
                        fontSize = 56.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Crossfade(targetState = uiState.phase, label = "phase") { phase ->
                        Text(
                            text = stringResource(
                                if (phase == PomodoroPhase.WORK) R.string.pomodoro_phase_work
                                else R.string.pomodoro_phase_break
                            ),
                            fontSize = 18.sp,
                            color = phaseColor,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
            ) {
                OutlinedButton(onClick = onReset) {
                    Text(text = stringResource(R.string.pomodoro_reset))
                }
                Button(
                    onClick = if (uiState.isRunning) onPause else onStart,
                    modifier = Modifier.height(48.dp)
                ) {
                    Text(
                        text = stringResource(
                            if (uiState.isRunning) R.string.pomodoro_pause
                            else R.string.pomodoro_start
                        ),
                        fontSize = 16.sp
                    )
                }
                OutlinedButton(onClick = onSkip) {
                    Text(text = stringResource(R.string.pomodoro_skip))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.pomodoro_daily_completed, uiState.dailyCompletedCount),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(R.string.pomodoro_settings),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(
                            R.string.pomodoro_work_duration,
                            stringResource(R.string.pomodoro_minutes, uiState.workDurationMin)
                        ),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Slider(
                        value = uiState.workDurationMin.toFloat(),
                        onValueChange = {
                            onUpdateSettings(
                                it.toInt(),
                                uiState.breakDurationMin,
                                uiState.vibrationEnabled,
                                uiState.autoSwitchEnabled
                            )
                        },
                        valueRange = 1f..60f,
                        steps = 58
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = stringResource(
                            R.string.pomodoro_break_duration,
                            stringResource(R.string.pomodoro_minutes, uiState.breakDurationMin)
                        ),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Slider(
                        value = uiState.breakDurationMin.toFloat(),
                        onValueChange = {
                            onUpdateSettings(
                                uiState.workDurationMin,
                                it.toInt(),
                                uiState.vibrationEnabled,
                                uiState.autoSwitchEnabled
                            )
                        },
                        valueRange = 1f..30f,
                        steps = 28
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.pomodoro_vibration),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Switch(
                            checked = uiState.vibrationEnabled,
                            onCheckedChange = {
                                onUpdateSettings(
                                    uiState.workDurationMin,
                                    uiState.breakDurationMin,
                                    it,
                                    uiState.autoSwitchEnabled
                                )
                            }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.pomodoro_auto_switch),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Switch(
                            checked = uiState.autoSwitchEnabled,
                            onCheckedChange = {
                                onUpdateSettings(
                                    uiState.workDurationMin,
                                    uiState.breakDurationMin,
                                    uiState.vibrationEnabled,
                                    it
                                )
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun CircularProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
    strokeWidth: androidx.compose.ui.unit.Dp = 8.dp,
    color: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.primary,
    trackColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.surfaceVariant
) {
    Canvas(modifier = modifier) {
        val stroke = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
        val diameter = size.minDimension
        val radius = diameter / 2 - stroke.width / 2
        val center = Offset(size.width / 2, size.height / 2)

        drawCircle(
            color = trackColor,
            radius = radius,
            center = center,
            style = stroke
        )

        drawArc(
            color = color,
            startAngle = -90f,
            sweepAngle = 360 * progress.coerceIn(0f, 1f),
            useCenter = false,
            topLeft = center - Offset(radius, radius),
            size = Size(radius * 2, radius * 2),
            style = stroke
        )
    }
}

private fun formatTime(seconds: Int): String {
    val m = seconds / 60
    val s = seconds % 60
    return "%02d:%02d".format(m, s)
}
