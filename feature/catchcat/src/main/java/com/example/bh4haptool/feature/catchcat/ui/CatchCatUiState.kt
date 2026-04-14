package com.example.bh4haptool.feature.catchcat.ui

import com.example.bh4haptool.feature.catchcat.domain.CatchCatEngine
import com.example.bh4haptool.feature.catchcat.domain.CatchCatStatus
import com.example.bh4haptool.feature.catchcat.domain.HexCoord

data class CatchCatUiState(
    val width: Int = CatchCatEngine.DEFAULT_WIDTH,
    val height: Int = CatchCatEngine.DEFAULT_HEIGHT,
    val initialWallCount: Int = CatchCatEngine.DEFAULT_INITIAL_WALL_COUNT,
    val cat: HexCoord = HexCoord(
        i = CatchCatEngine.DEFAULT_WIDTH / 2,
        j = CatchCatEngine.DEFAULT_HEIGHT / 2
    ),
    val walls: Set<HexCoord> = emptySet(),
    val status: CatchCatStatus = CatchCatStatus.PLAYING,
    val statusMessage: String = CatchCatEngine.DEFAULT_STATUS_MESSAGE,
    val moveCount: Int = 0
)
