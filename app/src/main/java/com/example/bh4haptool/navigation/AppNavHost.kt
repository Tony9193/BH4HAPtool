package com.example.bh4haptool.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bh4haptool.BuildConfig
import com.example.bh4haptool.R
import com.example.bh4haptool.core.toolkit.data.ToolboxPreferencesRepository
import com.example.bh4haptool.core.toolkit.data.ToolboxSettings
import com.example.bh4haptool.feature.catchcat.ui.CatchCatRoute
import com.example.bh4haptool.feature.aasplitter.navigation.AaSplitterDestination
import com.example.bh4haptool.feature.aasplitter.ui.AaSplitterRoute
import com.example.bh4haptool.feature.catchcat.navigation.CatchCatDestination
import com.example.bh4haptool.feature.eventcountdown.navigation.EventCountdownDestination
import com.example.bh4haptool.feature.eventcountdown.ui.EventCountdownRoute
import com.example.bh4haptool.feature.frisbeegroup.navigation.FrisbeeGroupDestination
import com.example.bh4haptool.feature.frisbeegroup.ui.FrisbeeGroupRoute
import com.example.bh4haptool.feature.luckywheel.navigation.LuckyWheelDestination
import com.example.bh4haptool.feature.luckywheel.ui.LuckyWheelRoute
import com.example.bh4haptool.feature.minesweeper.navigation.MinesweeperDestination
import com.example.bh4haptool.feature.minesweeper.ui.MinesweeperRoute
import com.example.bh4haptool.feature.pomodoro.navigation.PomodoroDestination
import com.example.bh4haptool.feature.pomodoro.ui.PomodoroRoute
import com.example.bh4haptool.feature.quickdecide.navigation.QuickDecideDestination
import com.example.bh4haptool.feature.quickdecide.ui.QuickDecideRoute
import com.example.bh4haptool.feature.scoreboard.navigation.ScoreboardDestination
import com.example.bh4haptool.feature.scoreboard.ui.ScoreboardRoute
import com.example.bh4haptool.feature.sokoban.navigation.SokobanDestination
import com.example.bh4haptool.feature.sokoban.ui.SokobanRoute
import com.example.bh4haptool.feature.shakedraw.navigation.ShakeDrawDestination
import com.example.bh4haptool.feature.shakedraw.ui.ShakeDrawRoute
import com.example.bh4haptool.feature.tetris.navigation.TetrisDestination
import com.example.bh4haptool.feature.tetris.ui.TetrisRoute
import com.example.bh4haptool.feature.turnqueue.navigation.TurnQueueDestination
import com.example.bh4haptool.feature.turnqueue.ui.TurnQueueRoute
import com.example.bh4haptool.tool.ToolEntry
import com.example.bh4haptool.tool.ToolRegistry
import com.example.bh4haptool.update.ReleaseUpdateInfo
import com.example.bh4haptool.update.ReleaseUpdateRepository
import com.example.bh4haptool.update.UpdateAvailableDialog
import com.example.bh4haptool.update.UpdateCheckResult
import com.example.bh4haptool.update.UpdateMessageDialog
import com.example.bh4haptool.ui.home.HomeRoute
import com.example.bh4haptool.ui.partymode.PartyModeRoute
import com.example.bh4haptool.ui.records.RecordsRoute
import com.example.bh4haptool.ui.releasenotes.ReleaseNotesRoute
import com.example.bh4haptool.ui.settings.HomeManageRoute
import com.example.bh4haptool.ui.settings.SettingsRoute
import kotlinx.coroutines.launch

