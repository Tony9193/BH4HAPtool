package com.example.bh4haptool.feature.shakedraw.ui

import com.example.bh4haptool.core.toolkit.data.DEFAULT_ACCELEROMETER_THRESHOLD
import com.example.bh4haptool.core.toolkit.data.DEFAULT_GYROSCOPE_THRESHOLD
import com.example.bh4haptool.core.toolkit.data.DEFAULT_SHAKE_COOLDOWN_MS
import com.example.bh4haptool.feature.shakedraw.sensor.ActiveSensorMode

data class ShakeDrawUiState(
    val candidatesInput: String = "",
    val parsedCount: Int = 0,
    val winner: String? = null,
    val message: String? = null,
    val sensorMode: ActiveSensorMode = ActiveSensorMode.UNSUPPORTED,
    val gyroscopeThreshold: Float = DEFAULT_GYROSCOPE_THRESHOLD,
    val accelerometerThreshold: Float = DEFAULT_ACCELEROMETER_THRESHOLD,
    val cooldownMs: Int = DEFAULT_SHAKE_COOLDOWN_MS
)
