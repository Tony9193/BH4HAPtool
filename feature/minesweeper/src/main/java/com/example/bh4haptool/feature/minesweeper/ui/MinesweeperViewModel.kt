package com.example.bh4haptool.feature.minesweeper.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bh4haptool.core.toolkit.data.MinesweeperDifficulty
import com.example.bh4haptool.core.toolkit.data.ToolboxPreferencesRepository
import com.example.bh4haptool.feature.minesweeper.domain.MinesweeperBoardConfig
import com.example.bh4haptool.feature.minesweeper.domain.MinesweeperEngine
import com.example.bh4haptool.feature.minesweeper.domain.MinesweeperSnapshot
import com.example.bh4haptool.feature.minesweeper.domain.MinesweeperStatus
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

@HiltViewModel
class MinesweeperViewModel @Inject constructor(
    private val repository: ToolboxPreferencesRepository
) : ViewModel() {

    private val engine = MinesweeperEngine()
    private val _uiState = MutableStateFlow(MinesweeperUiState())
    val uiState = _uiState.asStateFlow()

    private var timerJob: Job? = null

    init {
        loadInitialState()
    }

    fun onCellClicked(
        row: Int,
        col: Int
    ) {
        if (_uiState.value.isLoading) {
            return
        }

        val before = engine.snapshot()
        engine.reveal(row = row, col = col)
        val after = engine.snapshot()

        handleStatusTransition(
            previousStatus = before.status,
            currentStatus = after.status,
            difficulty = after.config.difficulty
        )
        syncFromSnapshot(after)
    }

    fun onCellLongPressed(
        row: Int,
        col: Int
    ) {
        if (_uiState.value.isLoading) {
            return
        }

        engine.toggleMark(row = row, col = col)
        syncFromSnapshot()
    }

    fun onPauseResumeClicked() {
        if (_uiState.value.isLoading) {
            return
        }

        when (_uiState.value.status) {
            MinesweeperStatus.PLAYING -> {
                engine.pause()
                stopTimer()
            }

            MinesweeperStatus.PAUSED -> {
                engine.resume()
                startTimer()
            }

            else -> {
                return
            }
        }

        syncFromSnapshot()
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

    fun onDifficultyChanged(difficulty: MinesweeperDifficulty) {
        _uiState.update { current ->
            val nextDraft = if (difficulty == MinesweeperDifficulty.CUSTOM) {
                current.settingsDraft.copy(difficulty = difficulty)
            } else {
                val preset = MinesweeperBoardConfig.preset(difficulty)
                current.settingsDraft.copy(
                    difficulty = difficulty,
                    width = preset.width,
                    height = preset.height,
                    mineCount = preset.mineCount
                )
            }
            current.copy(settingsDraft = nextDraft.sanitized())
        }
    }

    fun onDraftWidthChanged(width: Int) {
        _uiState.update { current ->
            current.copy(
                settingsDraft = current.settingsDraft
                    .copy(
                        difficulty = MinesweeperDifficulty.CUSTOM,
                        width = width
                    )
                    .sanitized()
            )
        }
    }

    fun onDraftHeightChanged(height: Int) {
        _uiState.update { current ->
            current.copy(
                settingsDraft = current.settingsDraft
                    .copy(
                        difficulty = MinesweeperDifficulty.CUSTOM,
                        height = height
                    )
                    .sanitized()
            )
        }
    }

    fun onDraftMineCountChanged(mineCount: Int) {
        _uiState.update { current ->
            current.copy(
                settingsDraft = current.settingsDraft
                    .copy(
                        difficulty = MinesweeperDifficulty.CUSTOM,
                        mineCount = mineCount
                    )
                    .sanitized()
            )
        }
    }

    fun onFirstClickSafeChanged(enabled: Boolean) {
        _uiState.update { current ->
            current.copy(
                settingsDraft = current.settingsDraft.copy(firstClickSafe = enabled)
            )
        }
    }

    fun onQuestionMarkChanged(enabled: Boolean) {
        _uiState.update { current ->
            current.copy(
                settingsDraft = current.settingsDraft.copy(questionMarkEnabled = enabled)
            )
        }
    }

    fun onAutoExpandChanged(enabled: Boolean) {
        _uiState.update { current ->
            current.copy(
                settingsDraft = current.settingsDraft.copy(autoExpandEnabled = enabled)
            )
        }
    }

    fun onSoundChanged(enabled: Boolean) {
        _uiState.update { current ->
            current.copy(
                settingsDraft = current.settingsDraft.copy(soundEnabled = enabled)
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
        val normalizedDraft = _uiState.value.settingsDraft.sanitized()
        _uiState.update { current ->
            current.copy(
                settingsDraft = normalizedDraft,
                showSettingsPanel = false
            )
        }

        viewModelScope.launch {
            repository.updateMinesweeperConfig(
                difficulty = normalizedDraft.difficulty,
                boardWidth = normalizedDraft.width,
                boardHeight = normalizedDraft.height,
                mineCount = normalizedDraft.mineCount,
                firstClickSafe = normalizedDraft.firstClickSafe,
                questionMarkEnabled = normalizedDraft.questionMarkEnabled,
                autoExpandEnabled = normalizedDraft.autoExpandEnabled,
                soundEnabled = normalizedDraft.soundEnabled,
                vibrationEnabled = normalizedDraft.vibrationEnabled
            )
        }

        startNewGame(normalizedDraft)
        _uiState.update { current ->
            current.copy(statusMessage = "设置已应用，已开始新局")
        }
    }

    private fun loadInitialState() {
        viewModelScope.launch {
            val settings = repository.settingsFlow.first()
            val initialDraft = MinesweeperSettingsDraft(
                difficulty = settings.minesweeperDifficulty,
                width = settings.minesweeperBoardWidth,
                height = settings.minesweeperBoardHeight,
                mineCount = settings.minesweeperMineCount,
                firstClickSafe = settings.minesweeperFirstClickSafe,
                questionMarkEnabled = settings.minesweeperQuestionMarkEnabled,
                autoExpandEnabled = settings.minesweeperAutoExpandEnabled,
                soundEnabled = settings.minesweeperSoundEnabled,
                vibrationEnabled = settings.minesweeperVibrationEnabled
            ).sanitized()

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                settingsDraft = initialDraft,
                wins = settings.minesweeperWins,
                losses = settings.minesweeperLosses,
                bestEasySec = settings.minesweeperBestTimeEasySec,
                bestNormalSec = settings.minesweeperBestTimeNormalSec,
                bestHardSec = settings.minesweeperBestTimeHardSec
            )

            startNewGame(initialDraft)
        }
    }

    private fun startNewGame(draft: MinesweeperSettingsDraft) {
        stopTimer()
        _uiState.update { current -> current.copy(elapsedSeconds = 0) }

        engine.newGame(
            config = draft.toBoardConfig(),
            firstClickSafe = draft.firstClickSafe,
            questionMarkEnabled = draft.questionMarkEnabled,
            autoExpandEnabled = draft.autoExpandEnabled
        )
        syncFromSnapshot(resetElapsed = true)
    }

    private fun syncFromSnapshot(
        snapshot: MinesweeperSnapshot = engine.snapshot(),
        resetElapsed: Boolean = false
    ) {
        _uiState.update { current ->
            current.copy(
                isLoading = false,
                status = snapshot.status,
                boardConfig = snapshot.config,
                cells = snapshot.cells,
                minesLeft = (snapshot.config.mineCount - snapshot.flaggedCount).coerceAtLeast(0),
                flaggedCount = snapshot.flaggedCount,
                revealedCount = snapshot.revealedCount,
                explodedCell = snapshot.explodedCell,
                statusMessage = snapshot.statusMessage,
                elapsedSeconds = if (resetElapsed) 0 else current.elapsedSeconds
            )
        }
    }

    private fun handleStatusTransition(
        previousStatus: MinesweeperStatus,
        currentStatus: MinesweeperStatus,
        difficulty: MinesweeperDifficulty
    ) {
        if (previousStatus == currentStatus) {
            return
        }

        when (currentStatus) {
            MinesweeperStatus.PLAYING -> {
                if (previousStatus == MinesweeperStatus.READY || previousStatus == MinesweeperStatus.PAUSED) {
                    startTimer()
                }
            }

            MinesweeperStatus.PAUSED -> {
                stopTimer()
            }

            MinesweeperStatus.WON -> {
                stopTimer()
                recordWin(difficulty)
            }

            MinesweeperStatus.LOST -> {
                stopTimer()
                recordLoss()
            }

            MinesweeperStatus.READY -> {
                stopTimer()
            }
        }
    }

    private fun recordWin(difficulty: MinesweeperDifficulty) {
        val elapsed = _uiState.value.elapsedSeconds

        _uiState.update { current ->
            var easy = current.bestEasySec
            var normal = current.bestNormalSec
            var hard = current.bestHardSec

            when (difficulty) {
                MinesweeperDifficulty.EASY -> {
                    if (easy == 0 || elapsed in 1 until easy) {
                        easy = elapsed
                    }
                }

                MinesweeperDifficulty.HARD -> {
                    if (hard == 0 || elapsed in 1 until hard) {
                        hard = elapsed
                    }
                }

                MinesweeperDifficulty.NORMAL,
                MinesweeperDifficulty.CUSTOM -> {
                    if (normal == 0 || elapsed in 1 until normal) {
                        normal = elapsed
                    }
                }
            }

            current.copy(
                wins = current.wins + 1,
                bestEasySec = easy,
                bestNormalSec = normal,
                bestHardSec = hard
            )
        }

        viewModelScope.launch {
            repository.incrementMinesweeperWins()
            repository.updateMinesweeperBestTime(
                difficulty = difficulty,
                elapsedSeconds = elapsed
            )
        }
    }

    private fun recordLoss() {
        _uiState.update { current ->
            current.copy(losses = current.losses + 1)
        }

        viewModelScope.launch {
            repository.incrementMinesweeperLosses()
        }
    }

    private fun startTimer() {
        if (timerJob != null) {
            return
        }

        timerJob = viewModelScope.launch {
            while (isActive) {
                delay(1_000)
                _uiState.update { current ->
                    if (current.status == MinesweeperStatus.PLAYING) {
                        current.copy(elapsedSeconds = current.elapsedSeconds + 1)
                    } else {
                        current
                    }
                }
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    override fun onCleared() {
        stopTimer()
        super.onCleared()
    }
}
