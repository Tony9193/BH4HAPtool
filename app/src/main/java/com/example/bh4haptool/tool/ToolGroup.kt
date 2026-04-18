package com.example.bh4haptool.tool

import com.example.bh4haptool.R

enum class ToolGroup {
    RANDOM,
    PARTY,
    GAME,
    FOCUS
}

fun ToolGroup.titleRes(): Int = when (this) {
    ToolGroup.RANDOM -> R.string.tool_group_random
    ToolGroup.PARTY -> R.string.tool_group_party
    ToolGroup.GAME -> R.string.tool_group_game
    ToolGroup.FOCUS -> R.string.tool_group_focus
}
