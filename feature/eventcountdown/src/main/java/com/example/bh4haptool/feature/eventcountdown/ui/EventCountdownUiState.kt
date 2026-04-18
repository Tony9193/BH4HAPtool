package com.example.bh4haptool.feature.eventcountdown.ui

import com.example.bh4haptool.feature.eventcountdown.domain.CountdownStage

data class EventCountdownUiState(
    val stagesInput: String = DEFAULT_STAGES_INPUT,
    val stages: List<CountdownStage> = emptyList(),
    val currentStageIndex: Int = 0,
    val remainingSeconds: Int = 0,
    val totalSeconds: Int = 0,
    val isRunning: Boolean = false,
    val isFinished: Boolean = false,
    val message: String? = null
)

const val DEFAULT_STAGES_INPUT = "集合|5\n游戏|20\n休息|10"
