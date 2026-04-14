package com.example.bh4haptool.feature.catchcat.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bh4haptool.feature.catchcat.R
import com.example.bh4haptool.feature.catchcat.domain.CatchCatStatus
import com.example.bh4haptool.feature.catchcat.domain.HexCoord
import kotlin.math.min
import kotlin.math.sqrt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatchCatRoute(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CatchCatViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.catch_cat_title)) },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text(text = stringResource(R.string.catch_cat_back))
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CatchCatBoard(
                uiState = uiState,
                onCellTapped = { coord ->
                    viewModel.onCellTapped(coord.i, coord.j)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .sizeIn(minHeight = 260.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = viewModel::onResetClicked,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = stringResource(R.string.catch_cat_reset))
                }
                OutlinedButton(
                    onClick = viewModel::onUndoClicked,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = stringResource(R.string.catch_cat_undo))
                }
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.catch_cat_status_label),
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = statusText(uiState.status),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = stringResource(R.string.catch_cat_moves, uiState.moveCount),
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = stringResource(
                            R.string.catch_cat_initial_walls,
                            uiState.initialWallCount
                        ),
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = stringResource(R.string.catch_cat_message_label),
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = uiState.statusMessage,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = stringResource(R.string.catch_cat_description),
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = stringResource(R.string.catch_cat_help),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
private fun statusText(status: CatchCatStatus): String {
    return when (status) {
        CatchCatStatus.PLAYING -> stringResource(R.string.catch_cat_state_playing)
        CatchCatStatus.PLAYER_WIN -> stringResource(R.string.catch_cat_state_win)
        CatchCatStatus.CAT_ESCAPE -> stringResource(R.string.catch_cat_state_lose)
    }
}

@Composable
private fun CatchCatBoard(
    uiState: CatchCatUiState,
    onCellTapped: (HexCoord) -> Unit,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme
    var boardSize by remember { mutableStateOf(IntSize.Zero) }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .background(
                color = colorScheme.surfaceVariant,
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged { boardSize = it }
        ) {
            val layout = buildBoardLayout(
                width = uiState.width,
                height = uiState.height,
                canvasSize = size
            )
            for (cell in layout.cells) {
                val isWall = cell.coord in uiState.walls
                val isCat = cell.coord == uiState.cat
                val isEdge = isEdgeCell(
                    coord = cell.coord,
                    width = uiState.width,
                    height = uiState.height
                )

                val fillColor = when {
                    isCat -> colorScheme.tertiaryContainer
                    isWall -> colorScheme.primary
                    else -> Color(0xFFB3D9FF)
                }
                val strokeColor = when {
                    isCat -> colorScheme.onTertiaryContainer
                    isWall -> colorScheme.onPrimary
                    else -> colorScheme.outline
                }

                drawCircle(
                    color = fillColor,
                    radius = layout.cellRadius * 0.78f,
                    center = cell.center
                )
                drawCircle(
                    color = strokeColor,
                    radius = layout.cellRadius * 0.78f,
                    center = cell.center,
                    style = Stroke(width = layout.cellRadius * 0.12f)
                )

                if (isEdge && !isWall && !isCat) {
                    drawCircle(
                        color = colorScheme.outlineVariant,
                        radius = layout.cellRadius * 0.88f,
                        center = cell.center,
                        style = Stroke(width = layout.cellRadius * 0.05f)
                    )
                }

                if (isCat) {
                    drawCircle(
                        color = colorScheme.onTertiaryContainer,
                        radius = layout.cellRadius * 0.26f,
                        center = cell.center
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(
                    uiState.width,
                    uiState.height,
                    uiState.walls,
                    uiState.cat,
                    boardSize
                ) {
                    detectTapGestures { tap ->
                        if (boardSize.width == 0 || boardSize.height == 0) {
                            return@detectTapGestures
                        }
                        val layout = buildBoardLayout(
                            width = uiState.width,
                            height = uiState.height,
                            canvasSize = Size(
                                width = boardSize.width.toFloat(),
                                height = boardSize.height.toFloat()
                            )
                        )
                        layout.findNearest(tap)?.let(onCellTapped)
                    }
                }
        )
    }
}

private fun isEdgeCell(
    coord: HexCoord,
    width: Int,
    height: Int
): Boolean {
    return coord.i <= 0 || coord.i >= width - 1 || coord.j <= 0 || coord.j >= height - 1
}

private data class BoardCellLayout(
    val coord: HexCoord,
    val center: Offset
)

private data class BoardLayout(
    val cellRadius: Float,
    val cells: List<BoardCellLayout>
) {
    fun findNearest(point: Offset): HexCoord? {
        if (cells.isEmpty()) {
            return null
        }

        var bestCell: BoardCellLayout? = null
        var bestDistanceSquared = Float.MAX_VALUE

        for (cell in cells) {
            val dx = cell.center.x - point.x
            val dy = cell.center.y - point.y
            val distanceSquared = dx * dx + dy * dy
            if (distanceSquared < bestDistanceSquared) {
                bestDistanceSquared = distanceSquared
                bestCell = cell
            }
        }

        val distance = sqrt(bestDistanceSquared)
        val clickThreshold = cellRadius * 1.2f
        return if (bestCell != null && distance <= clickThreshold) {
            bestCell.coord
        } else {
            null
        }
    }
}

private fun buildBoardLayout(
    width: Int,
    height: Int,
    canvasSize: Size
): BoardLayout {
    if (width <= 0 || height <= 0 || canvasSize.width <= 0f || canvasSize.height <= 0f) {
        return BoardLayout(
            cellRadius = 0f,
            cells = emptyList()
        )
    }

    val sqrt3 = sqrt(3f)
    val radius = min(
        canvasSize.width / (2f * width + 6.5f),
        canvasSize.height / (sqrt3 * height + 6f)
    )

    val cells = ArrayList<BoardCellLayout>(width * height)
    for (j in 0 until height) {
        for (i in 0 until width) {
            val x = radius * 3f + (if ((j and 1) == 0) radius else radius * 2f) + i * radius * 2f
            val y = radius * 3f + radius + j * radius * sqrt3
            cells += BoardCellLayout(
                coord = HexCoord(i, j),
                center = Offset(x, y)
            )
        }
    }

    return BoardLayout(
        cellRadius = radius,
        cells = cells
    )
}
