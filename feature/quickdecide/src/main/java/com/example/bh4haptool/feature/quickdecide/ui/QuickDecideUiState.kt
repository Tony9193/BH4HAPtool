package com.example.bh4haptool.feature.quickdecide.ui

data class QuickDecideUiState(
    val diceResult: Int? = null,
    val coinResult: CoinResult? = null,
    val yesNoResult: YesNoResult? = null
)

enum class CoinResult {
    HEADS, TAILS
}

enum class YesNoResult {
    YES, NO
}
