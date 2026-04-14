package com.example.bh4haptool.feature.shakedraw.sensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.abs
import kotlin.math.sqrt

enum class ActiveSensorMode {
    ACCELEROMETER,
    GYROSCOPE_FALLBACK,
    UNSUPPORTED
}

class ShakeDetector(
    private val sensorManager: SensorManager,
    private val gyroscopeThreshold: Float,
    private val accelerometerThreshold: Float,
    private val cooldownMs: Int,
    private val onShake: () -> Unit,
    private val onModeChanged: (ActiveSensorMode) -> Unit
) : SensorEventListener {
    private val activeSensor: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            ?: sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    private val activeMode: ActiveSensorMode = when (activeSensor?.type) {
        Sensor.TYPE_ACCELEROMETER -> ActiveSensorMode.ACCELEROMETER
        Sensor.TYPE_GYROSCOPE -> ActiveSensorMode.GYROSCOPE_FALLBACK
        else -> ActiveSensorMode.UNSUPPORTED
    }

    private var lastTriggeredAt = 0L

    fun start() {
        onModeChanged(activeMode)
        activeSensor ?: return
        sensorManager.registerListener(this, activeSensor, SensorManager.SENSOR_DELAY_GAME)
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        val values = event.values
        if (values.size < 3) {
            return
        }

        val magnitude = sqrt(
            (values[0] * values[0] + values[1] * values[1] + values[2] * values[2]).toDouble()
        ).toFloat()

        val now = System.currentTimeMillis()
        val shouldTrigger = when (activeMode) {
            ActiveSensorMode.GYROSCOPE_FALLBACK -> magnitude >= gyroscopeThreshold
            ActiveSensorMode.ACCELEROMETER -> {
                abs(magnitude - SensorManager.GRAVITY_EARTH) >= accelerometerThreshold
            }
            ActiveSensorMode.UNSUPPORTED -> false
        }

        if (shouldTrigger && now - lastTriggeredAt >= cooldownMs) {
            lastTriggeredAt = now
            onShake()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}
