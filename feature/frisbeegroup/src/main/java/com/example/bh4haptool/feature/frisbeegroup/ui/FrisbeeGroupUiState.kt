package com.example.bh4haptool.feature.frisbeegroup.ui

import com.example.bh4haptool.feature.frisbeegroup.domain.FrisbeePalette
import com.example.bh4haptool.feature.frisbeegroup.sensor.CompassSensorMode

enum class FrisbeeScreen {
    SETUP,
    COMPASS,
    DRAW,
    RESULTS
}

enum class PlayerInputMode {
    COUNT,
    NAMES
}

data class FrisbeeGroupUiState(
    val screen: FrisbeeScreen = FrisbeeScreen.SETUP,
    val inputMode: PlayerInputMode = PlayerInputMode.COUNT,
    val playerCount: Int = 12,
    val teamCount: Int = 2,
    val namesInput: String = "",
    val palette: FrisbeePalette = FrisbeePalette.RAINBOW,
    val players: List<String> = emptyList(),
    val assignments: List<Int> = emptyList(),
    val drawRevealCount: Int = 0,
    val drawLastPlayer: String? = null,
    val drawLastTeam: Int? = null,
    val compassHeading: Float = 0f,
    val compassPointerPlayer: Int = 0,
    val gyroscopeSpeed: Float = 0f,
    val sensorMode: CompassSensorMode = CompassSensorMode.UNSUPPORTED,
    val message: String? = null
)
