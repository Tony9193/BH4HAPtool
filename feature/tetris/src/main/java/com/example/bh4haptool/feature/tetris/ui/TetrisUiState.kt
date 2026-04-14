package com.example.bh4haptool.feature.tetris.ui

import com.example.bh4haptool.feature.tetris.domain.TetrisStatus
import com.example.bh4haptool.feature.tetris.domain.TetrominoType

data class TetrisSettingsDraft(
    val startLevel: Int = 1,
    val vibrationEnabled: Boolean = true
) {
    fun sanitized(): TetrisSettingsDraft {
        return copy(startLevel = startLevel.coerceIn(1, 10))
    }
}

data class TetrisUiState(
    val isLoading: Boolean = true,
    val boardWidth: Int = 10,
    val boardHeight: Int = 20,
    val board: List<List<Int>> = List(20) { List(10) { 0 } },
    val nextType: TetrominoType = TetrominoType.T,
    val score: Int = 0,
    val level: Int = 1,
    val linesCleared: Int = 0,
    val status: TetrisStatus = TetrisStatus.READY,
    val statusMessage: String = "",
    val dropIntervalMs: Int = 780,
    val showSettingsPanel: Boolean = false,
    val settingsDraft: TetrisSettingsDraft = TetrisSettingsDraft(),
    val highScore: Int = 0,
    val bestLevel: Int = 1
)
