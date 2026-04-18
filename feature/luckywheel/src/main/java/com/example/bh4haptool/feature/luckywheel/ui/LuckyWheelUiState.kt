package com.example.bh4haptool.feature.luckywheel.ui

import com.example.bh4haptool.feature.luckywheel.domain.LuckyWheelOption

data class LuckyWheelUiState(
    val optionsInput: String = DEFAULT_OPTIONS_INPUT,
    val parsedOptions: List<LuckyWheelOption> = emptyList(),
    val removedLabels: Set<String> = emptySet(),
    val removeDrawnOption: Boolean = true,
    val lastResult: LuckyWheelOption? = null,
    val history: List<String> = emptyList(),
    val errorMessage: String? = null
)

const val DEFAULT_OPTIONS_INPUT = "火锅|2\n烧烤|1\nKTV|1\n桌游|1"
