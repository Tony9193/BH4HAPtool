package com.example.bh4haptool.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bh4haptool.R
import com.example.bh4haptool.core.toolkit.data.AppThemeColor
import com.example.bh4haptool.core.toolkit.data.DarkThemeConfig
import com.example.bh4haptool.core.toolkit.data.ToolboxPreferencesRepository
import kotlinx.coroutines.launch
import com.example.bh4haptool.ui.theme.*

@Composable
fun SettingsRoute(
    onBack: () -> Unit,
    repository: ToolboxPreferencesRepository
) {
    val settings by repository.settingsFlow.collectAsState(initial = null)
    val scope = rememberCoroutineScope()

    settings?.let { currentSettings ->
        SettingsScreen(
            darkThemeConfig = currentSettings.darkThemeConfig,
            appThemeColor = currentSettings.appThemeColor,
            onDarkThemeConfigChange = { scope.launch { repository.updateDarkThemeConfig(it) } },
            onAppThemeColorChange = { scope.launch { repository.updateAppThemeColor(it) } },
            onBack = onBack
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    darkThemeConfig: DarkThemeConfig,
    appThemeColor: AppThemeColor,
    onDarkThemeConfigChange: (DarkThemeConfig) -> Unit,
    onAppThemeColorChange: (AppThemeColor) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
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
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                ThemeModeSection(
                    currentConfig = darkThemeConfig,
                    onConfigChange = onDarkThemeConfigChange
                )
            }
            item {
                ThemeColorSection(
                    currentColor = appThemeColor,
                    onColorChange = onAppThemeColorChange
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ThemeModeSection(
    currentConfig: DarkThemeConfig,
    onConfigChange: (DarkThemeConfig) -> Unit
) {
    Column {
        Text(
            text = stringResource(R.string.settings_theme_mode),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        val options = listOf(
            DarkThemeConfig.FOLLOW_SYSTEM to R.string.settings_theme_mode_system,
            DarkThemeConfig.LIGHT to R.string.settings_theme_mode_light,
            DarkThemeConfig.DARK to R.string.settings_theme_mode_dark
        )

        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            options.forEachIndexed { index, option ->
                SegmentedButton(
                    selected = currentConfig == option.first,
                    onClick = { onConfigChange(option.first) },
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size)
                ) {
                    Text(stringResource(option.second))
                }
            }
        }
    }
}

@Composable
private fun ThemeColorSection(
    currentColor: AppThemeColor,
    onColorChange: (AppThemeColor) -> Unit
) {
    Column {
        Text(
            text = stringResource(R.string.settings_theme_color),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        val colors = listOf(
            AppThemeColor.DEFAULT to Purple40,
            AppThemeColor.BLUE to Blue40,
            AppThemeColor.GREEN to Green40,
            AppThemeColor.GRAY to Gray40,
            AppThemeColor.RED to Red40,
            AppThemeColor.ORANGE to Orange40,
            AppThemeColor.TEAL to Teal40
        )

        val names = listOf(
            R.string.settings_color_default,
            R.string.settings_color_blue,
            R.string.settings_color_green,
            R.string.settings_color_gray,
            R.string.settings_color_red,
            R.string.settings_color_orange,
            R.string.settings_color_teal
        )

        colors.forEachIndexed { index, pair ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onColorChange(pair.first) }
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(pair.second),
                    contentAlignment = Alignment.Center
                ) {
                    if (currentColor == pair.first) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = androidx.compose.ui.graphics.Color.White
                        )
                    }
                }

                Text(
                    text = stringResource(names[index]),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}
