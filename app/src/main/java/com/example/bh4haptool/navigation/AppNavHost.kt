package com.example.bh4haptool.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bh4haptool.core.toolkit.data.ToolboxPreferencesRepository
import com.example.bh4haptool.feature.catchcat.navigation.CatchCatDestination
import com.example.bh4haptool.feature.catchcat.ui.CatchCatRoute
import com.example.bh4haptool.feature.frisbeegroup.navigation.FrisbeeGroupDestination
import com.example.bh4haptool.feature.frisbeegroup.ui.FrisbeeGroupRoute
import com.example.bh4haptool.feature.minesweeper.navigation.MinesweeperDestination
import com.example.bh4haptool.feature.minesweeper.ui.MinesweeperRoute
import com.example.bh4haptool.feature.sokoban.navigation.SokobanDestination
import com.example.bh4haptool.feature.sokoban.ui.SokobanRoute
import com.example.bh4haptool.feature.shakedraw.navigation.ShakeDrawDestination
import com.example.bh4haptool.feature.shakedraw.ui.ShakeDrawRoute
import com.example.bh4haptool.feature.simpledraw.navigation.SimpleDrawDestination
import com.example.bh4haptool.feature.simpledraw.ui.SimpleDrawRoute
import com.example.bh4haptool.feature.tetris.navigation.TetrisDestination
import com.example.bh4haptool.feature.tetris.ui.TetrisRoute
import com.example.bh4haptool.ui.home.HomeRoute
import com.example.bh4haptool.ui.settings.SettingsRoute

@Composable
fun AppNavHost(
    repository: ToolboxPreferencesRepository,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppDestination.homeRoute,
        modifier = modifier
    ) {
        composable(route = AppDestination.homeRoute) {
            HomeRoute(
                onToolSelected = { tool ->
                    navController.navigate(tool.route)
                },
                onSettingsClicked = {
                    navController.navigate(AppDestination.settingsRoute)
                }
            )
        }
        composable(route = AppDestination.settingsRoute) {
            SettingsRoute(
                onBack = { navController.popBackStack() },
                repository = repository
            )
        }
        composable(route = SimpleDrawDestination.route) {
            SimpleDrawRoute(
                onBack = { navController.popBackStack() }
            )
        }
        composable(route = ShakeDrawDestination.route) {
            ShakeDrawRoute(
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
    }
}
