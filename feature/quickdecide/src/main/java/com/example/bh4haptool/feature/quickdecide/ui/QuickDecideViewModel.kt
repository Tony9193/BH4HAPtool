package com.example.bh4haptool.feature.quickdecide.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class QuickDecideViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(QuickDecideUiState())
    val uiState: StateFlow<QuickDecideUiState> = _uiState.asStateFlow()

    fun rollDice() {
        _uiState.update { it.copy(diceResult = Random.nextInt(1, 7)) }
    }

    fun flipCoin() {
        _uiState.update { it.copy(coinResult = if (Random.nextBoolean()) CoinResult.HEADS else CoinResult.TAILS) }
    }

    fun decideYesNo() {
        _uiState.update { it.copy(yesNoResult = if (Random.nextBoolean()) YesNoResult.YES else YesNoResult.NO) }
    }
}
