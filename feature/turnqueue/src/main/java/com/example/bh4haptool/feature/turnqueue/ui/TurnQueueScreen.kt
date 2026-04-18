package com.example.bh4haptool.feature.turnqueue.ui

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
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bh4haptool.core.toolkit.ui.TabletTwoPane
import com.example.bh4haptool.core.toolkit.ui.rememberToolPaneMode
import com.example.bh4haptool.feature.turnqueue.R

@Composable
fun TurnQueueRoute(
    onBack: () -> Unit,
    viewModel: TurnQueueViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    TurnQueueScreen(
        uiState = uiState,
        onBack = onBack,
        onParticipantsInputChange = viewModel::updateParticipantsInput,
        onModeChange = viewModel::updateMode,
        onStart = viewModel::start,
        onNext = viewModel::next,
        onEliminateCurrent = viewModel::eliminateCurrent,
        onReset = viewModel::reset
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TurnQueueScreen(
    uiState: TurnQueueUiState,
    onBack: () -> Unit,
    onParticipantsInputChange: (String) -> Unit,
    onModeChange: (TurnQueueMode) -> Unit,
    onStart: () -> Unit,
    onNext: () -> Unit,
    onEliminateCurrent: () -> Unit,
    onReset: () -> Unit
) {
    val paneMode = rememberToolPaneMode()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.turn_queue_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.turn_queue_back)
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
                    QueueStatusPane(uiState = uiState)
                },
                secondary = {
                    QueueControlPane(
                        uiState = uiState,
                        onParticipantsInputChange = onParticipantsInputChange,
                        onModeChange = onModeChange,
                        onStart = onStart,
                        onNext = onNext,
                        onEliminateCurrent = onEliminateCurrent,
                        onReset = onReset
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
                QueueStatusPane(uiState = uiState)
                QueueControlPane(
                    uiState = uiState,
                    onParticipantsInputChange = onParticipantsInputChange,
                    onModeChange = onModeChange,
                    onStart = onStart,
                    onNext = onNext,
                    onEliminateCurrent = onEliminateCurrent,
                    onReset = onReset
                )
            }
        }
    }
}

@Composable
private fun QueueStatusPane(uiState: TurnQueueUiState) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = uiState.winner ?: uiState.participants.getOrNull(uiState.currentIndex)
                ?: stringResource(R.string.turn_queue_ready_hint),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.turn_queue_round, uiState.round),
                style = MaterialTheme.typography.bodyLarge
            )
            if (uiState.message != null) {
                Text(
                    text = uiState.message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
            uiState.participants.forEachIndexed { index, item ->
                Text(
                    text = buildString {
                        append(if (index == uiState.currentIndex && uiState.started) "-> " else "   ")
                        append(item)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QueueControlPane(
    uiState: TurnQueueUiState,
    onParticipantsInputChange: (String) -> Unit,
    onModeChange: (TurnQueueMode) -> Unit,
    onStart: () -> Unit,
    onNext: () -> Unit,
    onEliminateCurrent: () -> Unit,
    onReset: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = uiState.participantsInput,
                onValueChange = onParticipantsInputChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(R.string.turn_queue_participants)) },
                minLines = 5
            )
            val options = TurnQueueMode.entries
            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                options.forEachIndexed { index, option ->
                    SegmentedButton(
                        selected = uiState.mode == option,
                        onClick = { onModeChange(option) },
                        shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size)
                    ) {
                        Text(text = stringResource(option.labelRes()))
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = onStart, modifier = Modifier.weight(1f)) {
                    Text(text = stringResource(R.string.turn_queue_start))
                }
                OutlinedButton(onClick = onReset, modifier = Modifier.weight(1f)) {
                    Text(text = stringResource(R.string.turn_queue_reset))
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onNext,
                    enabled = uiState.started && uiState.participants.isNotEmpty() && uiState.winner == null,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = stringResource(R.string.turn_queue_next))
                }
                OutlinedButton(
                    onClick = onEliminateCurrent,
                    enabled = uiState.mode == TurnQueueMode.ELIMINATION && uiState.participants.size > 1,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = stringResource(R.string.turn_queue_eliminate))
                }
            }
        }
    }
}

private fun TurnQueueMode.labelRes(): Int = when (this) {
    TurnQueueMode.RANDOM_FIRST -> R.string.turn_queue_mode_random
    TurnQueueMode.SEQUENTIAL -> R.string.turn_queue_mode_sequential
    TurnQueueMode.ELIMINATION -> R.string.turn_queue_mode_elimination
}
