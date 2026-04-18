package com.example.bh4haptool.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bh4haptool.R
import com.example.bh4haptool.core.toolkit.data.ToolboxPreferencesRepository
import com.example.bh4haptool.core.toolkit.data.ToolboxSettings
import com.example.bh4haptool.tool.ToolEntry
import com.example.bh4haptool.tool.ToolGroup
import com.example.bh4haptool.tool.favoriteToolIds
import com.example.bh4haptool.tool.hiddenToolIds
import com.example.bh4haptool.tool.orderedToolEntries
import com.example.bh4haptool.tool.recentToolEntries
import com.example.bh4haptool.tool.titleRes
import com.example.bh4haptool.tool.toolUsageStats

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeRoute(
    repository: ToolboxPreferencesRepository,
    onToolSelected: (ToolEntry) -> Unit,
    onSettingsClicked: () -> Unit,
    onRecordsClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val settings by repository.settingsFlow.collectAsState(initial = ToolboxSettings())
    var query by rememberSaveable { mutableStateOf("") }
    var favoritesOnly by rememberSaveable { mutableStateOf(false) }
    var recentOnly by rememberSaveable { mutableStateOf(false) }
    var tabletOnly by rememberSaveable { mutableStateOf(false) }

    val favoriteIds = remember(settings.favoriteToolIdsEncoded) { favoriteToolIds(settings) }
    val hiddenIds = remember(settings.hiddenToolIdsEncoded) { hiddenToolIds(settings) }
    val usageStats = remember(settings.toolUsageStatsEncoded) { toolUsageStats(settings) }
    val orderedVisibleEntries = remember(settings.customToolOrderEncoded, settings.hiddenToolIdsEncoded) {
        orderedToolEntries(settings).filterNot { hiddenIds.contains(it.id) }
    }
    val recentEntries = remember(settings.recentToolIdsEncoded) { recentToolEntries(settings) }

    val trimmedQuery = query.trim()
    val filteredEntries = orderedVisibleEntries.filter { tool ->
        val searchTargets = listOf(
            tool.id,
            stringResource(tool.titleRes),
            stringResource(tool.descriptionRes),
            tool.tags.joinToString(" ")
        )
        val matchesQuery = trimmedQuery.isBlank() ||
            searchTargets.any { it.contains(trimmedQuery, ignoreCase = true) }

        val matchesFavorite = !favoritesOnly || favoriteIds.contains(tool.id)
        val matchesRecent = !recentOnly || recentEntries.any { it.id == tool.id }
        val matchesTablet = !tabletOnly || tool.supportsTablet

        matchesQuery && matchesFavorite && matchesRecent && matchesTablet
    }

    val lastUsedEntry = remember(settings.lastUsedToolId) {
        orderedVisibleEntries.firstOrNull { it.id == settings.lastUsedToolId }
    }
    val commonEntries = remember(favoriteIds, usageStats, orderedVisibleEntries) {
        val favoriteEntries = orderedVisibleEntries.filter { favoriteIds.contains(it.id) }
        if (favoriteEntries.isNotEmpty()) {
            favoriteEntries
        } else {
            orderedVisibleEntries
                .sortedWith(compareByDescending<ToolEntry> { usageStats[it.id] ?: 0 }.thenBy { it.order })
                .take(4)
        }
    }
    val hotEntries = remember(usageStats, orderedVisibleEntries) {
        orderedVisibleEntries
            .mapNotNull { entry ->
                val count = usageStats[entry.id] ?: 0
                if (count > 0) entry to count else null
            }
            .sortedWith(compareByDescending<Pair<ToolEntry, Int>> { it.second }.thenBy { it.first.order })
            .take(4)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.home_title)) },
                actions = {
                    TextButton(onClick = onRecordsClicked) {
                        Text(text = stringResource(R.string.home_records_action))
                    }
                    IconButton(onClick = onSettingsClicked) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = stringResource(R.string.home_settings_action)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 170.dp),
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = stringResource(R.string.home_subtitle),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    OutlinedTextField(
                        value = query,
                        onValueChange = { query = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(text = stringResource(R.string.home_search_label)) },
                        singleLine = true
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = favoritesOnly,
                            onClick = { favoritesOnly = !favoritesOnly },
                            label = { Text(text = stringResource(R.string.home_filter_favorites)) }
                        )
                        FilterChip(
                            selected = recentOnly,
                            onClick = { recentOnly = !recentOnly },
                            label = { Text(text = stringResource(R.string.home_filter_recent)) }
                        )
                        FilterChip(
                            selected = tabletOnly,
                            onClick = { tabletOnly = !tabletOnly },
                            label = { Text(text = stringResource(R.string.home_filter_tablet)) }
                        )
                    }
                }
            }

            if (query.isBlank() && !favoritesOnly && !recentOnly && !tabletOnly && lastUsedEntry != null) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    SectionTitle(title = stringResource(R.string.home_continue_title))
                }
                item(span = { GridItemSpan(maxLineSpan) }) {
                    FeaturedToolCard(
                        tool = lastUsedEntry,
                        badge = stringResource(R.string.home_continue_badge),
                        onClick = { onToolSelected(lastUsedEntry) }
                    )
                }
            }

            if (query.isBlank() && !favoritesOnly && !recentOnly && !tabletOnly && commonEntries.isNotEmpty()) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    SectionTitle(title = stringResource(R.string.home_common_title))
                }
                items(commonEntries, key = { "common_${it.id}" }) { tool ->
                    ToolEntryCard(
                        tool = tool,
                        isFavorite = favoriteIds.contains(tool.id),
                        onClick = { onToolSelected(tool) }
                    )
                }
            }

            if (query.isBlank() && !favoritesOnly && !recentOnly && !tabletOnly && recentEntries.isNotEmpty()) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    SectionTitle(title = stringResource(R.string.home_recent_title))
                }
                items(recentEntries.take(4), key = { "recent_${it.id}" }) { tool ->
                    ToolEntryCard(
                        tool = tool,
                        isFavorite = favoriteIds.contains(tool.id),
                        onClick = { onToolSelected(tool) }
                    )
                }
            }

            if (query.isBlank() && !favoritesOnly && !recentOnly && !tabletOnly && hotEntries.isNotEmpty()) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    SectionTitle(title = stringResource(R.string.home_hot_title))
                }
                items(hotEntries, key = { "hot_${it.first.id}" }) { (tool, count) ->
                    ToolEntryCard(
                        tool = tool,
                        isFavorite = favoriteIds.contains(tool.id),
                        usageText = stringResource(R.string.home_hot_count, count),
                        onClick = { onToolSelected(tool) }
                    )
                }
            }

            ToolGroup.entries.forEach { group ->
                val groupEntries = filteredEntries.filter { it.group == group }
                if (groupEntries.isNotEmpty()) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        SectionTitle(title = stringResource(group.titleRes()))
                    }
                    items(groupEntries, key = { "group_${group.name}_${it.id}" }) { tool ->
                        ToolEntryCard(
                            tool = tool,
                            isFavorite = favoriteIds.contains(tool.id),
                            onClick = { onToolSelected(tool) }
                        )
                    }
                }
            }

            if (filteredEntries.isEmpty()) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.large),
                        color = MaterialTheme.colorScheme.surfaceContainerLow
                    ) {
                        Text(
                            text = stringResource(R.string.home_empty_state),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
private fun FeaturedToolCard(
    tool: ToolEntry,
    badge: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = badge,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = stringResource(tool.titleRes),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = stringResource(tool.descriptionRes),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
private fun ToolEntryCard(
    tool: ToolEntry,
    isFavorite: Boolean,
    usageText: String? = null,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Image(
                        painter = painterResource(id = tool.iconRes),
                        contentDescription = null,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                    Text(
                        text = stringResource(tool.titleRes),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                if (isFavorite) {
                    Text(
                        text = stringResource(R.string.home_favorite_badge),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Text(
                text = stringResource(tool.descriptionRes),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                tool.tags.take(2).forEach { tag ->
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            text = tag,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                if (tool.supportsTablet) {
                    Surface(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            text = stringResource(R.string.home_tablet_badge),
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            if (!usageText.isNullOrBlank()) {
                Text(
                    text = usageText,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
