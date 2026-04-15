package com.example.bh4haptool.feature.shakedraw.ui

import android.content.Context
import android.hardware.SensorManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bh4haptool.core.toolkit.ui.TabletTwoPane
import com.example.bh4haptool.core.toolkit.ui.rememberToolPaneMode
import com.example.bh4haptool.feature.shakedraw.R
import com.example.bh4haptool.feature.shakedraw.sensor.ActiveSensorMode
import com.example.bh4haptool.feature.shakedraw.sensor.ShakeDetector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShakeDrawRoute(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ShakeDrawViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val paneMode = rememberToolPaneMode()
    val sensorManager = remember {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
    val haptic = LocalHapticFeedback.current
    val onShake by rememberUpdatedState(newValue = {
        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        viewModel.onShakeTriggered()
    })
    val onModeChanged by rememberUpdatedState(newValue = { mode: ActiveSensorMode ->
        viewModel.onShakeAvailabilityChanged(mode != ActiveSensorMode.UNSUPPORTED)
    })

    val shakeDetector = remember(
        sensorManager,
        uiState.gyroscopeThreshold,
        uiState.accelerometerThreshold,
        uiState.cooldownMs
    ) {
        ShakeDetector(
            sensorManager = sensorManager,
            gyroscopeThreshold = uiState.gyroscopeThreshold,
            accelerometerThreshold = uiState.accelerometerThreshold,
            cooldownMs = uiState.cooldownMs,
            onShake = onShake,
            onModeChanged = onModeChanged
        )
    }

    DisposableEffect(shakeDetector) {
        shakeDetector.start()
        onDispose {
            shakeDetector.stop()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.shake_draw_title)) },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text(text = stringResource(R.string.shake_draw_back))
                    }
                }
            )
        }
    ) { innerPadding ->
        if (paneMode.isTabletMode) {
            TabletTwoPane(
                contentPadding = innerPadding,
                primary = {
                MainPanel(
                    uiState = uiState,
                    onModeChanged = viewModel::onModeChanged,
                    onCandidatesInputChanged = viewModel::onCandidatesInputChanged,
                    onRangeStartChanged = viewModel::onRangeStartChanged,
                    onRangeEndChanged = viewModel::onRangeEndChanged,
                    expandedInput = true
                )
            },
                secondary = {
                ControlPanel(
                    uiState = uiState,
                    onManualDraw = viewModel::manualDraw,
                    onClear = viewModel::clear
                )
            })
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MainPanel(
                    uiState = uiState,
                    onModeChanged = viewModel::onModeChanged,
                    onCandidatesInputChanged = viewModel::onCandidatesInputChanged,
                    onRangeStartChanged = viewModel::onRangeStartChanged,
                    onRangeEndChanged = viewModel::onRangeEndChanged,
                    expandedInput = false
                )
                ControlPanel(
                    uiState = uiState,
                    onManualDraw = viewModel::manualDraw,
                    onClear = viewModel::clear
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainPanel(
    uiState: ShakeDrawUiState,
    onModeChanged: (ShakeDrawMode) -> Unit,
    onCandidatesInputChanged: (String) -> Unit,
    onRangeStartChanged: (String) -> Unit,
    onRangeEndChanged: (String) -> Unit,
    expandedInput: Boolean
) {
    val modes = listOf(ShakeDrawMode.CANDIDATES, ShakeDrawMode.RANDOM_NUMBER)

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = stringResource(R.string.shake_draw_description),
            style = MaterialTheme.typography.bodyMedium
        )

        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            modes.forEachIndexed { index, mode ->
                SegmentedButton(
                    selected = uiState.mode == mode,
                    onClick = { onModeChanged(mode) },
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = modes.size)
                ) {
                    Text(
                        text = stringResource(
                            if (mode == ShakeDrawMode.CANDIDATES) {
                                R.string.shake_draw_mode_candidates
                            } else {
                                R.string.shake_draw_mode_numbers
                            }
                        )
                    )
                }
            }
        }

        if (uiState.mode == ShakeDrawMode.CANDIDATES) {
            OutlinedTextField(
                value = uiState.candidatesInput,
                onValueChange = onCandidatesInputChanged,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(R.string.shake_draw_input_label)) },
                placeholder = { Text(text = stringResource(R.string.shake_draw_input_placeholder)) },
                minLines = if (expandedInput) 8 else 4
            )
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = uiState.rangeStartInput,
                    onValueChange = onRangeStartChanged,
                    modifier = Modifier.weight(1f),
                    label = { Text(text = stringResource(R.string.shake_draw_range_start)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
                OutlinedTextField(
                    value = uiState.rangeEndInput,
                    onValueChange = onRangeEndChanged,
                    modifier = Modifier.weight(1f),
                    label = { Text(text = stringResource(R.string.shake_draw_range_end)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
            }
        }

        uiState.winner?.let { winner ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.shake_draw_result_label),
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = winner,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun ControlPanel(
    uiState: ShakeDrawUiState,
    onManualDraw: () -> Unit,
    onClear: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = if (uiState.mode == ShakeDrawMode.CANDIDATES) {
                stringResource(R.string.shake_draw_count, uiState.parsedCount)
            } else {
                stringResource(
                    R.string.shake_draw_range_preview,
                    uiState.rangeStartInput.ifBlank { "-" },
                    uiState.rangeEndInput.ifBlank { "-" }
                )
            },
            style = MaterialTheme.typography.bodyMedium
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onManualDraw,
                modifier = Modifier.weight(1f)
            ) {
                Text(text = stringResource(R.string.shake_draw_manual_button))
            }
            OutlinedButton(
                onClick = onClear,
                modifier = Modifier.weight(1f)
            ) {
                Text(text = stringResource(R.string.shake_draw_clear_button))
            }
        }

        Text(
            text = stringResource(R.string.shake_draw_cooldown, uiState.cooldownMs),
            style = MaterialTheme.typography.bodySmall
        )

        if (!uiState.isShakeSupported) {
            Text(
                text = stringResource(R.string.shake_draw_shake_unavailable),
                style = MaterialTheme.typography.bodySmall
            )
        }

        uiState.message?.let { message ->
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
