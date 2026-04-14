package com.example.bh4haptool.tool

import com.example.bh4haptool.R
import com.example.bh4haptool.feature.catchcat.navigation.CatchCatDestination
import com.example.bh4haptool.feature.frisbeegroup.navigation.FrisbeeGroupDestination
import com.example.bh4haptool.feature.minesweeper.navigation.MinesweeperDestination
import com.example.bh4haptool.feature.sokoban.navigation.SokobanDestination
import com.example.bh4haptool.feature.shakedraw.navigation.ShakeDrawDestination
import com.example.bh4haptool.feature.simpledraw.navigation.SimpleDrawDestination
import com.example.bh4haptool.feature.tetris.navigation.TetrisDestination

object ToolRegistry {
    val entries: List<ToolEntry> = listOf(
        ToolEntry(
            id = "simple_draw",
            titleRes = R.string.tool_simple_draw_title,
            descriptionRes = R.string.tool_simple_draw_description,
            route = SimpleDrawDestination.route
        ),
        ToolEntry(
            id = "shake_draw",
            titleRes = R.string.tool_shake_draw_title,
            descriptionRes = R.string.tool_shake_draw_description,
            route = ShakeDrawDestination.route
        ),
        ToolEntry(
            id = "catch_cat",
            titleRes = R.string.tool_catch_cat_title,
            descriptionRes = R.string.tool_catch_cat_description,
            route = CatchCatDestination.route
        ),
        ToolEntry(
            id = "frisbee_group",
            titleRes = R.string.tool_frisbee_group_title,
            descriptionRes = R.string.tool_frisbee_group_description,
            route = FrisbeeGroupDestination.route
        ),
        ToolEntry(
            id = "minesweeper",
            titleRes = R.string.tool_minesweeper_title,
            descriptionRes = R.string.tool_minesweeper_description,
            route = MinesweeperDestination.route
        ),
        ToolEntry(
            id = "tetris",
            titleRes = R.string.tool_tetris_title,
            descriptionRes = R.string.tool_tetris_description,
            route = TetrisDestination.route
        ),
        ToolEntry(
            id = "sokoban",
            titleRes = R.string.tool_sokoban_title,
            descriptionRes = R.string.tool_sokoban_description,
            route = SokobanDestination.route
        )
    )
}
