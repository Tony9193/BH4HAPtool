package com.example.bh4haptool.feature.minesweeper.domain

import com.example.bh4haptool.core.toolkit.data.MinesweeperDifficulty
import java.util.ArrayDeque
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

data class CellCoord(
    val row: Int,
    val col: Int
)

enum class CellMark {
    NONE,
    FLAG,
    QUESTION
}

enum class MinesweeperStatus {
    READY,
    PLAYING,
    PAUSED,
    WON,
    LOST
}

data class MinesweeperBoardConfig(
    val width: Int,
    val height: Int,
    val mineCount: Int,
    val difficulty: MinesweeperDifficulty
) {
    fun sanitized(): MinesweeperBoardConfig {
        val safeWidth = width.coerceIn(MIN_WIDTH, MAX_WIDTH)
        val safeHeight = height.coerceIn(MIN_HEIGHT, MAX_HEIGHT)
        val maxMines = safeWidth * safeHeight - 1
        return copy(
            width = safeWidth,
            height = safeHeight,
            mineCount = mineCount.coerceIn(MIN_MINES, maxMines)
        )
    }

    companion object {
        const val MIN_WIDTH = 6
        const val MAX_WIDTH = 24
        const val MIN_HEIGHT = 6
        const val MAX_HEIGHT = 24
        const val MIN_MINES = 1

        fun preset(difficulty: MinesweeperDifficulty): MinesweeperBoardConfig {
            return when (difficulty) {
                MinesweeperDifficulty.EASY -> {
                    MinesweeperBoardConfig(width = 8, height = 8, mineCount = 10, difficulty = difficulty)
                }

                MinesweeperDifficulty.NORMAL -> {
                    MinesweeperBoardConfig(width = 10, height = 10, mineCount = 20, difficulty = difficulty)
                }

                MinesweeperDifficulty.HARD -> {
                    MinesweeperBoardConfig(width = 14, height = 14, mineCount = 40, difficulty = difficulty)
                }

                MinesweeperDifficulty.CUSTOM -> {
                    MinesweeperBoardConfig(width = 10, height = 10, mineCount = 20, difficulty = difficulty)
                }
            }
        }
    }
}

data class MinesweeperCell(
    val row: Int,
    val col: Int,
    val isMine: Boolean,
    val adjacentMines: Int,
    val isRevealed: Boolean,
    val mark: CellMark
)

data class MinesweeperSnapshot(
    val config: MinesweeperBoardConfig,
    val status: MinesweeperStatus,
    val cells: List<List<MinesweeperCell>>,
    val revealedCount: Int,
    val flaggedCount: Int,
    val explodedCell: CellCoord?,
    val statusMessage: String
)

