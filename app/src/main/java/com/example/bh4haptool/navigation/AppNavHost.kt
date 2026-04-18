package com.example.bh4haptool.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bh4haptool.core.toolkit.data.ToolboxPreferencesRepository
import com.example.bh4haptool.core.toolkit.data.ToolboxSettings
import com.example.bh4haptool.feature.catchcat.navigation.CatchCatDestination
import com.example.bh4haptool.feature.catchcat.ui.CatchCatRoute
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
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val settings by repository.settingsFlow.collectAsState(initial = ToolboxSettings())

    fun navigateToTool(tool: ToolEntry) {
        scope.launch {
            repository.recordToolLaunch(tool.id)
        }
        navController.navigate(tool.route)
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
                repository = repository
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
                keepScreenOnEnabled = settings.hostModeKeepScreenOn
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
    }
}
