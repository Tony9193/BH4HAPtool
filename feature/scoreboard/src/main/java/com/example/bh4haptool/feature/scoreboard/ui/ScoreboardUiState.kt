package com.example.bh4haptool.feature.scoreboard.ui

data class ScoreTeam(
    val id: Int,
    val name: String,
    val score: Int
)

data class ScoreboardUiState(
    val teams: List<ScoreTeam> = listOf(
        ScoreTeam(1, "红队", 0),
        ScoreTeam(2, "蓝队", 0),
        ScoreTeam(3, "绿队", 0),
        ScoreTeam(4, "黄队", 0)
    ),
    val canUndo: Boolean = false
)
