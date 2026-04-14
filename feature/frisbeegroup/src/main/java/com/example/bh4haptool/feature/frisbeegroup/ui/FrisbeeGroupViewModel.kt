package com.example.bh4haptool.feature.frisbeegroup.ui

import androidx.lifecycle.ViewModel
import com.example.bh4haptool.feature.frisbeegroup.domain.FrisbeeGroupingEngine
import com.example.bh4haptool.feature.frisbeegroup.domain.FrisbeePalette
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class FrisbeeGroupViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(FrisbeeGroupUiState())
    val uiState = _uiState.asStateFlow()

    fun onInputModeChanged(mode: PlayerInputMode) {
        _uiState.update { it.copy(inputMode = mode, message = null) }
    }

    fun onPlayerCountChanged(value: Int) {
        _uiState.update { it.copy(playerCount = value.coerceIn(2, 100), message = null) }
    }

    fun onTeamCountChanged(value: Int) {
        _uiState.update { it.copy(teamCount = value.coerceIn(2, 8), message = null) }
    }

    fun onNamesInputChanged(value: String) {
        _uiState.update { it.copy(namesInput = value, message = null) }
    }

    fun onPaletteChanged(palette: FrisbeePalette) {
        _uiState.update { it.copy(palette = palette) }
    }


    fun onSensorModeChanged(mode: com.example.bh4haptool.feature.frisbeegroup.sensor.CompassSensorMode) {
        _uiState.update { it.copy(sensorMode = mode) }
    }

    fun onGyroscopeSpeedChanged(speed: Float) {
        _uiState.update { it.copy(gyroscopeSpeed = speed) }
    }

    fun onCompassHeadingChanged(heading: Float) {
        _uiState.update { current ->
            val pointerPlayer = FrisbeeGroupingEngine.pointerPlayerIndex(
                totalPlayers = current.players.ifEmpty {
                    FrisbeeGroupingEngine.buildPlayers(
                        useNames = false,
                        namesInput = "",
                        count = current.playerCount
                    )
                }.size,
                heading = heading
            )
            current.copy(
                compassHeading = heading,
                compassPointerPlayer = pointerPlayer
            )
        }
    }

    fun onManualCompassAngleChanged(heading: Float) {
        onCompassHeadingChanged(heading)
    }

    fun startCompassMode() {
        val next = buildNewRound(FrisbeeScreen.COMPASS)
        _uiState.value = next
    }

    fun startDrawMode() {
        val next = buildNewRound(FrisbeeScreen.DRAW)
        _uiState.value = next
    }

    fun revealNext() {
        val current = _uiState.value
        if (current.screen != FrisbeeScreen.DRAW) {
            return
        }
        val nextIndex = current.drawRevealCount
        if (nextIndex >= current.players.size) {
            _uiState.update { it.copy(message = "全部揭晓完成") }
            return
        }

        val team = current.assignments[nextIndex]
        val player = current.players[nextIndex]
        _uiState.update {
            it.copy(
                drawRevealCount = nextIndex + 1,
                drawLastPlayer = player,
                drawLastTeam = team,
                message = if (nextIndex + 1 >= current.players.size) "全部揭晓完成" else null
            )
        }
    }

    fun openResults() {
        _uiState.update { it.copy(screen = FrisbeeScreen.RESULTS) }
    }

    fun backToSetup() {
        _uiState.update {
            it.copy(
                screen = FrisbeeScreen.SETUP,
                drawRevealCount = 0,
                drawLastPlayer = null,
                drawLastTeam = null,
                message = null
            )
        }
    }

    private fun buildNewRound(screen: FrisbeeScreen): FrisbeeGroupUiState {
        val current = _uiState.value
        val players = FrisbeeGroupingEngine.buildPlayers(
            useNames = current.inputMode == PlayerInputMode.NAMES,
            namesInput = current.namesInput,
            count = current.playerCount
        )
        val teams = current.teamCount.coerceIn(2, 8)
        val assignments = FrisbeeGroupingEngine.buildAssignments(players.size, teams)
        val pointer = FrisbeeGroupingEngine.pointerPlayerIndex(players.size, 0f)

        return current.copy(
            screen = screen,
            players = players,
            assignments = assignments,
            drawRevealCount = 0,
            drawLastPlayer = null,
            drawLastTeam = null,
            compassHeading = 0f,
            compassPointerPlayer = pointer,
            message = null
        )
    }
}
