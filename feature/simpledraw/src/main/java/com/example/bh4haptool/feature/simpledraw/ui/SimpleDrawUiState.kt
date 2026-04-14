package com.example.bh4haptool.feature.simpledraw.ui

data class SimpleDrawUiState(
    val candidatesInput: String = "",
    val parsedCount: Int = 0,
    val winner: String? = null,
    val message: String? = null
)
