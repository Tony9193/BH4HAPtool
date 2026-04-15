package com.example.bh4haptool.feature.shakedraw.ui

import com.example.bh4haptool.core.toolkit.data.DEFAULT_ACCELEROMETER_THRESHOLD
import com.example.bh4haptool.core.toolkit.data.DEFAULT_GYROSCOPE_THRESHOLD
import com.example.bh4haptool.core.toolkit.data.DEFAULT_SHAKE_COOLDOWN_MS

enum class ShakeDrawMode {
    CANDIDATES,
    RANDOM_NUMBER
}

data class ShakeDrawUiState(
    val mode: ShakeDrawMode = ShakeDrawMode.CANDIDATES,
    val candidatesInput: String = "",
    val parsedCount: Int = 0,
    val rangeStartInput: String = "1",
    val rangeEndInput: String = "100",
    val winner: String? = null,
    val message: String? = null,
    val isShakeSupported: Boolean = true,
    val gyroscopeThreshold: Float = DEFAULT_GYROSCOPE_THRESHOLD,
    val accelerometerThreshold: Float = DEFAULT_ACCELEROMETER_THRESHOLD,
    val cooldownMs: Int = DEFAULT_SHAKE_COOLDOWN_MS
)
