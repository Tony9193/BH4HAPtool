package com.example.bh4haptool.feature.scoreboard.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class ScoreboardViewModel @Inject constructor() : ViewModel() {
    private val history = ArrayDeque<List<ScoreTeam>>()
    private val _uiState = MutableStateFlow(ScoreboardUiState())
    val uiState: StateFlow<ScoreboardUiState> = _uiState.asStateFlow()

    fun addTeam() {
        val current = _uiState.value.teams
        if (current.size >= 8) {
            return
        }

        pushHistory()
        val nextId = (current.maxOfOrNull { it.id } ?: 0) + 1
        _uiState.update {
            it.copy(
                teams = current + ScoreTeam(nextId, "队伍$nextId", 0),
                canUndo = history.isNotEmpty()
            )
        }
    }

    fun removeTeam(teamId: Int) {
        val current = _uiState.value.teams
        if (current.size <= 2) {
            return
        }

        pushHistory()
        _uiState.update {
            it.copy(
                teams = current.filterNot { team -> team.id == teamId },
                canUndo = history.isNotEmpty()
            )
        }
    }

    fun renameTeam(teamId: Int, name: String) {
        _uiState.update { state ->
            state.copy(
                teams = state.teams.map { team ->
                    if (team.id == teamId) team.copy(name = name.ifBlank { "未命名队伍" }) else team
                }
            )
        }
    }

    fun changeScore(teamId: Int, delta: Int) {
        pushHistory()
        _uiState.update { state ->
            state.copy(
                teams = state.teams.map { team ->
                    if (team.id == teamId) team.copy(score = team.score + delta) else team
                },
                canUndo = history.isNotEmpty()
            )
        }
    }

    fun resetScores() {
        pushHistory()
        _uiState.update { state ->
            state.copy(
                teams = state.teams.map { it.copy(score = 0) },
                canUndo = history.isNotEmpty()
            )
        }
    }

    fun undo() {
        val previous = history.removeLastOrNull() ?: return
        _uiState.update {
            it.copy(
                teams = previous,
                canUndo = history.isNotEmpty()
            )
        }
    }

    private fun pushHistory() {
        history.addLast(_uiState.value.teams)
        if (history.size > 20) {
            history.removeFirst()
        }
    }
}
