package com.example.bh4haptool.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.bh4haptool.R
import com.example.bh4haptool.core.toolkit.data.ToolboxPreferencesRepository
import com.example.bh4haptool.core.toolkit.data.ToolboxSettings
import com.example.bh4haptool.tool.ToolRegistry
import com.example.bh4haptool.tool.favoriteToolIds
import com.example.bh4haptool.tool.hiddenToolIds
import com.example.bh4haptool.tool.orderedToolEntries
import com.example.bh4haptool.tool.titleRes
import kotlinx.coroutines.launch

@Composable
fun HomeManageRoute(
    repository: ToolboxPreferencesRepository,
    onBack: () -> Unit
) {
    val settings by repository.settingsFlow.collectAsState(initial = ToolboxSettings())
    val scope = rememberCoroutineScope()
    val orderedEntries = orderedToolEntries(settings)
    val favoriteIds = favoriteToolIds(settings)
    val hiddenIds = hiddenToolIds(settings)

    HomeManageScreen(
        orderedEntries = orderedEntries,
        favoriteIds = favoriteIds,
        hiddenIds = hiddenIds,
        onBack = onBack,
        onReset = { scope.launch { repository.clearHomeConfiguration() } },
        onMoveUp = { toolId ->
            scope.launch {
                repository.updateCustomToolOrder(
                    moveTool(orderedEntries.map { it.id }, toolId, -1)
                )
            }
        },
        onMoveDown = { toolId ->
            scope.launch {
                repository.updateCustomToolOrder(
                    moveTool(orderedEntries.map { it.id }, toolId, 1)
                )
            }
        },
        onFavoriteChange = { toolId, favorite ->
            scope.launch { repository.setToolFavorite(toolId, favorite) }
        },
        onHiddenChange = { toolId, hidden ->
            scope.launch { repository.setToolHidden(toolId, hidden) }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeManageScreen(
    orderedEntries: List<com.example.bh4haptool.tool.ToolEntry>,
    favoriteIds: Set<String>,
    hiddenIds: Set<String>,
    onBack: () -> Unit,
    onReset: () -> Unit,
    onMoveUp: (String) -> Unit,
    onMoveDown: (String) -> Unit,
    onFavoriteChange: (String, Boolean) -> Unit,
    onHiddenChange: (String, Boolean) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.settings_home_manage_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.settings_back)
                        )
                    }
                },
                actions = {
                    TextButton(onClick = onReset) {
                        Text(text = stringResource(R.string.home_manage_reset))
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.home_manage_description),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            itemsIndexed(orderedEntries, key = { _, item -> item.id }) { index, tool ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = stringResource(tool.titleRes),
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = stringResource(tool.group.titleRes()),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = { onMoveUp(tool.id) },
                                enabled = index > 0
                            ) {
                                Text(text = stringResource(R.string.home_manage_move_up))
                            }
                            OutlinedButton(
                                onClick = { onMoveDown(tool.id) },
                                enabled = index < orderedEntries.lastIndex
                            ) {
                                Text(text = stringResource(R.string.home_manage_move_down))
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = stringResource(R.string.home_manage_favorite))
                                Text(
                                    text = stringResource(R.string.home_manage_favorite_desc),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Switch(
                                checked = favoriteIds.contains(tool.id),
                                onCheckedChange = { onFavoriteChange(tool.id, it) }
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = stringResource(R.string.home_manage_hidden))
                                Text(
                                    text = stringResource(R.string.home_manage_hidden_desc),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Switch(
                                checked = hiddenIds.contains(tool.id),
                                onCheckedChange = { onHiddenChange(tool.id, it) }
                            )
                        }
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

private fun moveTool(currentOrder: List<String>, toolId: String, delta: Int): List<String> {
    val mutable = currentOrder.toMutableList()
    val fromIndex = mutable.indexOf(toolId)
    if (fromIndex == -1) {
        return ToolRegistry.defaultOrderIds
    }

    val targetIndex = (fromIndex + delta).coerceIn(0, mutable.lastIndex)
    if (fromIndex == targetIndex) {
        return mutable
    }

    val item = mutable.removeAt(fromIndex)
    mutable.add(targetIndex, item)
    return mutable
}
