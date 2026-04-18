package com.example.bh4haptool.feature.luckywheel.ui

import androidx.lifecycle.ViewModel
import com.example.bh4haptool.feature.luckywheel.domain.LuckyWheelEngine
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class LuckyWheelViewModel @Inject constructor(
    private val engine: LuckyWheelEngine
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        LuckyWheelUiState(
            parsedOptions = engine.parseOptions(DEFAULT_OPTIONS_INPUT)
        )
    )
    val uiState: StateFlow<LuckyWheelUiState> = _uiState.asStateFlow()

    fun updateOptionsInput(value: String) {
        val parsed = engine.parseOptions(value)
        _uiState.update {
            it.copy(
                optionsInput = value,
                parsedOptions = parsed,
                removedLabels = emptySet(),
                lastResult = null,
                history = emptyList(),
                errorMessage = null
            )
        }
    }

    fun setRemoveDrawnOption(enabled: Boolean) {
        _uiState.update {
            it.copy(
                removeDrawnOption = enabled,
                removedLabels = if (enabled) it.removedLabels else emptySet(),
                errorMessage = null
            )
        }
    }

    fun spin() {
        val state = _uiState.value
        val availableOptions = state.parsedOptions.filterNot {
            state.removeDrawnOption && state.removedLabels.contains(it.label)
        }

        val result = engine.spin(availableOptions)
        if (result == null) {
            _uiState.update {
                it.copy(errorMessage = "请先输入至少一个候选项")
            }
            return
        }

        _uiState.update {
            it.copy(
                lastResult = result,
                removedLabels = if (it.removeDrawnOption) it.removedLabels + result.label else it.removedLabels,
                history = listOf(result.label) + it.history,
                errorMessage = null
            )
        }
    }

    fun resetPool() {
        _uiState.update {
            it.copy(
                removedLabels = emptySet(),
                lastResult = null,
                history = emptyList(),
                errorMessage = null
            )
        }
    }
}
