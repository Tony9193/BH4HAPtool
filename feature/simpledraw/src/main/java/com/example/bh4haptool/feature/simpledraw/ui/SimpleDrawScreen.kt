package com.example.bh4haptool.feature.simpledraw.ui

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bh4haptool.core.toolkit.ui.TabletTwoPane
import com.example.bh4haptool.core.toolkit.ui.rememberToolPaneMode
import com.example.bh4haptool.feature.simpledraw.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleDrawRoute(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SimpleDrawViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val paneMode = rememberToolPaneMode()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.simple_draw_title)) },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text(text = stringResource(R.string.common_back))
                    }
                }
            )
        }
    ) { innerPadding ->
        if (paneMode.isTabletMode) {
            TabletTwoPane(
                contentPadding = innerPadding,
                primary = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.simple_draw_description),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    OutlinedTextField(
                        value = uiState.candidatesInput,
                        onValueChange = viewModel::onCandidatesInputChanged,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(text = stringResource(R.string.simple_draw_input_label)) },
                        placeholder = { Text(text = stringResource(R.string.simple_draw_input_placeholder)) },
                        minLines = 8
                    )
                    uiState.winner?.let { winner ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = stringResource(R.string.simple_draw_result_label),
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
            },
                secondary = {
                Text(
                    text = stringResource(R.string.simple_draw_count, uiState.parsedCount),
                    style = MaterialTheme.typography.bodyMedium
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = viewModel::draw,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = stringResource(R.string.common_draw))
                    }
                    OutlinedButton(
                        onClick = viewModel::clear,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = stringResource(R.string.common_clear))
                    }
                }
                uiState.message?.let { message ->
                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            })
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = stringResource(R.string.simple_draw_description),
                    style = MaterialTheme.typography.bodyMedium
                )
                OutlinedTextField(
                    value = uiState.candidatesInput,
                    onValueChange = viewModel::onCandidatesInputChanged,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = stringResource(R.string.simple_draw_input_label)) },
                    placeholder = { Text(text = stringResource(R.string.simple_draw_input_placeholder)) },
                    minLines = 4
                )
                Text(
                    text = stringResource(R.string.simple_draw_count, uiState.parsedCount),
                    style = MaterialTheme.typography.bodyMedium
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = viewModel::draw,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = stringResource(R.string.common_draw))
                    }
                    OutlinedButton(
                        onClick = viewModel::clear,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = stringResource(R.string.common_clear))
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
                                text = stringResource(R.string.simple_draw_result_label),
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
}
