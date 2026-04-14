package com.example.bh4haptool.feature.minesweeper.ui

import com.example.bh4haptool.core.toolkit.data.MinesweeperDifficulty
import com.example.bh4haptool.feature.minesweeper.domain.CellCoord
import com.example.bh4haptool.feature.minesweeper.domain.MinesweeperBoardConfig
import com.example.bh4haptool.feature.minesweeper.domain.MinesweeperCell
import com.example.bh4haptool.feature.minesweeper.domain.MinesweeperStatus

data class MinesweeperSettingsDraft(
    val difficulty: MinesweeperDifficulty = MinesweeperDifficulty.NORMAL,
    val width: Int = 10,
    val height: Int = 10,
    val mineCount: Int = 20,
    val firstClickSafe: Boolean = true,
    val questionMarkEnabled: Boolean = true,
    val autoExpandEnabled: Boolean = true,
    val soundEnabled: Boolean = false,
    val vibrationEnabled: Boolean = true
) {
    fun sanitized(): MinesweeperSettingsDraft {
        val safeWidth = width.coerceIn(MinesweeperBoardConfig.MIN_WIDTH, MinesweeperBoardConfig.MAX_WIDTH)
        val safeHeight =
            height.coerceIn(MinesweeperBoardConfig.MIN_HEIGHT, MinesweeperBoardConfig.MAX_HEIGHT)
        val maxMines = safeWidth * safeHeight - 1
        return copy(
            width = safeWidth,
            height = safeHeight,
            mineCount = mineCount.coerceIn(MinesweeperBoardConfig.MIN_MINES, maxMines)
        )
    }

    fun toBoardConfig(): MinesweeperBoardConfig {
        val normalized = sanitized()
        return MinesweeperBoardConfig(
            width = normalized.width,
            height = normalized.height,
            mineCount = normalized.mineCount,
            difficulty = normalized.difficulty
        ).sanitized()
    }
}

data class MinesweeperUiState(
    val isLoading: Boolean = true,
    val status: MinesweeperStatus = MinesweeperStatus.READY,
    val boardConfig: MinesweeperBoardConfig = MinesweeperBoardConfig.preset(MinesweeperDifficulty.NORMAL),
    val cells: List<List<MinesweeperCell>> = emptyList(),
    val minesLeft: Int = boardConfig.mineCount,
    val flaggedCount: Int = 0,
    val revealedCount: Int = 0,
    val elapsedSeconds: Int = 0,
    val explodedCell: CellCoord? = null,
    val statusMessage: String = "",
    val showSettingsPanel: Boolean = false,
    val settingsDraft: MinesweeperSettingsDraft = MinesweeperSettingsDraft(),
    val wins: Int = 0,
    val losses: Int = 0,
    val bestEasySec: Int = 0,
    val bestNormalSec: Int = 0,
    val bestHardSec: Int = 0
)
