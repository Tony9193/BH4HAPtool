package com.example.bh4haptool.feature.sokoban.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bh4haptool.core.toolkit.data.ToolboxPreferencesRepository
import com.example.bh4haptool.feature.sokoban.domain.MoveDirection
import com.example.bh4haptool.feature.sokoban.domain.SokobanEngine
import com.example.bh4haptool.feature.sokoban.domain.SokobanSnapshot
import com.example.bh4haptool.feature.sokoban.domain.SokobanStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SokobanViewModel @Inject constructor(
    private val repository: ToolboxPreferencesRepository
) : ViewModel() {

    private val engine = SokobanEngine()
    private val bestMovesByLevel = mutableMapOf<Int, Int>()

    private val _uiState = MutableStateFlow(SokobanUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadInitialState()
    }

    fun onMove(direction: MoveDirection): Boolean {
        if (_uiState.value.isLoading) {
            return false
        }

        val wasWon = _uiState.value.status == SokobanStatus.WON
        val moved = engine.move(direction)

        syncFromSnapshot(engine.snapshot())
        if (!moved) {
            return false
        }

        val latest = _uiState.value
        persistCurrentLevel(latest.levelIndex)

        if (!wasWon && latest.status == SokobanStatus.WON) {
            onLevelCompleted(
                levelIndex = latest.levelIndex,
                moves = latest.moveCount
            )
        }

        return true
    }

    fun onUndo(): Boolean {
        val changed = engine.undo()
        if (changed) {
            syncFromSnapshot(engine.snapshot())
            persistCurrentLevel(_uiState.value.levelIndex)
        }
        return changed
    }

    fun onReset() {
        engine.reset()
        syncFromSnapshot(engine.snapshot())
        persistCurrentLevel(_uiState.value.levelIndex)
    }

    fun onNextLevel(): Boolean {
        val changed = engine.nextLevel()
        if (changed) {
            syncFromSnapshot(engine.snapshot())
            persistCurrentLevel(_uiState.value.levelIndex)
        }
        return changed
    }

    fun onPreviousLevel(): Boolean {
        val changed = engine.previousLevel()
        if (changed) {
            syncFromSnapshot(engine.snapshot())
            persistCurrentLevel(_uiState.value.levelIndex)
        }
        return changed
    }

    fun onRandomLevel() {
        val total = _uiState.value.totalLevels
        if (total > 1) {
            val random = kotlin.random.Random.nextInt(total)
            engine.loadLevel(random)
            syncFromSnapshot(engine.snapshot())
            persistCurrentLevel(_uiState.value.levelIndex)
        }
    }

    fun onJumpToLevel(index: Int) {
        val changed = engine.loadLevel(index)
        if (changed) {
            syncFromSnapshot(engine.snapshot())
            persistCurrentLevel(_uiState.value.levelIndex)
        }
    }

    fun onVibrationChanged(enabled: Boolean) {
        _uiState.update { current ->
            current.copy(vibrationEnabled = enabled)
        }

        viewModelScope.launch {
            repository.updateSokobanVibrationEnabled(enabled)
        }
    }

    private fun loadInitialState() {
        viewModelScope.launch {
            val settings = repository.settingsFlow.first()

            bestMovesByLevel.clear()
            bestMovesByLevel.putAll(decodeBestMoves(settings.sokobanBestMovesEncoded))

            engine.loadLevel(settings.sokobanLastLevelIndex)
            syncFromSnapshot(
                snapshot = engine.snapshot(),
                completedLevelsOverride = settings.sokobanCompletedLevels,
                vibrationOverride = settings.sokobanVibrationEnabled
            )
        }
    }

    private fun onLevelCompleted(levelIndex: Int, moves: Int) {
        var shouldUpdateBest = false
        val currentBest = bestMovesByLevel[levelIndex]
        if (currentBest == null || moves < currentBest) {
            bestMovesByLevel[levelIndex] = moves
            shouldUpdateBest = true
        }

        _uiState.update { current ->
            current.copy(
                completedLevels = current.completedLevels + 1,
                currentLevelBestMoves = bestMovesByLevel[levelIndex] ?: 0
            )
        }

        viewModelScope.launch {
            repository.incrementSokobanCompletedLevels()
            if (shouldUpdateBest) {
                repository.updateSokobanBestMoves(levelIndex, moves)
            }
        }
    }

    private fun persistCurrentLevel(levelIndex: Int) {
        viewModelScope.launch {
            repository.updateSokobanLastLevelIndex(levelIndex)
        }
    }

    private fun syncFromSnapshot(
        snapshot: SokobanSnapshot,
        completedLevelsOverride: Int? = null,
        vibrationOverride: Boolean? = null
    ) {
        val completed = completedLevelsOverride ?: _uiState.value.completedLevels
        val vibration = vibrationOverride ?: _uiState.value.vibrationEnabled
        val best = bestMovesByLevel[snapshot.levelIndex] ?: 0

        _uiState.value = SokobanUiState(
            isLoading = false,
            levelIndex = snapshot.levelIndex,
            totalLevels = snapshot.totalLevels,
            levelName = snapshot.levelName,
            boardWidth = snapshot.width,
            boardHeight = snapshot.height,
            walls = snapshot.walls,
            targets = snapshot.targets,
            boxes = snapshot.boxes,
            player = snapshot.player,
            moveCount = snapshot.moveCount,
            status = snapshot.status,
            statusMessage = snapshot.statusMessage,
            completedLevels = completed,
            currentLevelBestMoves = best,
            vibrationEnabled = vibration
        )
    }

    private fun decodeBestMoves(encoded: String): Map<Int, Int> {
        if (encoded.isBlank()) {
            return emptyMap()
        }

        return encoded
            .split(',')
            .mapNotNull { token ->
                val parts = token.split(':')
                if (parts.size != 2) {
                    return@mapNotNull null
                }

                val level = parts[0].trim().toIntOrNull() ?: return@mapNotNull null
                val moves = parts[1].trim().toIntOrNull() ?: return@mapNotNull null
                level to moves
            }
            .toMap()
    }
}
