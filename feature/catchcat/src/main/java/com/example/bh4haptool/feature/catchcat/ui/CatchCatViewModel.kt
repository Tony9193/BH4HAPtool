package com.example.bh4haptool.feature.catchcat.ui

import androidx.lifecycle.ViewModel
import com.example.bh4haptool.feature.catchcat.domain.CatchCatEngine
import com.example.bh4haptool.feature.catchcat.domain.CatchCatSnapshot
import com.example.bh4haptool.feature.catchcat.domain.HexCoord
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class CatchCatViewModel @Inject constructor() : ViewModel() {
    private val engine = CatchCatEngine()

    private val _uiState = MutableStateFlow(engine.snapshot().toUiState())
    val uiState = _uiState.asStateFlow()

    fun onCellTapped(i: Int, j: Int) {
        engine.placeWall(HexCoord(i, j))
        syncState()
    }

    fun onResetClicked() {
        engine.reset()
        syncState()
    }

    fun onUndoClicked() {
        engine.undo()
        syncState()
    }

    private fun syncState() {
        _uiState.value = engine.snapshot().toUiState()
    }

    private fun CatchCatSnapshot.toUiState(): CatchCatUiState {
        return CatchCatUiState(
            width = width,
            height = height,
            initialWallCount = initialWallCount,
            cat = cat,
            walls = walls,
            status = status,
            statusMessage = statusMessage,
            moveCount = moveCount
        )
    }
}
