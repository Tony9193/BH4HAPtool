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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
    val sensorManager = remember {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
    val haptic = LocalHapticFeedback.current
    val onShake by rememberUpdatedState(newValue = {
        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        viewModel.onShakeTriggered()
    })
    val onModeChanged by rememberUpdatedState(newValue = viewModel::onSensorModeChanged)

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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(R.string.shake_draw_sensor_mode_label),
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = sensorModeLabel(uiState.sensorMode),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = stringResource(
                            R.string.shake_draw_cooldown,
                            uiState.cooldownMs
                        ),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            OutlinedTextField(
                value = uiState.candidatesInput,
                onValueChange = viewModel::onCandidatesInputChanged,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(R.string.shake_draw_input_label)) },
                placeholder = { Text(text = stringResource(R.string.shake_draw_input_placeholder)) },
                minLines = 4
            )
            Text(
                text = stringResource(R.string.shake_draw_count, uiState.parsedCount),
                style = MaterialTheme.typography.bodyMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = viewModel::manualDraw,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = stringResource(R.string.shake_draw_manual_button))
                }
                OutlinedButton(
                    onClick = viewModel::clear,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = stringResource(R.string.shake_draw_clear_button))
                }
            }
            uiState.message?.let { message ->
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            uiState.winner?.let { winner ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = stringResource(R.string.shake_draw_result_label),
                            style = MaterialTheme.typography.labelLarge
                        )
                        Text(
                            text = winner,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun sensorModeLabel(mode: ActiveSensorMode): String {
    return when (mode) {
        ActiveSensorMode.ACCELEROMETER -> stringResource(R.string.shake_draw_sensor_mode_accelerometer)
        ActiveSensorMode.GYROSCOPE_FALLBACK -> stringResource(R.string.shake_draw_sensor_mode_gyroscope)
        ActiveSensorMode.UNSUPPORTED -> stringResource(R.string.shake_draw_sensor_mode_unsupported)
    }
}
