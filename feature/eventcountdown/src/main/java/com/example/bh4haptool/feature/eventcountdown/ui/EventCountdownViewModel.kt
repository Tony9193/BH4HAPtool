package com.example.bh4haptool.feature.eventcountdown.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bh4haptool.feature.eventcountdown.domain.EventCountdownParser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class EventCountdownViewModel @Inject constructor(
    private val parser: EventCountdownParser
) : ViewModel() {
    private var tickerJob: Job? = null

    private val _uiState = MutableStateFlow(
        createInitialState(DEFAULT_STAGES_INPUT)
    )
    val uiState: StateFlow<EventCountdownUiState> = _uiState.asStateFlow()

    fun updateStagesInput(value: String) {
        tickerJob?.cancel()
        _uiState.value = createInitialState(value)
    }

    fun start() {
        val state = _uiState.value
        if (state.stages.isEmpty()) {
            _uiState.update { it.copy(message = "请按“名称|分钟”输入至少一个阶段") }
            return
        }
        if (state.isFinished) {
            _uiState.value = createInitialState(state.stagesInput)
        }

        tickerJob?.cancel()
        _uiState.update { it.copy(isRunning = true, message = null) }
        tickerJob = viewModelScope.launch {
            while (true) {
                delay(1000L)
                val current = _uiState.value
                if (!current.isRunning || current.isFinished) {
                    break
                }

                if (current.remainingSeconds > 1) {
                    _uiState.update { it.copy(remainingSeconds = it.remainingSeconds - 1) }
                } else {
                    moveToNextStage()
                    if (_uiState.value.isFinished) {
                        break
                    }
                }
            }
        }
    }

    fun pause() {
        tickerJob?.cancel()
        _uiState.update { it.copy(isRunning = false) }
    }

    fun skipStage() {
        moveToNextStage()
    }

    fun reset() {
        tickerJob?.cancel()
        _uiState.value = createInitialState(_uiState.value.stagesInput)
    }

    private fun moveToNextStage() {
        tickerJob?.cancel()
        val state = _uiState.value
        val nextIndex = state.currentStageIndex + 1
        val nextStage = state.stages.getOrNull(nextIndex)

        if (nextStage == null) {
            _uiState.update {
                it.copy(
                    isRunning = false,
                    isFinished = true,
                    remainingSeconds = 0,
                    totalSeconds = 0,
                    message = null
                )
            }
            return
        }

        _uiState.update {
            it.copy(
                currentStageIndex = nextIndex,
                remainingSeconds = nextStage.durationMinutes * 60,
                totalSeconds = nextStage.durationMinutes * 60,
                isRunning = true,
                isFinished = false,
                message = null
            )
        }
        start()
    }

    private fun createInitialState(input: String): EventCountdownUiState {
        val stages = parser.parse(input)
        val firstStage = stages.firstOrNull()
        return EventCountdownUiState(
            stagesInput = input,
            stages = stages,
            currentStageIndex = 0,
            remainingSeconds = (firstStage?.durationMinutes ?: 0) * 60,
            totalSeconds = (firstStage?.durationMinutes ?: 0) * 60,
            isRunning = false,
            isFinished = false,
            message = null
        )
    }

    override fun onCleared() {
        tickerJob?.cancel()
        super.onCleared()
    }
}
