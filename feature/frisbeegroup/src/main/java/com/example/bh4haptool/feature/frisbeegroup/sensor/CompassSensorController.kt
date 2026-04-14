package com.example.bh4haptool.feature.frisbeegroup.sensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.sqrt

enum class CompassSensorMode {
    ROTATION_VECTOR,
    ACCELEROMETER_MAGNETIC_FIELD,
    UNSUPPORTED
}

class CompassSensorController(
    private val sensorManager: SensorManager,
    private val onHeadingChanged: (Float) -> Unit,
    private val onGyroscopeSpeedChanged: (Float) -> Unit,
    private val onModeChanged: (CompassSensorMode) -> Unit
) : SensorEventListener {

    private val rotationVectorSensor: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
    private val accelerometerSensor: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private val magneticFieldSensor: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    private val gyroscopeSensor: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    private val mode: CompassSensorMode = when {
        rotationVectorSensor != null -> CompassSensorMode.ROTATION_VECTOR
        accelerometerSensor != null && magneticFieldSensor != null -> {
            CompassSensorMode.ACCELEROMETER_MAGNETIC_FIELD
        }
        else -> CompassSensorMode.UNSUPPORTED
    }

    private val accelValues = FloatArray(3)
    private val magneticValues = FloatArray(3)
    private var hasAccelValues = false
    private var hasMagneticValues = false

    fun start() {
        onModeChanged(mode)
        if (mode == CompassSensorMode.UNSUPPORTED) {
            return
        }

        if (mode == CompassSensorMode.ROTATION_VECTOR) {
            rotationVectorSensor?.let {
                sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
            }
        } else {
            accelerometerSensor?.let {
                sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
            }
            magneticFieldSensor?.let {
                sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
            }
        }

        gyroscopeSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor.type) {
            Sensor.TYPE_ROTATION_VECTOR -> {
                emitHeadingFromRotationVector(event.values)
            }

            Sensor.TYPE_ACCELEROMETER -> {
                System.arraycopy(event.values, 0, accelValues, 0, minOf(event.values.size, 3))
                hasAccelValues = true
                emitHeadingFromAccelMag()
            }

            Sensor.TYPE_MAGNETIC_FIELD -> {
                System.arraycopy(event.values, 0, magneticValues, 0, minOf(event.values.size, 3))
                hasMagneticValues = true
                emitHeadingFromAccelMag()
            }

            Sensor.TYPE_GYROSCOPE -> {
                if (event.values.size >= 3) {
                    val speed = sqrt(
                        event.values[0] * event.values[0] +
                            event.values[1] * event.values[1] +
                            event.values[2] * event.values[2]
                    )
                    onGyroscopeSpeedChanged(speed)
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    private fun emitHeadingFromRotationVector(values: FloatArray) {
        val rotationMatrix = FloatArray(9)
        val orientation = FloatArray(3)
        SensorManager.getRotationMatrixFromVector(rotationMatrix, values)
        SensorManager.getOrientation(rotationMatrix, orientation)
        val azimuth = Math.toDegrees(orientation[0].toDouble()).toFloat()
        onHeadingChanged(normalizeHeading(azimuth))
    }

    private fun emitHeadingFromAccelMag() {
        if (!hasAccelValues || !hasMagneticValues) {
            return
        }
        val rotationMatrix = FloatArray(9)
        val orientation = FloatArray(3)
        val success = SensorManager.getRotationMatrix(
            rotationMatrix,
            null,
            accelValues,
            magneticValues
        )
        if (!success) {
            return
        }
        SensorManager.getOrientation(rotationMatrix, orientation)
        val azimuth = Math.toDegrees(orientation[0].toDouble()).toFloat()
        onHeadingChanged(normalizeHeading(azimuth))
    }

    private fun normalizeHeading(value: Float): Float {
        return ((value % 360f) + 360f) % 360f
    }
}
