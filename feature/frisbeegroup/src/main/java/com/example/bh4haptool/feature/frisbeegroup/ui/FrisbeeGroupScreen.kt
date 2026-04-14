package com.example.bh4haptool.feature.frisbeegroup.ui

import android.content.Context
import android.hardware.SensorManager
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bh4haptool.feature.frisbeegroup.R
import com.example.bh4haptool.feature.frisbeegroup.domain.FrisbeeGroupingEngine
import com.example.bh4haptool.feature.frisbeegroup.domain.FrisbeePalette
import com.example.bh4haptool.feature.frisbeegroup.domain.TeamStyle
import com.example.bh4haptool.feature.frisbeegroup.sensor.CompassSensorController
import com.example.bh4haptool.feature.frisbeegroup.sensor.CompassSensorMode
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FrisbeeGroupRoute(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FrisbeeGroupViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val sensorManager = remember {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
    val onHeadingChanged by rememberUpdatedState(newValue = viewModel::onCompassHeadingChanged)
    val onGyroChanged by rememberUpdatedState(newValue = viewModel::onGyroscopeSpeedChanged)
    val onSensorModeChanged by rememberUpdatedState(newValue = viewModel::onSensorModeChanged)

    val sensorController = remember(sensorManager) {
        CompassSensorController(
            sensorManager = sensorManager,
            onHeadingChanged = onHeadingChanged,
            onGyroscopeSpeedChanged = onGyroChanged,
            onModeChanged = onSensorModeChanged
        )
    }

    DisposableEffect(uiState.screen, sensorController) {
        if (uiState.screen == FrisbeeScreen.COMPASS) {
            sensorController.start()
        } else {
            sensorController.stop()
        }
        onDispose {
            sensorController.stop()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (uiState.screen) {
                            FrisbeeScreen.SETUP -> stringResource(R.string.frisbee_setup_title)
                            FrisbeeScreen.COMPASS -> stringResource(R.string.frisbee_mode_compass)
                            FrisbeeScreen.DRAW -> stringResource(R.string.frisbee_mode_draw)
                            FrisbeeScreen.RESULTS -> stringResource(R.string.frisbee_results_title)
                        }
                    )
                },
                navigationIcon = {
                    TextButton(
                        onClick = {
                            if (uiState.screen == FrisbeeScreen.SETUP) {
                                onBack()
                            } else {
                                viewModel.backToSetup()
                            }
                        }
                    ) {
                        Text(stringResource(R.string.frisbee_group_back))
                    }
                },
                actions = {
                    if (uiState.screen == FrisbeeScreen.COMPASS || uiState.screen == FrisbeeScreen.DRAW) {
                        TextButton(onClick = viewModel::openResults) {
                            Text(stringResource(R.string.frisbee_open_results))
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        when (uiState.screen) {
            FrisbeeScreen.SETUP -> SetupContent(
                uiState = uiState,
                onInputModeChanged = viewModel::onInputModeChanged,
                onPlayerCountChanged = viewModel::onPlayerCountChanged,
                onTeamCountChanged = viewModel::onTeamCountChanged,
                onNamesInputChanged = viewModel::onNamesInputChanged,
                onPaletteChanged = viewModel::onPaletteChanged,
                onStartCompass = viewModel::startCompassMode,
                onStartDraw = viewModel::startDrawMode,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )

            FrisbeeScreen.COMPASS -> CompassContent(
                uiState = uiState,
                onFinish = viewModel::openResults,
                onManualAngle = viewModel::onManualCompassAngleChanged,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )

            FrisbeeScreen.DRAW -> DrawContent(
                uiState = uiState,
                onReveal = viewModel::revealNext,
                onFinish = viewModel::openResults,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )

            FrisbeeScreen.RESULTS -> ResultsContent(
                uiState = uiState,
                onRestart = {
                    if (uiState.players.isNotEmpty()) {
                        viewModel.startDrawMode()
                    } else {
                        viewModel.backToSetup()
                    }
                },
                onBackToSetup = viewModel::backToSetup,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )
        }
    }
}

@Composable
private fun SetupContent(
    uiState: FrisbeeGroupUiState,
    onInputModeChanged: (PlayerInputMode) -> Unit,
    onPlayerCountChanged: (Int) -> Unit,
    onTeamCountChanged: (Int) -> Unit,
    onNamesInputChanged: (String) -> Unit,
    onPaletteChanged: (FrisbeePalette) -> Unit,
    onStartCompass: () -> Unit,
    onStartDraw: () -> Unit,
    modifier: Modifier = Modifier
) {
    val teamHint = FrisbeeGroupingEngine.teamSizeHint(
        totalPlayers = if (uiState.inputMode == PlayerInputMode.NAMES) {
            maxOf(2, FrisbeeGroupingEngine.parseNames(uiState.namesInput).size)
        } else {
            uiState.playerCount
        },
        totalTeams = uiState.teamCount
    )

    val paletteLabels = paletteLabels()

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.frisbee_mode_title),
            style = MaterialTheme.typography.bodyMedium
        )

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = stringResource(R.string.frisbee_input_mode_label),
                    style = MaterialTheme.typography.labelLarge
                )
                SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                    val options = listOf(PlayerInputMode.COUNT, PlayerInputMode.NAMES)
                    options.forEachIndexed { index, mode ->
                        SegmentedButton(
                            selected = uiState.inputMode == mode,
                            onClick = { onInputModeChanged(mode) },
                            shape = SegmentedButtonDefaults.itemShape(index, options.size)
                        ) {
                            Text(
                                text = if (mode == PlayerInputMode.COUNT) {
                                    stringResource(R.string.frisbee_input_mode_count)
                                } else {
                                    stringResource(R.string.frisbee_input_mode_names)
                                }
                            )
                        }
                    }
                }

                if (uiState.inputMode == PlayerInputMode.COUNT) {
                    NumberAdjuster(
                        label = stringResource(R.string.frisbee_player_count_label),
                        value = uiState.playerCount,
                        range = 2..100,
                        onChanged = onPlayerCountChanged
                    )
                } else {
                    OutlinedTextField(
                        value = uiState.namesInput,
                        onValueChange = onNamesInputChanged,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(stringResource(R.string.frisbee_names_label)) },
                        placeholder = { Text(stringResource(R.string.frisbee_names_placeholder)) },
                        minLines = 4
                    )
                }

                NumberAdjuster(
                    label = stringResource(R.string.frisbee_team_count_label),
                    value = uiState.teamCount,
                    range = 2..8,
                    onChanged = onTeamCountChanged
                )

                PaletteSelector(
                    selected = uiState.palette,
                    labels = paletteLabels,
                    onChanged = onPaletteChanged
                )

                Text(
                    text = stringResource(R.string.frisbee_team_size_hint, teamHint),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(onClick = onStartCompass, modifier = Modifier.weight(1f)) {
                Text(stringResource(R.string.frisbee_mode_compass))
            }
            OutlinedButton(onClick = onStartDraw, modifier = Modifier.weight(1f)) {
                Text(stringResource(R.string.frisbee_mode_draw))
            }
        }

        Text(
            text = stringResource(R.string.frisbee_mode_compass_desc),
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = stringResource(R.string.frisbee_mode_draw_desc),
            style = MaterialTheme.typography.bodySmall
        )
        uiState.message?.let {
            Text(text = it, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun NumberAdjuster(
    label: String,
    value: Int,
    range: IntRange,
    onChanged: (Int) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(text = label, style = MaterialTheme.typography.labelLarge)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(onClick = { onChanged((value - 1).coerceAtLeast(range.first)) }) {
                Text("-")
            }
            Text(
                text = value.toString(),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            OutlinedButton(onClick = { onChanged((value + 1).coerceAtMost(range.last)) }) {
                Text("+")
            }
        }
    }
}

@Composable
private fun PaletteSelector(
    selected: FrisbeePalette,
    labels: Map<FrisbeePalette, String>,
    onChanged: (FrisbeePalette) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = stringResource(R.string.frisbee_palette_label),
            style = MaterialTheme.typography.labelLarge
        )
        Box {
            OutlinedButton(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
                Text(labels[selected].orEmpty())
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.widthIn(min = 220.dp)
            ) {
                FrisbeePalette.entries.forEach { palette ->
                    DropdownMenuItem(
                        text = { Text(labels[palette].orEmpty()) },
                        onClick = {
                            onChanged(palette)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun CompassContent(
    uiState: FrisbeeGroupUiState,
    onFinish: () -> Unit,
    onManualAngle: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    val paletteStyles = paletteStyles(uiState.palette)
    val pointerTeamIndex = uiState.assignments.getOrNull(uiState.compassPointerPlayer) ?: 0
    val teamStyle = paletteStyles[pointerTeamIndex % paletteStyles.size]

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = stringResource(R.string.frisbee_compass_sensor_label),
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = sensorModeLabel(uiState.sensorMode),
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = stringResource(R.string.frisbee_heading, uiState.compassHeading),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = stringResource(R.string.frisbee_gyro_speed, uiState.gyroscopeSpeed),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        CompassWheel(
            assignments = uiState.assignments,
            paletteStyles = paletteStyles,
            heading = uiState.compassHeading,
            modifier = Modifier
                .fillMaxWidth()
                .height(340.dp)
        )

        if (uiState.sensorMode == CompassSensorMode.UNSUPPORTED) {
            Text(
                text = "手动调试角度（设备不支持传感器）",
                style = MaterialTheme.typography.labelLarge
            )
            Slider(
                value = uiState.compassHeading,
                valueRange = 0f..360f,
                onValueChange = onManualAngle
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(Color(teamStyle.argb), CircleShape)
                )
                Text(
                    text = stringResource(
                        R.string.frisbee_pointer_team,
                        teamStyle.emoji,
                        teamStyle.name
                    ),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Button(onClick = onFinish, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.frisbee_finish_grouping))
        }

        uiState.message?.let {
            Text(text = it, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun CompassWheel(
    assignments: List<Int>,
    paletteStyles: List<TeamStyle>,
    heading: Float,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme

    Card(modifier = modifier) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Canvas(modifier = Modifier.fillMaxSize().padding(12.dp)) {
                if (assignments.isEmpty()) {
                    return@Canvas
                }
                val count = assignments.size
                val radius = size.minDimension / 2.2f
                val center = Offset(size.width / 2f, size.height / 2f)
                val sweep = 360f / count.toFloat()

                val globalRotation = -heading
                for (index in assignments.indices) {
                    val teamIndex = assignments[index]
                    val team = paletteStyles[teamIndex % paletteStyles.size]
                    val startAngle = -90f + index * sweep + globalRotation
                    drawArc(
                        color = Color(team.argb),
                        startAngle = startAngle,
                        sweepAngle = sweep,
                        useCenter = true,
                        topLeft = Offset(center.x - radius, center.y - radius),
                        size = androidx.compose.ui.geometry.Size(radius * 2f, radius * 2f)
                    )
                }

                drawCircle(
                    color = colorScheme.surface,
                    radius = radius * 0.42f,
                    center = center
                )
                drawCircle(
                    color = colorScheme.outline,
                    radius = radius * 0.42f,
                    center = center,
                    style = Stroke(width = 4f)
                )

                // Pointer is fixed facing "forward" (top of the screen)
                val fixedHeadingRadians = (-90f / 180f * PI).toFloat()
                val arrowEnd = Offset(
                    x = center.x + cos(fixedHeadingRadians) * radius,
                    y = center.y + sin(fixedHeadingRadians) * radius
                )
                drawLine(
                    color = colorScheme.onSurface,
                    start = center,
                    end = arrowEnd,
                    strokeWidth = 8f,
                    cap = StrokeCap.Round,
                    pathEffect = PathEffect.cornerPathEffect(12f)
                )
                drawCircle(
                    color = colorScheme.primary,
                    center = center,
                    radius = 12f
                )
            }
        }
    }
}

@Composable
private fun DrawContent(
    uiState: FrisbeeGroupUiState,
    onReveal: () -> Unit,
    onFinish: () -> Unit,
    modifier: Modifier = Modifier
) {
    val paletteStyles = paletteStyles(uiState.palette)
    val revealDone = uiState.drawRevealCount >= uiState.players.size && uiState.players.isNotEmpty()

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(
                R.string.frisbee_draw_progress,
                uiState.drawRevealCount,
                uiState.players.size
            ),
            style = MaterialTheme.typography.titleMedium
        )

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val teamIndex = uiState.drawLastTeam
                val teamStyle = teamIndex?.let { paletteStyles[it % paletteStyles.size] }
                if (teamStyle == null) {
                    Text(
                        text = "点击按钮揭晓下一位",
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .background(Color(teamStyle.argb), CircleShape)
                    )
                    Text(
                        text = uiState.drawLastPlayer.orEmpty(),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "${teamStyle.emoji} ${teamStyle.name}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onReveal,
                modifier = Modifier.weight(1f),
                enabled = !revealDone
            ) {
                Text(
                    if (revealDone) {
                        stringResource(R.string.frisbee_draw_done)
                    } else {
                        stringResource(R.string.frisbee_draw_action)
                    }
                )
            }
            OutlinedButton(onClick = onFinish, modifier = Modifier.weight(1f)) {
                Text(stringResource(R.string.frisbee_open_results))
            }
        }

        uiState.message?.let {
            Text(text = it, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun ResultsContent(
    uiState: FrisbeeGroupUiState,
    onRestart: () -> Unit,
    onBackToSetup: () -> Unit,
    modifier: Modifier = Modifier
) {
    val paletteStyles = paletteStyles(uiState.palette)
    val teamCount = uiState.teamCount.coerceIn(2, 8)

    val grouped = remember(uiState.players, uiState.assignments, teamCount) {
        List(teamCount) { mutableListOf<String>() }.also { result ->
            uiState.players.forEachIndexed { index, player ->
                val team = uiState.assignments.getOrElse(index) { 0 }.coerceIn(0, teamCount - 1)
                result[team].add(player)
            }
        }
    }

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(grouped) { index, members ->
                val style = paletteStyles[index % paletteStyles.size]
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .background(Color(style.argb), CircleShape)
                            )
                            Text(
                                text = "${style.emoji} ${style.name}（${members.size}人）",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        if (members.isEmpty()) {
                            Text("暂无成员", style = MaterialTheme.typography.bodyMedium)
                        } else {
                            members.forEachIndexed { i, member ->
                                Text("${i + 1}. $member", style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(onClick = onRestart, modifier = Modifier.weight(1f)) {
                Text(stringResource(R.string.frisbee_restart))
            }
            OutlinedButton(onClick = onBackToSetup, modifier = Modifier.weight(1f)) {
                Text(stringResource(R.string.frisbee_reset_to_setup))
            }
        }
    }
}

@Composable
private fun sensorModeLabel(mode: CompassSensorMode): String {
    return when (mode) {
        CompassSensorMode.ROTATION_VECTOR -> {
            stringResource(R.string.frisbee_sensor_rotation_vector)
        }
        CompassSensorMode.ACCELEROMETER_MAGNETIC_FIELD -> {
            stringResource(R.string.frisbee_sensor_acc_mag)
        }
        CompassSensorMode.UNSUPPORTED -> {
            stringResource(R.string.frisbee_sensor_unsupported)
        }
    }
}

@Composable
private fun paletteLabels(): Map<FrisbeePalette, String> {
    return mapOf(
        FrisbeePalette.RAINBOW to stringResource(R.string.frisbee_palette_rainbow),
        FrisbeePalette.BLUE_PURPLE to stringResource(R.string.frisbee_palette_blue_purple),
        FrisbeePalette.GREEN to stringResource(R.string.frisbee_palette_green),
        FrisbeePalette.GRAY to stringResource(R.string.frisbee_palette_gray),
        FrisbeePalette.SUNSET to stringResource(R.string.frisbee_palette_sunset),
        FrisbeePalette.OCEAN to stringResource(R.string.frisbee_palette_ocean),
        FrisbeePalette.NIGHT to stringResource(R.string.frisbee_palette_night)
    )
}

private fun paletteStyles(palette: FrisbeePalette): List<TeamStyle> {
    return FrisbeeGroupingEngine.palettes().getValue(palette)
}
