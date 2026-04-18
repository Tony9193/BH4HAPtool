package com.example.bh4haptool.tool

import com.example.bh4haptool.R
import com.example.bh4haptool.feature.eventcountdown.navigation.EventCountdownDestination
import com.example.bh4haptool.feature.aasplitter.navigation.AaSplitterDestination
import com.example.bh4haptool.feature.catchcat.navigation.CatchCatDestination
import com.example.bh4haptool.feature.frisbeegroup.navigation.FrisbeeGroupDestination
import com.example.bh4haptool.feature.luckywheel.navigation.LuckyWheelDestination
import com.example.bh4haptool.feature.minesweeper.navigation.MinesweeperDestination
import com.example.bh4haptool.feature.pomodoro.navigation.PomodoroDestination
import com.example.bh4haptool.feature.quickdecide.navigation.QuickDecideDestination
import com.example.bh4haptool.feature.scoreboard.navigation.ScoreboardDestination
import com.example.bh4haptool.feature.sokoban.navigation.SokobanDestination
import com.example.bh4haptool.feature.shakedraw.navigation.ShakeDrawDestination
import com.example.bh4haptool.feature.tetris.navigation.TetrisDestination
import com.example.bh4haptool.feature.turnqueue.navigation.TurnQueueDestination
import com.example.bh4haptool.navigation.AppDestination

