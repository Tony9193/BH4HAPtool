package com.example.bh4haptool.feature.shakedraw.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bh4haptool.core.toolkit.data.ToolboxPreferencesRepository
import com.example.bh4haptool.core.toolkit.draw.CandidateParser
import com.example.bh4haptool.core.toolkit.draw.DrawEngine
import com.example.bh4haptool.feature.shakedraw.sensor.ActiveSensorMode
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
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
            val parsedCount = CandidateParser.parse(settings.shakeDrawCandidates).size
            _uiState.value = ShakeDrawUiState(
                candidatesInput = settings.shakeDrawCandidates,
                parsedCount = parsedCount,
                gyroscopeThreshold = settings.shakeGyroscopeThreshold,
                accelerometerThreshold = settings.shakeAccelerometerThreshold,
                cooldownMs = settings.shakeCooldownMs
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
            preferencesRepository.updateShakeDrawCandidates(value)
        }
    }

    fun onSensorModeChanged(mode: ActiveSensorMode) {
        _uiState.update { current ->
            current.copy(sensorMode = mode)
        }
    }

    fun manualDraw() {
        performDraw(triggerSource = "手动触发")
    }

    fun onShakeTriggered() {
        performDraw(triggerSource = "摇一摇触发")
    }

    fun clear() {
        _uiState.value = ShakeDrawUiState(
            gyroscopeThreshold = _uiState.value.gyroscopeThreshold,
            accelerometerThreshold = _uiState.value.accelerometerThreshold,
            cooldownMs = _uiState.value.cooldownMs,
            sensorMode = _uiState.value.sensorMode
        )
        viewModelScope.launch {
            preferencesRepository.updateShakeDrawCandidates("")
        }
    }

    private fun performDraw(triggerSource: String) {
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
                message = "$triggerSource：共 ${result.poolSize} 人，已完成随机抽取"
            )
        }
    }
}
