package com.example.bh4haptool.feature.shakedraw.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bh4haptool.core.toolkit.data.ToolboxPreferencesRepository
import com.example.bh4haptool.core.toolkit.draw.CandidateParser
import com.example.bh4haptool.core.toolkit.draw.DrawEngine
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ShakeDrawViewModel @Inject constructor(
    private val drawEngine: DrawEngine,
    private val preferencesRepository: ToolboxPreferencesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ShakeDrawUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val settings = preferencesRepository.settingsFlow.first()
            val initialCandidates = settings.shakeDrawCandidates.ifBlank {
                settings.simpleDrawCandidates
            }
            val parsedCount = CandidateParser.parse(initialCandidates).size
            _uiState.value = ShakeDrawUiState(
                candidatesInput = initialCandidates,
                parsedCount = parsedCount,
                gyroscopeThreshold = settings.shakeGyroscopeThreshold,
                accelerometerThreshold = settings.shakeAccelerometerThreshold,
                cooldownMs = settings.shakeCooldownMs
            )

            if (settings.shakeDrawCandidates.isBlank() && initialCandidates.isNotBlank()) {
                preferencesRepository.updateShakeDrawCandidates(initialCandidates)
            }
        }
    }

    fun onModeChanged(mode: ShakeDrawMode) {
        _uiState.update { current ->
            current.copy(
                mode = mode,
                winner = null,
                message = null
            )
        }
    }

    fun onCandidatesInputChanged(value: String) {
        val parsedCount = CandidateParser.parse(value).size
        _uiState.update { current ->
            current.copy(
                candidatesInput = value,
                parsedCount = parsedCount,
                winner = null,
                message = null
            )
        }
        viewModelScope.launch {
            preferencesRepository.updateShakeDrawCandidates(value)
            preferencesRepository.updateSimpleDrawCandidates(value)
        }
    }

    fun onRangeStartChanged(value: String) {
        _uiState.update { current ->
            current.copy(
                rangeStartInput = sanitizeNumberInput(value),
                winner = null,
                message = null
            )
        }
    }

    fun onRangeEndChanged(value: String) {
        _uiState.update { current ->
            current.copy(
                rangeEndInput = sanitizeNumberInput(value),
                winner = null,
                message = null
            )
        }
    }

    fun onShakeAvailabilityChanged(isSupported: Boolean) {
        _uiState.update { current ->
            current.copy(isShakeSupported = isSupported)
        }
    }

    fun manualDraw() {
        performDraw(triggerSource = "Manual trigger")
    }

    fun onShakeTriggered() {
        performDraw(triggerSource = "Shake trigger")
    }

    fun clear() {
        _uiState.update { current ->
            current.copy(
                candidatesInput = "",
                parsedCount = 0,
                rangeStartInput = "1",
                rangeEndInput = "100",
                winner = null,
                message = null
            )
        }
        viewModelScope.launch {
            preferencesRepository.updateShakeDrawCandidates("")
            preferencesRepository.updateSimpleDrawCandidates("")
        }
    }

    private fun performDraw(triggerSource: String) {
        when (_uiState.value.mode) {
            ShakeDrawMode.CANDIDATES -> drawCandidate(triggerSource)
            ShakeDrawMode.RANDOM_NUMBER -> drawRandomNumber(triggerSource)
        }
    }

    private fun drawCandidate(triggerSource: String) {
        val candidates = CandidateParser.parse(_uiState.value.candidatesInput)
        if (candidates.isEmpty()) {
            _uiState.update { current ->
                current.copy(
                    winner = null,
                    message = "Please enter at least one candidate."
                )
            }
            return
        }

        val result = drawEngine.draw(candidates) ?: return
        _uiState.update { current ->
            current.copy(
                winner = result.winner,
                message = "$triggerSource: picked from ${result.poolSize} candidates."
            )
        }
    }

    private fun drawRandomNumber(triggerSource: String) {
        val start = _uiState.value.rangeStartInput.toIntOrNull()
        val end = _uiState.value.rangeEndInput.toIntOrNull()

        if (start == null || end == null) {
            _uiState.update { current ->
                current.copy(
                    winner = null,
                    message = "Please enter a valid integer range."
                )
            }
            return
        }

        if (start > end) {
            _uiState.update { current ->
                current.copy(
                    winner = null,
                    message = "Minimum value must not exceed maximum value."
                )
            }
            return
        }

        val result = Random.nextLong(start.toLong(), end.toLong() + 1L).toInt()
        _uiState.update { current ->
            current.copy(
                winner = result.toString(),
                message = "$triggerSource: picked a number between $start and $end."
            )
        }
    }

    private fun sanitizeNumberInput(value: String): String {
        if (value.isEmpty()) {
            return value
        }

        val builder = StringBuilder()
        value.forEachIndexed { index, char ->
            when {
                char.isDigit() -> builder.append(char)
                char == '-' && index == 0 -> builder.append(char)
            }
        }
        return builder.toString()
    }
}
