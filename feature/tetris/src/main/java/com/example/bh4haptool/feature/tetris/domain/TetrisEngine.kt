package com.example.bh4haptool.feature.tetris.domain

import kotlin.random.Random

enum class TetrisStatus {
    READY,
    PLAYING,
    PAUSED,
    GAME_OVER
}

enum class TetrominoType {
    I,
    O,
    T,
    S,
    Z,
    J,
    L
}

data class TetrisCell(
    val row: Int,
    val col: Int,
    val color: Int
)

data class TetrisSnapshot(
    val width: Int,
    val height: Int,
    val settledBoard: List<List<Int>>,
    val activeCells: List<TetrisCell>,
    val nextType: TetrominoType,
    val score: Int,
    val level: Int,
    val linesCleared: Int,
    val status: TetrisStatus,
    val statusMessage: String,
    val dropIntervalMs: Int
)

private data class Tetromino(
    val type: TetrominoType,
    val rotation: Int,
    val row: Int,
    val col: Int
)

class TetrisEngine(
    private val width: Int = 10,
    private val height: Int = 20,
    private val random: Random = Random.Default
) {
    private var board: Array<IntArray> = Array(height) { IntArray(width) }
    private var currentPiece: Tetromino? = null
    private var nextPieceType: TetrominoType = randomType()

    private var startLevel: Int = 1
    private var score: Int = 0
    private var linesCleared: Int = 0
    private var level: Int = 1
    private var status: TetrisStatus = TetrisStatus.READY
    private var statusMessage: String = "点击新局开始"

    fun newGame(startLevel: Int = 1) {
        this.startLevel = startLevel.coerceIn(1, 10)
        score = 0
        linesCleared = 0
        level = this.startLevel
        status = TetrisStatus.PLAYING
        statusMessage = "游戏开始"

        board = Array(height) { IntArray(width) }
        currentPiece = null
        nextPieceType = randomType()
        spawnNextPiece()
    }

    fun togglePause() {
        status = when (status) {
            TetrisStatus.PLAYING -> {
                statusMessage = "已暂停"
                TetrisStatus.PAUSED
            }

            TetrisStatus.PAUSED -> {
                statusMessage = "继续游戏"
                TetrisStatus.PLAYING
            }

            else -> status
        }
    }

    fun moveLeft(): Boolean {
        if (status != TetrisStatus.PLAYING) {
            return false
        }
        return tryMove(0, -1)
    }

    fun moveRight(): Boolean {
        if (status != TetrisStatus.PLAYING) {
            return false
        }
        return tryMove(0, 1)
    }

    fun rotate(): Boolean {
        if (status != TetrisStatus.PLAYING) {
            return false
        }

        val piece = currentPiece ?: return false
        val rotated = piece.copy(rotation = (piece.rotation + 1) % 4)

        if (canPlace(rotated)) {
            currentPiece = rotated
            statusMessage = "旋转成功"
            return true
        }

        val kickLeft = rotated.copy(col = rotated.col - 1)
        if (canPlace(kickLeft)) {
            currentPiece = kickLeft
            statusMessage = "旋转成功"
            return true
        }

        val kickRight = rotated.copy(col = rotated.col + 1)
        if (canPlace(kickRight)) {
            currentPiece = kickRight
            statusMessage = "旋转成功"
            return true
        }

        return false
    }

    fun softDrop(): Boolean {
        if (status != TetrisStatus.PLAYING) {
            return false
        }

        if (tryMove(1, 0)) {
            score += 1
            return true
        }

        lockCurrentPiece()
        processAfterLock()
        return true
    }

    fun hardDrop(): Boolean {
        if (status != TetrisStatus.PLAYING) {
            return false
        }

        var droppedRows = 0
        while (tryMove(1, 0)) {
            droppedRows++
        }

        score += droppedRows * 2
        lockCurrentPiece()
        processAfterLock()
        return true
    }

    fun tick(): Boolean {
        if (status != TetrisStatus.PLAYING) {
            return false
        }

        if (tryMove(1, 0)) {
            return true
        }

        lockCurrentPiece()
        processAfterLock()
        return true
    }

    fun snapshot(): TetrisSnapshot {
        val active = currentPiece
            ?.let { piece ->
                cellsFor(piece).map { (row, col) ->
                    TetrisCell(
                        row = row,
                        col = col,
                        color = piece.type.ordinal + 1
                    )
                }
            }
            ?: emptyList()

        return TetrisSnapshot(
            width = width,
            height = height,
            settledBoard = board.map { row -> row.toList() },
            activeCells = active,
            nextType = nextPieceType,
            score = score,
            level = level,
            linesCleared = linesCleared,
            status = status,
            statusMessage = statusMessage,
            dropIntervalMs = computeDropInterval(level)
        )
    }

    private fun tryMove(deltaRow: Int, deltaCol: Int): Boolean {
        val piece = currentPiece ?: return false
        val moved = piece.copy(
            row = piece.row + deltaRow,
            col = piece.col + deltaCol
        )

        return if (canPlace(moved)) {
            currentPiece = moved
            true
        } else {
            false
        }
    }

    private fun spawnNextPiece() {
        val spawnCol = (width / 2) - 2
        val candidate = Tetromino(
            type = nextPieceType,
            rotation = 0,
            row = 0,
            col = spawnCol
        )
        nextPieceType = randomType()

        if (canPlace(candidate)) {
            currentPiece = candidate
            status = TetrisStatus.PLAYING
            statusMessage = "方块下落中"
        } else {
            currentPiece = null
            status = TetrisStatus.GAME_OVER
            statusMessage = "游戏结束"
        }
    }

    private fun lockCurrentPiece() {
        val piece = currentPiece ?: return

        for ((row, col) in cellsFor(piece)) {
            if (row in 0 until height && col in 0 until width) {
                board[row][col] = piece.type.ordinal + 1
            }
        }

        currentPiece = null
    }

    private fun processAfterLock() {
        val cleared = clearLines()
        if (cleared > 0) {
            linesCleared += cleared
            score += lineScore(cleared) * level
            statusMessage = "消除 $cleared 行"
        }

        level = (startLevel + linesCleared / 10).coerceAtMost(20)
        spawnNextPiece()
    }

    private fun clearLines(): Int {
        var clearedCount = 0
        var writeRow = height - 1

        for (readRow in height - 1 downTo 0) {
            val filled = board[readRow].all { value -> value != 0 }
            if (filled) {
                clearedCount++
                continue
            }

            if (writeRow != readRow) {
                board[writeRow] = board[readRow].clone()
            }
            writeRow--
        }

        for (row in writeRow downTo 0) {
            board[row] = IntArray(width)
        }

        return clearedCount
    }

    private fun lineScore(cleared: Int): Int {
        return when (cleared) {
            1 -> 100
            2 -> 300
            3 -> 500
            4 -> 800
            else -> 0
        }
    }

    private fun computeDropInterval(level: Int): Int {
        return (780 - (level - 1) * 45).coerceAtLeast(120)
    }

    private fun randomType(): TetrominoType {
        return TetrominoType.entries[random.nextInt(TetrominoType.entries.size)]
    }

    private fun canPlace(piece: Tetromino): Boolean {
        for ((row, col) in cellsFor(piece)) {
            if (row !in 0 until height || col !in 0 until width) {
                return false
            }
            if (board[row][col] != 0) {
                return false
            }
        }
        return true
    }

    private fun cellsFor(piece: Tetromino): List<Pair<Int, Int>> {
        val rotations = SHAPES[piece.type] ?: return emptyList()
        val offsets = rotations[piece.rotation % rotations.size]
        return offsets.map { (dr, dc) ->
            piece.row + dr to piece.col + dc
        }
    }

    private companion object {
        val SHAPES: Map<TetrominoType, Array<List<Pair<Int, Int>>>> = mapOf(
            TetrominoType.I to arrayOf(
                listOf(1 to 0, 1 to 1, 1 to 2, 1 to 3),
                listOf(0 to 2, 1 to 2, 2 to 2, 3 to 2),
                listOf(2 to 0, 2 to 1, 2 to 2, 2 to 3),
                listOf(0 to 1, 1 to 1, 2 to 1, 3 to 1)
            ),
            TetrominoType.O to arrayOf(
                listOf(1 to 1, 1 to 2, 2 to 1, 2 to 2),
                listOf(1 to 1, 1 to 2, 2 to 1, 2 to 2),
                listOf(1 to 1, 1 to 2, 2 to 1, 2 to 2),
                listOf(1 to 1, 1 to 2, 2 to 1, 2 to 2)
            ),
            TetrominoType.T to arrayOf(
                listOf(0 to 1, 1 to 0, 1 to 1, 1 to 2),
                listOf(0 to 1, 1 to 1, 1 to 2, 2 to 1),
                listOf(1 to 0, 1 to 1, 1 to 2, 2 to 1),
                listOf(0 to 1, 1 to 0, 1 to 1, 2 to 1)
            ),
            TetrominoType.S to arrayOf(
                listOf(0 to 1, 0 to 2, 1 to 0, 1 to 1),
                listOf(0 to 1, 1 to 1, 1 to 2, 2 to 2),
                listOf(1 to 1, 1 to 2, 2 to 0, 2 to 1),
                listOf(0 to 0, 1 to 0, 1 to 1, 2 to 1)
            ),
            TetrominoType.Z to arrayOf(
                listOf(0 to 0, 0 to 1, 1 to 1, 1 to 2),
                listOf(0 to 2, 1 to 1, 1 to 2, 2 to 1),
                listOf(1 to 0, 1 to 1, 2 to 1, 2 to 2),
                listOf(0 to 1, 1 to 0, 1 to 1, 2 to 0)
            ),
            TetrominoType.J to arrayOf(
                listOf(0 to 0, 1 to 0, 1 to 1, 1 to 2),
                listOf(0 to 1, 0 to 2, 1 to 1, 2 to 1),
                listOf(1 to 0, 1 to 1, 1 to 2, 2 to 2),
                listOf(0 to 1, 1 to 1, 2 to 0, 2 to 1)
            ),
            TetrominoType.L to arrayOf(
                listOf(0 to 2, 1 to 0, 1 to 1, 1 to 2),
                listOf(0 to 1, 1 to 1, 2 to 1, 2 to 2),
                listOf(1 to 0, 1 to 1, 1 to 2, 2 to 0),
                listOf(0 to 0, 0 to 1, 1 to 1, 2 to 1)
            )
        )
    }
}
