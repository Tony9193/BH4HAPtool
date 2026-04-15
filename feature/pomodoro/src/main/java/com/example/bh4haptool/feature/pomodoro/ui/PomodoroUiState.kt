package com.example.bh4haptool.feature.pomodoro.ui

data class PomodoroUiState(
    val remainingSeconds: Int = 25 * 60,
    val totalSeconds: Int = 25 * 60,
    val phase: PomodoroPhase = PomodoroPhase.WORK,
    val isRunning: Boolean = false,
    val dailyCompletedCount: Int = 0,
    val workDurationMin: Int = 25,
    val breakDurationMin: Int = 5,
    val vibrationEnabled: Boolean = true,
    val autoSwitchEnabled: Boolean = true
)

enum class PomodoroPhase {
    WORK,
    BREAK
}
