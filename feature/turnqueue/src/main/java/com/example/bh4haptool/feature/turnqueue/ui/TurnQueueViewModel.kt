package com.example.bh4haptool.feature.turnqueue.ui

import androidx.lifecycle.ViewModel
import com.example.bh4haptool.feature.turnqueue.domain.TurnQueueEngine
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class TurnQueueViewModel @Inject constructor(
    private val engine: TurnQueueEngine
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        TurnQueueUiState(
            participants = engine.parseParticipants(DEFAULT_PARTICIPANTS_INPUT)
        )
    )
    val uiState: StateFlow<TurnQueueUiState> = _uiState.asStateFlow()

    fun updateParticipantsInput(value: String) {
        _uiState.update {
            it.copy(
                participantsInput = value,
                participants = engine.parseParticipants(value),
                started = false,
                currentIndex = 0,
                round = 1,
                winner = null,
                message = null
            )
        }
    }

    fun updateMode(mode: TurnQueueMode) {
        _uiState.update {
            it.copy(mode = mode)
        }
    }

    fun start() {
        val participants = engine.parseParticipants(_uiState.value.participantsInput)
        if (participants.isEmpty()) {
            _uiState.update { it.copy(message = "请至少输入两名参与者") }
            return
        }

        val queue = engine.buildInitialQueue(
            participants = participants,
            randomFirst = _uiState.value.mode != TurnQueueMode.SEQUENTIAL
        )

        _uiState.update {
            it.copy(
                participants = queue,
                started = true,
                currentIndex = 0,
                round = 1,
                winner = if (queue.size == 1) queue.first() else null,
                message = null
            )
        }
    }

    fun next() {
        val state = _uiState.value
        if (!state.started || state.participants.isEmpty() || state.winner != null) {
            return
        }

        val nextIndex = if (state.currentIndex >= state.participants.lastIndex) 0 else state.currentIndex + 1
        val nextRound = if (nextIndex == 0) state.round + 1 else state.round
        _uiState.update {
            it.copy(currentIndex = nextIndex, round = nextRound)
        }
    }

    fun eliminateCurrent() {
        val state = _uiState.value
        if (!state.started || state.participants.size <= 1) {
            return
        }

        val updated = state.participants.toMutableList().also { it.removeAt(state.currentIndex) }
        val adjustedIndex = state.currentIndex.coerceAtMost(updated.lastIndex.coerceAtLeast(0))
        _uiState.update {
            it.copy(
                participants = updated,
                currentIndex = adjustedIndex,
                winner = updated.singleOrNull(),
                message = null
            )
        }
    }

    fun reset() {
        val parsed = engine.parseParticipants(_uiState.value.participantsInput)
        _uiState.update {
            it.copy(
                participants = parsed,
                currentIndex = 0,
                round = 1,
                started = false,
                winner = null,
                message = null
            )
        }
    }
}
