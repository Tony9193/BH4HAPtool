package com.example.bh4haptool.feature.luckywheel.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bh4haptool.core.toolkit.ui.TabletTwoPane
import com.example.bh4haptool.core.toolkit.ui.rememberToolPaneMode
import com.example.bh4haptool.feature.luckywheel.R

@Composable
fun LuckyWheelRoute(
    onBack: () -> Unit,
    viewModel: LuckyWheelViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LuckyWheelScreen(
        uiState = uiState,
        onBack = onBack,
        onOptionsInputChange = viewModel::updateOptionsInput,
        onRemoveDrawnOptionChange = viewModel::setRemoveDrawnOption,
        onSpin = viewModel::spin,
        onResetPool = viewModel::resetPool
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LuckyWheelScreen(
    uiState: LuckyWheelUiState,
    onBack: () -> Unit,
    onOptionsInputChange: (String) -> Unit,
    onRemoveDrawnOptionChange: (Boolean) -> Unit,
    onSpin: () -> Unit,
    onResetPool: () -> Unit
) {
    val paneMode = rememberToolPaneMode()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.lucky_wheel_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.lucky_wheel_back)
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
                ResultPane(
                    uiState = uiState,
                    onSpin = onSpin,
                    onResetPool = onResetPool
                )
            },
                secondary = {
                ControlPane(
                    uiState = uiState,
                    onOptionsInputChange = onOptionsInputChange,
                    onRemoveDrawnOptionChange = onRemoveDrawnOptionChange
                )
            })
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ResultPane(
                    uiState = uiState,
                    onSpin = onSpin,
                    onResetPool = onResetPool
                )
                ControlPane(
                    uiState = uiState,
                    onOptionsInputChange = onOptionsInputChange,
                    onRemoveDrawnOptionChange = onRemoveDrawnOptionChange
                )
            }
        }
    }
}

@Composable
private fun ResultPane(
    uiState: LuckyWheelUiState,
    onSpin: () -> Unit,
    onResetPool: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Casino,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = uiState.lastResult?.label
                            ?: stringResource(R.string.lucky_wheel_ready_hint),
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }

            Button(
                onClick = onSpin,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.lucky_wheel_spin))
            }
            OutlinedButton(
                onClick = onResetPool,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.lucky_wheel_reset_pool))
            }
            if (uiState.errorMessage != null) {
                Text(
                    text = uiState.errorMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }

    if (uiState.history.isNotEmpty()) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.lucky_wheel_history),
                    style = MaterialTheme.typography.titleMedium
                )
                uiState.history.take(5).forEachIndexed { index, item ->
                    Text(text = "${index + 1}. $item")
                }
            }
        }
    }
}

@Composable
private fun ControlPane(
    uiState: LuckyWheelUiState,
    onOptionsInputChange: (String) -> Unit,
    onRemoveDrawnOptionChange: (Boolean) -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.lucky_wheel_options_title),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stringResource(R.string.lucky_wheel_options_desc),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            OutlinedTextField(
                value = uiState.optionsInput,
                onValueChange = onOptionsInputChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(R.string.lucky_wheel_options_label)) },
                minLines = 6
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = stringResource(R.string.lucky_wheel_remove_after_draw))
                    Text(
                        text = stringResource(R.string.lucky_wheel_remove_after_draw_desc),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(
                    checked = uiState.removeDrawnOption,
                    onCheckedChange = onRemoveDrawnOptionChange
                )
            }
            Text(
                text = stringResource(
                    R.string.lucky_wheel_remaining_count,
                    uiState.parsedOptions.size - if (uiState.removeDrawnOption) uiState.removedLabels.size else 0
                ),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