class MinesweeperEngine(
    initialConfig: MinesweeperBoardConfig = MinesweeperBoardConfig.preset(MinesweeperDifficulty.NORMAL),
    private var firstClickSafe: Boolean = true,
    private var questionMarkEnabled: Boolean = true,
    private var autoExpandEnabled: Boolean = true
) {
    private var config: MinesweeperBoardConfig = initialConfig.sanitized()
    private lateinit var board: Array<Array<MutableCell>>

    private var status: MinesweeperStatus = MinesweeperStatus.READY
    private var statusMessage: String = STATUS_READY
    private var explodedCell: CellCoord? = null

    private var minesPlaced = false
    private var revealedCount = 0
    private var flaggedCount = 0

    init {
        newGame(config = config)
    }

    fun newGame(
        config: MinesweeperBoardConfig = this.config,
        firstClickSafe: Boolean = this.firstClickSafe,
        questionMarkEnabled: Boolean = this.questionMarkEnabled,
        autoExpandEnabled: Boolean = this.autoExpandEnabled,
        random: Random = Random.Default
    ) {
        this.config = config.sanitized()
        this.firstClickSafe = firstClickSafe
        this.questionMarkEnabled = questionMarkEnabled
        this.autoExpandEnabled = autoExpandEnabled

        board = Array(this.config.height) {
            Array(this.config.width) {
                MutableCell()
            }
        }

        minesPlaced = false
        revealedCount = 0
        flaggedCount = 0
        explodedCell = null
        status = MinesweeperStatus.READY
        statusMessage = STATUS_READY

        if (!this.firstClickSafe) {
            placeMines(random = random, safeOrigin = null)
            recalculateAdjacentMines()
            minesPlaced = true
        }
    }

    fun reveal(
        row: Int,
        col: Int,
        random: Random = Random.Default
    ) {
        if (!isInside(row, col)) {
            statusMessage = "点击位置超出棋盘"
            return
        }

        if (status == MinesweeperStatus.WON || status == MinesweeperStatus.LOST) {
            statusMessage = "本局已结束，请开始新局"
            return
        }

        if (status == MinesweeperStatus.PAUSED) {
            statusMessage = "游戏已暂停"
            return
        }

        val cell = board[row][col]
        if (cell.mark == CellMark.FLAG) {
            statusMessage = "该格已插旗"
            return
        }

        if (!minesPlaced) {
            val safeOrigin = if (firstClickSafe) CellCoord(row, col) else null
            placeMines(random = random, safeOrigin = safeOrigin)
            recalculateAdjacentMines()
            minesPlaced = true
        }

        status = MinesweeperStatus.PLAYING

        if (cell.isRevealed) {
            chordReveal(row, col)
            return
        }

        revealAt(row, col)
    }

    fun toggleMark(
        row: Int,
        col: Int
    ) {
        if (!isInside(row, col)) {
            statusMessage = "点击位置超出棋盘"
            return
        }

        if (status == MinesweeperStatus.WON || status == MinesweeperStatus.LOST) {
            statusMessage = "本局已结束，请开始新局"
            return
        }

        if (status == MinesweeperStatus.PAUSED) {
            statusMessage = "游戏已暂停"
            return
        }

        val cell = board[row][col]
        if (cell.isRevealed) {
            statusMessage = "已翻开的格子不能标记"
            return
        }

        cell.mark = when (cell.mark) {
            CellMark.NONE -> {
                flaggedCount += 1
                CellMark.FLAG
            }

            CellMark.FLAG -> {
                flaggedCount = max(0, flaggedCount - 1)
                if (questionMarkEnabled) {
                    CellMark.QUESTION
                } else {
                    CellMark.NONE
                }
            }

            CellMark.QUESTION -> {
                CellMark.NONE
            }
        }

        statusMessage = when (cell.mark) {
            CellMark.NONE -> "已清除标记"
            CellMark.FLAG -> "已插旗"
            CellMark.QUESTION -> "已标记问号"
        }
    }

    fun pause() {
        if (status == MinesweeperStatus.PLAYING) {
            status = MinesweeperStatus.PAUSED
            statusMessage = "游戏已暂停"
        }
    }

    fun resume() {
        if (status == MinesweeperStatus.PAUSED) {
            status = MinesweeperStatus.PLAYING
            statusMessage = "继续排雷"
        }
    }

    fun snapshot(): MinesweeperSnapshot {
        val cells = List(config.height) { row ->
            List(config.width) { col ->
                board[row][col].toImmutable(row = row, col = col)
            }
        }

        return MinesweeperSnapshot(
            config = config,
            status = status,
            cells = cells,
            revealedCount = revealedCount,
            flaggedCount = flaggedCount,
            explodedCell = explodedCell,
            statusMessage = statusMessage
        )
    }

    private fun chordReveal(
        row: Int,
        col: Int
    ) {
        val center = board[row][col]
        if (!center.isRevealed || center.adjacentMines <= 0) {
            statusMessage = "当前格子无法快速展开"
            return
        }

        val neighbours = neighbours(row, col)
        val flagsAround = neighbours.count { neighbour ->
            board[neighbour.row][neighbour.col].mark == CellMark.FLAG
        }

        if (flagsAround != center.adjacentMines) {
            statusMessage = "旗子数量不匹配"
            return
        }

        var changed = false
        for (neighbour in neighbours) {
            if (status == MinesweeperStatus.LOST || status == MinesweeperStatus.WON) {
                break
            }
            val target = board[neighbour.row][neighbour.col]
            if (!target.isRevealed && target.mark != CellMark.FLAG) {
                changed = true
                revealAt(neighbour.row, neighbour.col)
            }
        }

        if (status == MinesweeperStatus.PLAYING) {
            statusMessage = if (changed) {
                "已尝试快速展开"
            } else {
                "周围没有可展开格子"
            }
        }
    }

    private fun revealAt(
        row: Int,
        col: Int
    ) {
        val cell = board[row][col]
        if (cell.isRevealed || cell.mark == CellMark.FLAG) {
            return
        }

        if (cell.isMine) {
            cell.isRevealed = true
            explodedCell = CellCoord(row, col)
            revealAllMines()
            status = MinesweeperStatus.LOST
            statusMessage = "踩雷了，游戏结束"
            return
        }

        if (autoExpandEnabled && cell.adjacentMines == 0) {
            floodReveal(row, col)
        } else {
            revealSafeCell(row, col)
        }

        if (status == MinesweeperStatus.PLAYING) {
            if (hasWon()) {
                status = MinesweeperStatus.WON
                statusMessage = "排雷成功，恭喜获胜"
            } else {
                statusMessage = "继续排雷"
            }
        }
    }

    private fun floodReveal(
        row: Int,
        col: Int
    ) {
        val queue: ArrayDeque<CellCoord> = ArrayDeque()
        queue.add(CellCoord(row, col))

        while (queue.isNotEmpty()) {
            val coord = queue.removeFirst()
            if (!isInside(coord.row, coord.col)) {
                continue
            }

            val cell = board[coord.row][coord.col]
            if (cell.isRevealed || cell.mark == CellMark.FLAG || cell.isMine) {
                continue
            }

            revealSafeCell(coord.row, coord.col)
            if (cell.adjacentMines == 0) {
                neighbours(coord.row, coord.col).forEach { neighbour ->
                    val target = board[neighbour.row][neighbour.col]
                    if (!target.isRevealed && target.mark != CellMark.FLAG && !target.isMine) {
                        queue.addLast(neighbour)
                    }
                }
            }
        }
    }

    private fun revealSafeCell(
        row: Int,
        col: Int
    ) {
        val cell = board[row][col]
        if (!cell.isRevealed) {
            cell.isRevealed = true
            revealedCount += 1
        }
    }

    private fun revealAllMines() {
        for (row in 0 until config.height) {
            for (col in 0 until config.width) {
                val cell = board[row][col]
                if (cell.isMine) {
                    cell.isRevealed = true
                }
            }
        }
    }

    private fun placeMines(
        random: Random,
        safeOrigin: CellCoord?
    ) {
        val excluded = mutableSetOf<CellCoord>()
        if (safeOrigin != null) {
            excluded += safeOrigin
            excluded += neighbours(safeOrigin.row, safeOrigin.col)
        }

        val candidates = mutableListOf<CellCoord>()
        for (row in 0 until config.height) {
            for (col in 0 until config.width) {
                val coord = CellCoord(row, col)
                if (coord !in excluded) {
                    candidates += coord
                }
            }
        }

        val targetMineCount = min(config.mineCount, candidates.size)
        if (targetMineCount != config.mineCount) {
            config = config.copy(mineCount = targetMineCount)
        }

        for (index in 0 until targetMineCount) {
            val randomIndex = index + random.nextInt(candidates.size - index)
            val selected = candidates[randomIndex]
            candidates[randomIndex] = candidates[index]
            candidates[index] = selected

            board[selected.row][selected.col].isMine = true
        }
    }

    private fun recalculateAdjacentMines() {
        for (row in 0 until config.height) {
            for (col in 0 until config.width) {
                val cell = board[row][col]
                if (cell.isMine) {
                    cell.adjacentMines = 0
                    continue
                }

                val count = neighbours(row, col).count { neighbour ->
                    board[neighbour.row][neighbour.col].isMine
                }
                cell.adjacentMines = count
            }
        }
    }

    private fun hasWon(): Boolean {
        val totalSafeCells = config.width * config.height - config.mineCount
        return revealedCount >= totalSafeCells
    }

    private fun isInside(
        row: Int,
        col: Int
    ): Boolean {
        return row in 0 until config.height && col in 0 until config.width
    }

    private fun neighbours(
        row: Int,
        col: Int
    ): List<CellCoord> {
        val result = ArrayList<CellCoord>(8)
        for (dr in -1..1) {
            for (dc in -1..1) {
                if (dr == 0 && dc == 0) {
                    continue
                }
                val nr = row + dr
                val nc = col + dc
                if (isInside(nr, nc)) {
                    result += CellCoord(nr, nc)
                }
            }
        }
        return result
    }

    private data class MutableCell(
        var isMine: Boolean = false,
        var adjacentMines: Int = 0,
        var isRevealed: Boolean = false,
        var mark: CellMark = CellMark.NONE
    ) {
        fun toImmutable(
            row: Int,
            col: Int
        ): MinesweeperCell {
            return MinesweeperCell(
                row = row,
                col = col,
                isMine = isMine,
                adjacentMines = adjacentMines,
                isRevealed = isRevealed,
                mark = mark
            )
        }
    }

    companion object {
        const val STATUS_READY = "开始新局，点击任意格子开局"
    }
}
