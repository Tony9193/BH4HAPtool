package com.example.bh4haptool.feature.turnqueue.ui

enum class TurnQueueMode {
    RANDOM_FIRST,
    SEQUENTIAL,
    ELIMINATION
}

data class TurnQueueUiState(
    val participantsInput: String = DEFAULT_PARTICIPANTS_INPUT,
    val participants: List<String> = emptyList(),
    val mode: TurnQueueMode = TurnQueueMode.RANDOM_FIRST,
    val currentIndex: Int = 0,
    val round: Int = 1,
    val started: Boolean = false,
    val winner: String? = null,
    val message: String? = null
)

const val DEFAULT_PARTICIPANTS_INPUT = "阿明\n小雨\nTony\n嘉嘉"
