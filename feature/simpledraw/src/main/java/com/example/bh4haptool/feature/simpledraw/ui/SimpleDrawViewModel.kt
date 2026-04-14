package com.example.bh4haptool.feature.simpledraw.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bh4haptool.core.toolkit.data.ToolboxPreferencesRepository
import com.example.bh4haptool.core.toolkit.draw.CandidateParser
import com.example.bh4haptool.core.toolkit.draw.DrawEngine
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SimpleDrawViewModel @Inject constructor(
    private val drawEngine: DrawEngine,
    private val preferencesRepository: ToolboxPreferencesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SimpleDrawUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val settings = preferencesRepository.settingsFlow.first()
            val parsedCount = CandidateParser.parse(settings.simpleDrawCandidates).size
            _uiState.value = SimpleDrawUiState(
                candidatesInput = settings.simpleDrawCandidates,
                parsedCount = parsedCount
            )
        }
    }

    fun onCandidatesInputChanged(value: String) {
        val parsedCount = CandidateParser.parse(value).size
        _uiState.update { current ->
            current.copy(
                candidatesInput = value,
                parsedCount = parsedCount,
                message = null
            )
        }
        viewModelScope.launch {
            preferencesRepository.updateSimpleDrawCandidates(value)
        }
    }

    fun draw() {
        val candidates = CandidateParser.parse(_uiState.value.candidatesInput)
        if (candidates.isEmpty()) {
            _uiState.update { current ->
                current.copy(
                    winner = null,
                    message = "请先输入至少一个有效候选人"
                )
            }
            return
        }

        val result = drawEngine.draw(candidates) ?: return
        _uiState.update { current ->
            current.copy(
                winner = result.winner,
                message = "共 ${result.poolSize} 人，已完成随机抽取"
            )
        }
    }

    fun clear() {
        _uiState.value = SimpleDrawUiState()
        viewModelScope.launch {
            preferencesRepository.updateSimpleDrawCandidates("")
        }
    }
}
