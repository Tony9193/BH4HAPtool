package com.example.bh4haptool.feature.tetris.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bh4haptool.core.toolkit.data.ToolboxPreferencesRepository
import com.example.bh4haptool.feature.tetris.domain.TetrisCell
import com.example.bh4haptool.feature.tetris.domain.TetrisEngine
import com.example.bh4haptool.feature.tetris.domain.TetrisSnapshot
import com.example.bh4haptool.feature.tetris.domain.TetrisStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/** Coordinates Tetris UI state, persistence and game loop ticks. */
@HiltViewModel
class TetrisViewModel @Inject constructor(
    private val repository: ToolboxPreferencesRepository
) : ViewModel() {

    private val engine = TetrisEngine()
    private val _uiState = MutableStateFlow(TetrisUiState())
    val uiState = _uiState.asStateFlow()

    private var tickJob: Job? = null

    init {
        loadInitialState()
    }

    fun onMoveLeft(): Boolean {
        return performMove { engine.moveLeft() }
    }

    fun onMoveRight(): Boolean {
        return performMove { engine.moveRight() }
    }

    fun onRotate(): Boolean {
        return performMove { engine.rotate() }
    }

    fun onSoftDrop(): Boolean {
        return performMove { engine.softDrop() }
    }

    fun onHardDrop(): Boolean {
        return performMove { engine.hardDrop() }
    }

    fun onPauseResumeClicked() {
        if (_uiState.value.isLoading) {
            return
        }

        engine.togglePause()
        syncFromSnapshot(engine.snapshot())
    }

    fun onNewGameClicked() {
        if (_uiState.value.isLoading) {
            return
        }

        startNewGame(_uiState.value.settingsDraft)
    }

    fun onSettingsPanelToggle() {
        _uiState.update { current ->
            current.copy(showSettingsPanel = !current.showSettingsPanel)
        }
    }

    fun onStartLevelChanged(level: Int) {
        _uiState.update { current ->
            current.copy(
                settingsDraft = current.settingsDraft
                    .copy(startLevel = level)
                    .sanitized()
            )
        }
    }

    fun onVibrationChanged(enabled: Boolean) {
        _uiState.update { current ->
            current.copy(
                settingsDraft = current.settingsDraft.copy(vibrationEnabled = enabled)
            )
        }
    }

    fun onApplySettings() {
        val normalized = _uiState.value.settingsDraft.sanitized()

        _uiState.update { current ->
            current.copy(
                settingsDraft = normalized,
                showSettingsPanel = false
            )
        }

        viewModelScope.launch {
            repository.updateTetrisSettings(
                startLevel = normalized.startLevel,
                vibrationEnabled = normalized.vibrationEnabled
            )
        }

        startNewGame(normalized)
        _uiState.update { current ->
            current.copy(statusMessage = "设置已应用，已开始新局")
        }
    }

    private fun loadInitialState() {
        viewModelScope.launch {
            val settings = repository.settingsFlow.first()
            val draft = TetrisSettingsDraft(
                startLevel = settings.tetrisStartLevel,
                vibrationEnabled = settings.tetrisVibrationEnabled
            ).sanitized()

            _uiState.update { current ->
                current.copy(
                    isLoading = false,
                    settingsDraft = draft,
                    highScore = settings.tetrisHighScore,
                    bestLevel = settings.tetrisBestLevel
                )
            }

            startNewGame(draft)
        }
    }

    private fun startNewGame(draft: TetrisSettingsDraft) {
        stopTickLoop()
        engine.newGame(startLevel = draft.startLevel)
        syncFromSnapshot(engine.snapshot())
    }

    private fun performMove(action: () -> Boolean): Boolean {
        if (_uiState.value.isLoading || _uiState.value.status != TetrisStatus.PLAYING) {
            return false
        }

        val changed = action()
        if (changed) {
            syncFromSnapshot(engine.snapshot())
        }
        return changed
    }

    private fun syncFromSnapshot(snapshot: TetrisSnapshot) {
        val previousStatus = _uiState.value.status

        _uiState.update { current ->
            current.copy(
                isLoading = false,
                boardWidth = snapshot.width,
                boardHeight = snapshot.height,
                board = mergeBoard(snapshot),
                nextType = snapshot.nextType,
                score = snapshot.score,
                level = snapshot.level,
                linesCleared = snapshot.linesCleared,
                status = snapshot.status,
                statusMessage = snapshot.statusMessage,
                dropIntervalMs = snapshot.dropIntervalMs
            )
        }

        if (snapshot.status == TetrisStatus.PLAYING && previousStatus != TetrisStatus.PLAYING) {
            startTickLoop()
        }

        if (snapshot.status != TetrisStatus.PLAYING && previousStatus == TetrisStatus.PLAYING) {
            stopTickLoop()
        }

        if (snapshot.status == TetrisStatus.GAME_OVER && previousStatus != TetrisStatus.GAME_OVER) {
            recordBest(snapshot.score, snapshot.level)
        }
    }

    private fun startTickLoop() {
        if (tickJob?.isActive == true) {
            return
        }

        // Tick interval is driven by engine snapshot and can change with level.
        tickJob = viewModelScope.launch {
            while (isActive) {
                val interval = _uiState.value.dropIntervalMs.toLong().coerceAtLeast(80L)
                delay(interval)

                if (_uiState.value.status != TetrisStatus.PLAYING) {
                    continue
                }

                engine.tick()
                syncFromSnapshot(engine.snapshot())
            }
        }
    }

    private fun stopTickLoop() {
        tickJob?.cancel()
        tickJob = null
    }

    private fun mergeBoard(snapshot: TetrisSnapshot): List<List<Int>> {
        val merged = snapshot.settledBoard.map { row -> row.toMutableList() }

        // Overlay active piece on top of settled cells for one render frame.
        for (cell in snapshot.activeCells) {
            if (cell.row in 0 until snapshot.height && cell.col in 0 until snapshot.width) {
                merged[cell.row][cell.col] = cell.color
            }
        }

        return merged.map { row -> row.toList() }
    }

    private fun recordBest(score: Int, level: Int) {
        val currentState = _uiState.value
        val nextHigh = maxOf(currentState.highScore, score)
        val nextLevel = maxOf(currentState.bestLevel, level)

        _uiState.update { current ->
            current.copy(
                highScore = nextHigh,
                bestLevel = nextLevel
            )
        }

        viewModelScope.launch {
            repository.updateTetrisBestRecord(
                highScore = score,
                bestLevel = level
            )
        }
    }

    override fun onCleared() {
        stopTickLoop()
        super.onCleared()
    }
}
