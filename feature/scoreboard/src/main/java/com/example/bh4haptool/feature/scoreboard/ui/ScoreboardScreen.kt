package com.example.bh4haptool.feature.scoreboard.ui

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bh4haptool.core.toolkit.ui.TabletTwoPane
import com.example.bh4haptool.core.toolkit.ui.rememberToolPaneMode
import com.example.bh4haptool.feature.scoreboard.R

@Composable
fun ScoreboardRoute(
    onBack: () -> Unit,
    viewModel: ScoreboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    ScoreboardScreen(
        uiState = uiState,
        onBack = onBack,
        onAddTeam = viewModel::addTeam,
        onRemoveTeam = viewModel::removeTeam,
        onRenameTeam = viewModel::renameTeam,
        onChangeScore = viewModel::changeScore,
        onResetScores = viewModel::resetScores,
        onUndo = viewModel::undo
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScoreboardScreen(
    uiState: ScoreboardUiState,
    onBack: () -> Unit,
    onAddTeam: () -> Unit,
    onRemoveTeam: (Int) -> Unit,
    onRenameTeam: (Int, String) -> Unit,
    onChangeScore: (Int, Int) -> Unit,
    onResetScores: () -> Unit,
    onUndo: () -> Unit
) {
    val paneMode = rememberToolPaneMode()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.scoreboard_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.scoreboard_back)
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
                    StandingsPane(uiState = uiState)
                },
                secondary = {
                    ControlsPane(
                        uiState = uiState,
                        onAddTeam = onAddTeam,
                        onRemoveTeam = onRemoveTeam,
                        onRenameTeam = onRenameTeam,
                        onChangeScore = onChangeScore,
                        onResetScores = onResetScores,
                        onUndo = onUndo
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
                StandingsPane(uiState = uiState)
                ControlsPane(
                    uiState = uiState,
                    onAddTeam = onAddTeam,
                    onRemoveTeam = onRemoveTeam,
                    onRenameTeam = onRenameTeam,
                    onChangeScore = onChangeScore,
                    onResetScores = onResetScores,
                    onUndo = onUndo
                )
            }
        }
    }
}

@Composable
private fun StandingsPane(uiState: ScoreboardUiState) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.scoreboard_standings),
                style = MaterialTheme.typography.titleMedium
            )
            uiState.teams.sortedByDescending { it.score }.forEachIndexed { index, team ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${index + 1}. ${team.name}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = team.score.toString(),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ControlsPane(
    uiState: ScoreboardUiState,
    onAddTeam: () -> Unit,
    onRemoveTeam: (Int) -> Unit,
    onRenameTeam: (Int, String) -> Unit,
    onChangeScore: (Int, Int) -> Unit,
    onResetScores: () -> Unit,
    onUndo: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onAddTeam,
                    enabled = uiState.teams.size < 8,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = stringResource(R.string.scoreboard_add_team))
                }
                OutlinedButton(
                    onClick = onUndo,
                    enabled = uiState.canUndo,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = stringResource(R.string.scoreboard_undo))
                }
                OutlinedButton(
                    onClick = onResetScores,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = stringResource(R.string.scoreboard_reset))
                }
            }

            uiState.teams.forEach { team ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = team.name,
                            onValueChange = { onRenameTeam(team.id, it) },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text(text = stringResource(R.string.scoreboard_team_name)) },
                            singleLine = true
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = { onChangeScore(team.id, -1) },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = "-1")
                            }
                            Button(
                                onClick = { onChangeScore(team.id, 1) },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = "+1")
                            }
                            Button(
                                onClick = { onChangeScore(team.id, 3) },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = "+3")
                            }
                            OutlinedButton(
                                onClick = { onRemoveTeam(team.id) },
                                enabled = uiState.teams.size > 2,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = stringResource(R.string.scoreboard_remove_team))
                            }
                        }
                    }
                }
            }
        }
    }
}
