package com.example.bh4haptool.feature.eventcountdown.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bh4haptool.core.toolkit.ui.TabletTwoPane
import com.example.bh4haptool.core.toolkit.ui.rememberToolPaneMode
import com.example.bh4haptool.feature.eventcountdown.R

@Composable
fun EventCountdownRoute(
    onBack: () -> Unit,
    keepScreenOnEnabled: Boolean,
    viewModel: EventCountdownViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    EventCountdownScreen(
        uiState = uiState,
        keepScreenOnEnabled = keepScreenOnEnabled,
        onBack = onBack,
        onStagesInputChange = viewModel::updateStagesInput,
        onStart = viewModel::start,
        onPause = viewModel::pause,
        onSkipStage = viewModel::skipStage,
        onReset = viewModel::reset
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EventCountdownScreen(
    uiState: EventCountdownUiState,
    keepScreenOnEnabled: Boolean,
    onBack: () -> Unit,
    onStagesInputChange: (String) -> Unit,
    onStart: () -> Unit,
    onPause: () -> Unit,
    onSkipStage: () -> Unit,
    onReset: () -> Unit
) {
    val paneMode = rememberToolPaneMode()
    val view = LocalView.current

    DisposableEffect(uiState.isRunning, keepScreenOnEnabled) {
        view.keepScreenOn = keepScreenOnEnabled && uiState.isRunning
        onDispose { view.keepScreenOn = false }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.event_countdown_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.event_countdown_back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (paneMode.isTabletMode) {
            TabletTwoPane(
                contentPadding = innerPadding,
                primary = {
                    StatusPane(
                        uiState = uiState,
                        onStart = onStart,
                        onPause = onPause,
                        onSkipStage = onSkipStage,
                        onReset = onReset
                    )
                },
                secondary = {
                    StageEditorPane(
                        uiState = uiState,
                        onStagesInputChange = onStagesInputChange
                    )
                }
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StatusPane(
                    uiState = uiState,
                    onStart = onStart,
                    onPause = onPause,
                    onSkipStage = onSkipStage,
                    onReset = onReset
                )
                StageEditorPane(
                    uiState = uiState,
                    onStagesInputChange = onStagesInputChange
                )
            }
        }
    }
}

@Composable
private fun StatusPane(
    uiState: EventCountdownUiState,
    onStart: () -> Unit,
    onPause: () -> Unit,
    onSkipStage: () -> Unit,
    onReset: () -> Unit
) {
    val currentStage = uiState.stages.getOrNull(uiState.currentStageIndex)

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = currentStage?.title ?: stringResource(R.string.event_countdown_ready_hint),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = formatCountdown(uiState.remainingSeconds),
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.primary
            )
            if (uiState.isFinished) {
                Text(text = stringResource(R.string.event_countdown_finished))
            }
            if (uiState.message != null) {
                Text(
                    text = uiState.message,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = if (uiState.isRunning) onPause else onStart,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(
                            if (uiState.isRunning) R.string.event_countdown_pause
                            else R.string.event_countdown_start
                        )
                    )
                }
                OutlinedButton(
                    onClick = onSkipStage,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = stringResource(R.string.event_countdown_skip))
                }
                OutlinedButton(
                    onClick = onReset,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = stringResource(R.string.event_countdown_reset))
                }
            }
            uiState.stages.forEachIndexed { index, stage ->
                Text(
                    text = buildString {
                        append(if (index == uiState.currentStageIndex) "-> " else "   ")
                        append(stage.title)
                        append(" / ")
                        append(stage.durationMinutes)
                        append(" min")
                    }
                )
            }
        }
    }
}

@Composable
private fun StageEditorPane(
    uiState: EventCountdownUiState,
    onStagesInputChange: (String) -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.event_countdown_stages_title),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stringResource(R.string.event_countdown_stages_desc),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            OutlinedTextField(
                value = uiState.stagesInput,
                onValueChange = onStagesInputChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(R.string.event_countdown_stages_label)) },
                minLines = 5
            )
        }
    }
}

private fun formatCountdown(totalSeconds: Int): String {
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%02d:%02d".format(minutes, seconds)
}