@Composable
fun AppNavHost(
    repository: ToolboxPreferencesRepository,
    updateRepository: ReleaseUpdateRepository,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val settings by repository.settingsFlow.collectAsState(initial = null)
    val currentSettings = settings ?: ToolboxSettings()
    var autoCheckTriggered by rememberSaveable { mutableStateOf(false) }
    var autoUpdateRelease by remember { mutableStateOf<ReleaseUpdateInfo?>(null) }
    var autoUpdateMessage by rememberSaveable { mutableStateOf<String?>(null) }

    fun navigateToTool(tool: ToolEntry) {
        scope.launch {
            repository.recordToolLaunch(tool.id)
        }
        navController.navigate(tool.route)
    }

    LaunchedEffect(settings, autoCheckTriggered) {
        val resolvedSettings = settings ?: return@LaunchedEffect
        if (autoCheckTriggered) {
            return@LaunchedEffect
        }

        autoCheckTriggered = true
        if (!resolvedSettings.updateAutoCheckEnabled) {
            return@LaunchedEffect
        }

        if (System.currentTimeMillis() - resolvedSettings.lastUpdateCheckAtMillis < AUTO_UPDATE_CHECK_INTERVAL_MS) {
            return@LaunchedEffect
        }

        when (val result = updateRepository.checkForUpdate(BuildConfig.VERSION_NAME)) {
            is UpdateCheckResult.HasUpdate -> {
                repository.recordUpdateCheck()
                if (result.release.tagName != resolvedSettings.ignoredUpdateTag) {
                    autoUpdateRelease = result.release
                }
            }

            is UpdateCheckResult.UpToDate -> {
                repository.recordUpdateCheck()
            }

            is UpdateCheckResult.Error -> {
                repository.recordUpdateCheck()
                autoUpdateMessage = result.message
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = AppDestination.homeRoute,
        modifier = modifier
    ) {
        composable(route = AppDestination.homeRoute) {
            HomeRoute(
                repository = repository,
                onToolSelected = { tool -> navigateToTool(tool) },
                onSettingsClicked = {
                    navController.navigate(AppDestination.settingsRoute)
                },
                onRecordsClicked = {
                    navController.navigate(AppDestination.recordsRoute)
                }
            )
        }
        composable(route = AppDestination.settingsRoute) {
            SettingsRoute(
                onBack = { navController.popBackStack() },
                onManageHome = { navController.navigate(AppDestination.homeManageRoute) },
                onRecords = { navController.navigate(AppDestination.recordsRoute) },
                onReleaseNotes = { navController.navigate(AppDestination.releaseNotesRoute) },
                repository = repository,
                updateRepository = updateRepository
            )
        }
        composable(route = AppDestination.homeManageRoute) {
            HomeManageRoute(
                repository = repository,
                onBack = { navController.popBackStack() }
            )
        }
        composable(route = AppDestination.recordsRoute) {
            RecordsRoute(
                repository = repository,
                onBack = { navController.popBackStack() }
            )
        }
        composable(route = AppDestination.releaseNotesRoute) {
            ReleaseNotesRoute(
                onBack = { navController.popBackStack() }
            )
        }
        composable(route = AppDestination.partyModeRoute) {
            PartyModeRoute(
                onBack = { navController.popBackStack() },
                onOpenTool = { toolId ->
                    ToolRegistry.entryById(toolId)?.let(::navigateToTool)
                }
            )
        }
        composable(route = ShakeDrawDestination.route) {
            ShakeDrawRoute(
                onBack = { navController.popBackStack() }
            )
        }
        composable(route = LuckyWheelDestination.route) {
            LuckyWheelRoute(
                onBack = { navController.popBackStack() }
            )
        }
        composable(route = CatchCatDestination.route) {
            CatchCatRoute(
                onBack = { navController.popBackStack() }
            )
        }
        composable(route = FrisbeeGroupDestination.route) {
            FrisbeeGroupRoute(
                onBack = { navController.popBackStack() }
            )
        }
        composable(route = ScoreboardDestination.route) {
            ScoreboardRoute(
                onBack = { navController.popBackStack() }
            )
        }
        composable(route = TurnQueueDestination.route) {
            TurnQueueRoute(
                onBack = { navController.popBackStack() }
            )
        }
        composable(route = EventCountdownDestination.route) {
            EventCountdownRoute(
                onBack = { navController.popBackStack() },
                keepScreenOnEnabled = currentSettings.hostModeKeepScreenOn
            )
        }
        composable(route = MinesweeperDestination.route) {
            MinesweeperRoute(
                onBack = { navController.popBackStack() }
            )
        }
        composable(route = TetrisDestination.route) {
            TetrisRoute(
                onBack = { navController.popBackStack() }
            )
        }
        composable(route = SokobanDestination.route) {
            SokobanRoute(
                onBack = { navController.popBackStack() }
            )
        }
        composable(route = PomodoroDestination.route) {
            PomodoroRoute(
                onBack = { navController.popBackStack() }
            )
        }
        composable(route = QuickDecideDestination.route) {
            QuickDecideRoute(
                onBack = { navController.popBackStack() }
            )
        }
        composable(route = AaSplitterDestination.route) {
            AaSplitterRoute(
                onBack = { navController.popBackStack() }
            )
        }
    }

    autoUpdateRelease?.let { release ->
        UpdateAvailableDialog(
            currentVersionName = BuildConfig.VERSION_NAME,
            release = release,
            onUpdateNow = {
                if (!updateRepository.openReleasePage(context, release)) {
                    autoUpdateMessage = context.getString(R.string.update_status_browser_unavailable)
                }
                autoUpdateRelease = null
            },
            onIgnore = {
                scope.launch {
                    repository.updateIgnoredUpdateTag(release.tagName)
                }
                autoUpdateRelease = null
            },
            onDismiss = {
                autoUpdateRelease = null
            }
        )
    }

    autoUpdateMessage?.let { message ->
        UpdateMessageDialog(
            title = context.getString(R.string.settings_check_update_title),
            message = message,
            onDismiss = { autoUpdateMessage = null }
        )
    }
}

private const val AUTO_UPDATE_CHECK_INTERVAL_MS = 12 * 60 * 60 * 1000L
