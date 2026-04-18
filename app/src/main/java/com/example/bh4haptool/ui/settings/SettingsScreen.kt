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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.bh4haptool.R
import com.example.bh4haptool.core.toolkit.data.AppThemeColor
import com.example.bh4haptool.core.toolkit.data.DarkThemeConfig
import com.example.bh4haptool.core.toolkit.data.ToolboxPreferencesRepository
import com.example.bh4haptool.ui.theme.Blue40
import com.example.bh4haptool.ui.theme.Gray40
import com.example.bh4haptool.ui.theme.Green40
import com.example.bh4haptool.ui.theme.Orange40
import com.example.bh4haptool.ui.theme.Purple40
import com.example.bh4haptool.ui.theme.Red40
import com.example.bh4haptool.ui.theme.Teal40
import kotlinx.coroutines.launch

@Composable
fun SettingsRoute(
    onBack: () -> Unit,
    onManageHome: () -> Unit,
    onRecords: () -> Unit,
    onReleaseNotes: () -> Unit,
    repository: ToolboxPreferencesRepository
) {
    val settings by repository.settingsFlow.collectAsState(initial = null)
    val scope = rememberCoroutineScope()
    var confirmClearRecords by rememberSaveable { mutableStateOf(false) }
    var confirmResetPreferences by rememberSaveable { mutableStateOf(false) }

    settings?.let { currentSettings ->
        SettingsScreen(
            darkThemeConfig = currentSettings.darkThemeConfig,
            appThemeColor = currentSettings.appThemeColor,
            hostModeKeepScreenOn = currentSettings.hostModeKeepScreenOn,
            onDarkThemeConfigChange = { scope.launch { repository.updateDarkThemeConfig(it) } },
            onAppThemeColorChange = { scope.launch { repository.updateAppThemeColor(it) } },
            onHostModeKeepScreenOnChange = {
                scope.launch { repository.updateHostModeKeepScreenOn(it) }
            },
            onManageHome = onManageHome,
            onRecords = onRecords,
            onReleaseNotes = onReleaseNotes,
            onClearRecords = { confirmClearRecords = true },
            onResetPreferences = { confirmResetPreferences = true },
            onBack = onBack
        )
    }

    if (confirmClearRecords) {
        ConfirmationDialog(
            title = stringResource(R.string.settings_clear_records_title),
            message = stringResource(R.string.settings_clear_records_message),
            confirmLabel = stringResource(R.string.settings_clear_records_confirm),
            onConfirm = {
                scope.launch { repository.clearRecords() }
                confirmClearRecords = false
            },
            onDismiss = { confirmClearRecords = false }
        )
    }

    if (confirmResetPreferences) {
        ConfirmationDialog(
            title = stringResource(R.string.settings_reset_all_title),
            message = stringResource(R.string.settings_reset_all_message),
            confirmLabel = stringResource(R.string.settings_reset_all_confirm),
            onConfirm = {
                scope.launch { repository.resetAllPreferences() }
                confirmResetPreferences = false
            },
            onDismiss = { confirmResetPreferences = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    darkThemeConfig: DarkThemeConfig,
    appThemeColor: AppThemeColor,
    hostModeKeepScreenOn: Boolean,
    onDarkThemeConfigChange: (DarkThemeConfig) -> Unit,
    onAppThemeColorChange: (AppThemeColor) -> Unit,
    onHostModeKeepScreenOnChange: (Boolean) -> Unit,
    onManageHome: () -> Unit,
    onRecords: () -> Unit,
    onReleaseNotes: () -> Unit,
    onClearRecords: () -> Unit,
    onResetPreferences: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.settings_back)
                        )
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
            item {
                SectionBlock(title = stringResource(R.string.settings_toolbox_section)) {
                    SettingsLinkRow(
                        title = stringResource(R.string.settings_home_manage_title),
                        subtitle = stringResource(R.string.settings_home_manage_desc),
                        onClick = onManageHome
                    )
                    SettingsLinkRow(
                        title = stringResource(R.string.settings_records_title),
                        subtitle = stringResource(R.string.settings_records_desc),
                        onClick = onRecords
                    )
                    SettingsLinkRow(
                        title = stringResource(R.string.settings_release_notes_title),
                        subtitle = stringResource(R.string.settings_release_notes_desc),
                        onClick = onReleaseNotes
                    )
                }
            }
            item {
                SectionBlock(title = stringResource(R.string.settings_host_mode_section)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = stringResource(R.string.settings_host_mode_keep_screen_on),
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = stringResource(R.string.settings_host_mode_keep_screen_on_desc),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Switch(
                            checked = hostModeKeepScreenOn,
                            onCheckedChange = onHostModeKeepScreenOnChange
                        )
                    }
                }
            }
            item {
                SectionBlock(title = stringResource(R.string.settings_data_section)) {
                    OutlinedButton(
                        onClick = onClearRecords,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = stringResource(R.string.settings_clear_records_title))
                    }
                    Button(
                        onClick = onResetPreferences,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                    ) {
                        Text(text = stringResource(R.string.settings_reset_all_title))
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
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
    SectionBlock(title = stringResource(R.string.settings_theme_mode)) {
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
    SectionBlock(title = stringResource(R.string.settings_theme_color)) {
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
                            tint = Color.White
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

@Composable
private fun SectionBlock(
    title: String,
    content: @Composable () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium
        )
        content()
    }
}

@Composable
private fun SettingsLinkRow(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = stringResource(R.string.settings_open_label),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun ConfirmationDialog(
    title: String,
    message: String,
    confirmLabel: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = { Text(text = message) },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(text = confirmLabel)
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.settings_dialog_cancel))
            }
        }
    )
}
