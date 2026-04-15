package com.example.bh4haptool.feature.pomodoro.ui

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bh4haptool.core.toolkit.data.ToolboxPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class PomodoroViewModel @Inject constructor(
    private val preferencesRepository: ToolboxPreferencesRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(PomodoroUiState())
    val uiState: StateFlow<PomodoroUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null

    init {
        viewModelScope.launch {
            preferencesRepository.settingsFlow.collect { settings ->
                val today = LocalDate.now().toString()
                val isSameDay = settings.pomodoroLastRecordDate == today
                val dailyCount = if (isSameDay) settings.pomodoroDailyCompletedCount else 0

                val isRunning = _uiState.value.isRunning
                val shouldSyncDuration = !isRunning && timerJob == null

                _uiState.update { state ->
                    val newWorkDurationMin = settings.pomodoroWorkDurationMin.coerceAtLeast(1)
                    val newBreakDurationMin = settings.pomodoroBreakDurationMin.coerceAtLeast(1)
                    val currentPhase = state.phase
                    val newTotalSeconds = if (shouldSyncDuration) {
                        if (currentPhase == PomodoroPhase.WORK) newWorkDurationMin * 60
                        else newBreakDurationMin * 60
                    } else state.totalSeconds

                    state.copy(
                        dailyCompletedCount = dailyCount,
                        workDurationMin = newWorkDurationMin,
                        breakDurationMin = newBreakDurationMin,
                        vibrationEnabled = settings.pomodoroVibrationEnabled,
                        autoSwitchEnabled = settings.pomodoroAutoSwitchEnabled,
                        totalSeconds = newTotalSeconds,
                        remainingSeconds = if (shouldSyncDuration) newTotalSeconds else state.remainingSeconds
                    )
                }
            }
        }
    }

    fun start() {
        if (timerJob != null) return
        timerJob = viewModelScope.launch {
            while (_uiState.value.remainingSeconds > 0) {
                delay(1000)
                _uiState.update { it.copy(remainingSeconds = it.remainingSeconds - 1) }
            }
            onTimerFinished()
        }
        _uiState.update { it.copy(isRunning = true) }
    }

    fun pause() {
        timerJob?.cancel()
        timerJob = null
        _uiState.update { it.copy(isRunning = false) }
    }

    fun reset() {
        pause()
        val total = if (_uiState.value.phase == PomodoroPhase.WORK) {
            _uiState.value.workDurationMin * 60
        } else {
            _uiState.value.breakDurationMin * 60
        }
        _uiState.update { it.copy(remainingSeconds = total, totalSeconds = total) }
    }

    fun skip() {
        pause()
        switchPhase(manualSkip = true)
    }

    fun updateSettings(workMin: Int, breakMin: Int, vibration: Boolean, autoSwitch: Boolean) {
        viewModelScope.launch {
            preferencesRepository.updatePomodoroSettings(workMin, breakMin, vibration, autoSwitch)
        }
    }

    private fun onTimerFinished() {
        val currentPhase = _uiState.value.phase
        val autoSwitch = _uiState.value.autoSwitchEnabled
        val vibrationEnabled = _uiState.value.vibrationEnabled

        viewModelScope.launch {
            if (currentPhase == PomodoroPhase.WORK) {
                val today = LocalDate.now().toString()
                preferencesRepository.incrementPomodoroDailyCompletedCount(today)
            }

            if (vibrationEnabled) {
                vibrate()
            }

            if (autoSwitch) {
                switchPhase(manualSkip = false)
                start()
            } else {
                _uiState.update { it.copy(isRunning = false) }
            }
        }
    }

    private fun switchPhase(manualSkip: Boolean) {
        val nextPhase = if (_uiState.value.phase == PomodoroPhase.WORK) PomodoroPhase.BREAK else PomodoroPhase.WORK
        val nextTotal = if (nextPhase == PomodoroPhase.WORK) {
            _uiState.value.workDurationMin * 60
        } else {
            _uiState.value.breakDurationMin * 60
        }
        _uiState.update {
            it.copy(
                phase = nextPhase,
                remainingSeconds = nextTotal,
                totalSeconds = nextTotal,
                isRunning = false
            )
        }
    }

    private fun vibrate() {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator ?: return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(500)
        }
    }

    override fun onCleared() {
        super.onCleared()
        pause()
    }
}
