package com.example.bh4haptool.feature.sokoban.ui

import com.example.bh4haptool.feature.sokoban.domain.SokobanCoord
import com.example.bh4haptool.feature.sokoban.domain.SokobanStatus

data class SokobanUiState(
    val isLoading: Boolean = true,
    val levelIndex: Int = 0,
    val totalLevels: Int = 1,
    val levelName: String = "",
    val boardWidth: Int = 0,
    val boardHeight: Int = 0,
    val walls: Set<SokobanCoord> = emptySet(),
    val targets: Set<SokobanCoord> = emptySet(),
    val boxes: Set<SokobanCoord> = emptySet(),
    val player: SokobanCoord = SokobanCoord(0, 0),
    val moveCount: Int = 0,
    val status: SokobanStatus = SokobanStatus.PLAYING,
    val statusMessage: String = "",
    val completedLevels: Int = 0,
    val currentLevelBestMoves: Int = 0,
    val vibrationEnabled: Boolean = true
)