object ToolRegistry {
    val entries: List<ToolEntry> = listOf(
        ToolEntry(
            id = "shake_draw",
            titleRes = R.string.tool_shake_draw_title,
            descriptionRes = R.string.tool_shake_draw_description,
            route = ShakeDrawDestination.route,
            group = ToolGroup.RANDOM,
            order = 10,
            tags = listOf("抽签", "随机", "名单", "摇一摇"),
            iconRes = android.R.drawable.ic_menu_rotate,
            isFeatured = true,
            supportsTablet = true
        ),
        ToolEntry(
            id = "quick_decide",
            titleRes = R.string.tool_quick_decide_title,
            descriptionRes = R.string.tool_quick_decide_description,
            route = QuickDecideDestination.route,
            group = ToolGroup.RANDOM,
            order = 20,
            tags = listOf("骰子", "硬币", "yes", "no", "随机"),
            iconRes = android.R.drawable.ic_menu_help,
            isFeatured = true,
            supportsTablet = true
        ),
        ToolEntry(
            id = "lucky_wheel",
            titleRes = R.string.tool_lucky_wheel_title,
            descriptionRes = R.string.tool_lucky_wheel_description,
            route = LuckyWheelDestination.route,
            group = ToolGroup.RANDOM,
            order = 25,
            tags = listOf("幸运转盘", "随机", "权重", "抽签"),
            iconRes = android.R.drawable.ic_menu_rotate,
            isFeatured = true,
            supportsTablet = true
        ),
        ToolEntry(
            id = "party_mode",
            titleRes = R.string.tool_party_mode_title,
            descriptionRes = R.string.tool_party_mode_description,
            route = AppDestination.partyModeRoute,
            group = ToolGroup.PARTY,
            order = 28,
            tags = listOf("派对模式", "主持", "流程", "模板"),
            iconRes = android.R.drawable.ic_menu_agenda,
            isFeatured = true,
            supportsTablet = true
        ),
        ToolEntry(
            id = "frisbee_group",
            titleRes = R.string.tool_frisbee_group_title,
            descriptionRes = R.string.tool_frisbee_group_description,
            route = FrisbeeGroupDestination.route,
            group = ToolGroup.PARTY,
            order = 30,
            tags = listOf("分组", "队伍", "飞盘", "活动"),
            iconRes = android.R.drawable.ic_menu_sort_by_size,
            isFeatured = true,
            supportsTablet = true
        ),
        ToolEntry(
            id = "scoreboard",
            titleRes = R.string.tool_scoreboard_title,
            descriptionRes = R.string.tool_scoreboard_description,
            route = ScoreboardDestination.route,
            group = ToolGroup.PARTY,
            order = 35,
            tags = listOf("记分", "比赛", "队伍", "主持"),
            iconRes = android.R.drawable.ic_menu_edit,
            isFeatured = true,
            supportsTablet = true
        ),
        ToolEntry(
            id = "turn_queue",
            titleRes = R.string.tool_turn_queue_title,
            descriptionRes = R.string.tool_turn_queue_description,
            route = TurnQueueDestination.route,
            group = ToolGroup.PARTY,
            order = 37,
            tags = listOf("轮次", "先手", "排队", "淘汰"),
            iconRes = android.R.drawable.ic_menu_more,
            isFeatured = true,
            supportsTablet = true
        ),
        ToolEntry(
            id = "event_countdown",
            titleRes = R.string.tool_event_countdown_title,
            descriptionRes = R.string.tool_event_countdown_description,
            route = EventCountdownDestination.route,
            group = ToolGroup.PARTY,
            order = 39,
            tags = listOf("倒计时", "流程", "主持", "活动"),
            iconRes = android.R.drawable.ic_menu_recent_history,
            isFeatured = true,
            supportsTablet = true
        ),
        ToolEntry(
            id = "catch_cat",
            titleRes = R.string.tool_catch_cat_title,
            descriptionRes = R.string.tool_catch_cat_description,
            route = CatchCatDestination.route,
            group = ToolGroup.GAME,
            order = 40,
            tags = listOf("抓小猫", "围堵", "益智", "棋盘"),
            iconRes = android.R.drawable.ic_menu_compass,
            isFeatured = false,
            supportsTablet = true
        ),
        ToolEntry(
            id = "minesweeper",
            titleRes = R.string.tool_minesweeper_title,
            descriptionRes = R.string.tool_minesweeper_description,
            route = MinesweeperDestination.route,
            group = ToolGroup.GAME,
            order = 50,
            tags = listOf("扫雷", "经典", "棋盘", "闯关"),
            iconRes = android.R.drawable.ic_delete,
            isFeatured = true,
            supportsTablet = true
        ),
        ToolEntry(
            id = "tetris",
            titleRes = R.string.tool_tetris_title,
            descriptionRes = R.string.tool_tetris_description,
            route = TetrisDestination.route,
            group = ToolGroup.GAME,
            order = 60,
            tags = listOf("俄罗斯方块", "消行", "经典", "分数"),
            iconRes = android.R.drawable.ic_menu_crop,
            isFeatured = true,
            supportsTablet = true
        ),
        ToolEntry(
            id = "sokoban",
            titleRes = R.string.tool_sokoban_title,
            descriptionRes = R.string.tool_sokoban_description,
            route = SokobanDestination.route,
            group = ToolGroup.GAME,
            order = 70,
            tags = listOf("推箱子", "关卡", "益智", "步数"),
            iconRes = android.R.drawable.ic_menu_directions,
            isFeatured = true,
            supportsTablet = true
        ),
        ToolEntry(
            id = "pomodoro",
            titleRes = R.string.tool_pomodoro_title,
            descriptionRes = R.string.tool_pomodoro_description,
            route = PomodoroDestination.route,
            group = ToolGroup.FOCUS,
            order = 80,
            tags = listOf("番茄钟", "专注", "计时", "效率"),
            iconRes = android.R.drawable.ic_menu_recent_history,
            isFeatured = true,
            supportsTablet = true
        ),
        ToolEntry(
            id = "aa_splitter",
            titleRes = R.string.tool_aa_splitter_title,
            descriptionRes = R.string.tool_aa_splitter_description,
            route = AaSplitterDestination.route,
            group = ToolGroup.FOCUS,
            order = 85,
            tags = listOf("aa", "分账", "垫付", "均摊"),
            iconRes = android.R.drawable.ic_menu_share,
            isFeatured = true,
            supportsTablet = true
        )
    )

    val defaultOrderIds: List<String> = entries.sortedBy { it.order }.map { it.id }

    fun entryById(toolId: String): ToolEntry? = entries.firstOrNull { it.id == toolId }
}
