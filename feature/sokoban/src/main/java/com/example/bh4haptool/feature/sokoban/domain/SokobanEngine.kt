package com.example.bh4haptool.feature.sokoban.domain

enum class SokobanStatus {
    PLAYING,
    WON
}

enum class MoveDirection(
    val dRow: Int,
    val dCol: Int
) {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1)
}

data class SokobanCoord(
    val row: Int,
    val col: Int
)

data class SokobanSnapshot(
    val levelIndex: Int,
    val totalLevels: Int,
    val levelName: String,
    val width: Int,
    val height: Int,
    val walls: Set<SokobanCoord>,
    val targets: Set<SokobanCoord>,
    val boxes: Set<SokobanCoord>,
    val player: SokobanCoord,
    val moveCount: Int,
    val status: SokobanStatus,
    val statusMessage: String
)

private data class HistoryState(
    val boxes: Set<SokobanCoord>,
    val player: SokobanCoord,
    val moveCount: Int,
    val status: SokobanStatus
)

private data class ParsedLevel(
    val width: Int,
    val height: Int,
    val walls: Set<SokobanCoord>,
    val targets: Set<SokobanCoord>,
    val boxes: Set<SokobanCoord>,
    val player: SokobanCoord
)

/**
 * Pure Sokoban rule engine with level loading, movement, push rules and undo.
 */
class SokobanEngine(
    private val levels: List<SokobanLevel> = SokobanLevels.DEFAULT_LEVELS
) {
    private var levelIndex: Int = 0
    private var width: Int = 0
    private var height: Int = 0

    private var walls: Set<SokobanCoord> = emptySet()
    private var targets: Set<SokobanCoord> = emptySet()
    private var initialBoxes: Set<SokobanCoord> = emptySet()
    private var initialPlayer: SokobanCoord = SokobanCoord(0, 0)

    private var boxes: MutableSet<SokobanCoord> = mutableSetOf()
    private var player: SokobanCoord = SokobanCoord(0, 0)
    private var moveCount: Int = 0
    private var status: SokobanStatus = SokobanStatus.PLAYING
    private var statusMessage: String = ""

    private val history = ArrayDeque<HistoryState>()

    init {
        require(levels.isNotEmpty()) { "Sokoban levels must not be empty" }
        loadLevel(0)
    }

    fun loadLevel(index: Int): Boolean {
        if (index !in levels.indices) {
            return false
        }

        levelIndex = index
        val parsed = parseLevel(levels[index])

        width = parsed.width
        height = parsed.height
        walls = parsed.walls
        targets = parsed.targets
        initialBoxes = parsed.boxes
        initialPlayer = parsed.player

        boxes = initialBoxes.toMutableSet()
        player = initialPlayer
        moveCount = 0
        status = if (isWon()) SokobanStatus.WON else SokobanStatus.PLAYING
        statusMessage = if (status == SokobanStatus.WON) {
            "本关已通关"
        } else {
            "第 ${index + 1} 关开始"
        }

        history.clear()
        return true
    }

    fun nextLevel(): Boolean {
        if (levelIndex >= levels.lastIndex) {
            return false
        }
        return loadLevel(levelIndex + 1)
    }

    fun previousLevel(): Boolean {
        if (levelIndex <= 0) {
            return false
        }
        return loadLevel(levelIndex - 1)
    }

    fun reset() {
        boxes = initialBoxes.toMutableSet()
        player = initialPlayer
        moveCount = 0
        status = if (isWon()) SokobanStatus.WON else SokobanStatus.PLAYING
        statusMessage = "已重开本关"
        history.clear()
    }

    fun move(direction: MoveDirection): Boolean {
        if (status == SokobanStatus.WON) {
            return false
        }

        val next = SokobanCoord(
            row = player.row + direction.dRow,
            col = player.col + direction.dCol
        )

        if (next in walls) {
            statusMessage = "前方是墙"
            return false
        }

        saveHistory()

        if (next in boxes) {
            val pushTarget = SokobanCoord(
                row = next.row + direction.dRow,
                col = next.col + direction.dCol
            )

            if (pushTarget in walls || pushTarget in boxes) {
                history.removeLast()
                statusMessage = "箱子无法推动"
                return false
            }

            boxes.remove(next)
            boxes.add(pushTarget)
        }

        player = next
        moveCount++

        status = if (isWon()) {
            statusMessage = "通关成功"
            SokobanStatus.WON
        } else {
            statusMessage = "继续推进"
            SokobanStatus.PLAYING
        }

        return true
    }

    fun undo(): Boolean {
        if (history.isEmpty()) {
            return false
        }

        val previous = history.removeLast()
        boxes = previous.boxes.toMutableSet()
        player = previous.player
        moveCount = previous.moveCount
        status = previous.status
        statusMessage = "已撤销一步"
        return true
    }

    fun snapshot(): SokobanSnapshot {
        return SokobanSnapshot(
            levelIndex = levelIndex,
            totalLevels = levels.size,
            levelName = levels[levelIndex].name,
            width = width,
            height = height,
            walls = walls,
            targets = targets,
            boxes = boxes.toSet(),
            player = player,
            moveCount = moveCount,
            status = status,
            statusMessage = statusMessage
        )
    }

    private fun saveHistory() {
        if (history.size >= MAX_HISTORY_SIZE) {
            history.removeFirst()
        }
        history.addLast(
            HistoryState(
                boxes = boxes.toSet(),
                player = player,
                moveCount = moveCount,
                status = status
            )
        )
    }

    private fun isWon(): Boolean {
        return targets.isNotEmpty() && targets.all { target -> target in boxes }
    }

    private fun parseLevel(level: SokobanLevel): ParsedLevel {
        val parsedHeight = level.rows.size
        val parsedWidth = level.rows.maxOfOrNull { row -> row.length } ?: 0

        val parsedWalls = mutableSetOf<SokobanCoord>()
        val parsedTargets = mutableSetOf<SokobanCoord>()
        val parsedBoxes = mutableSetOf<SokobanCoord>()
        var parsedPlayer: SokobanCoord? = null

        for (row in 0 until parsedHeight) {
            for (col in 0 until parsedWidth) {
                val symbol = level.rows[row].getOrElse(col) { ' ' }
                val coord = SokobanCoord(row = row, col = col)

                // Sokoban symbols: # wall, . target, $ box, * box on target, @ player, + player on target.
                when (symbol) {
                    '#' -> parsedWalls.add(coord)
                    '.' -> parsedTargets.add(coord)
                    '$' -> parsedBoxes.add(coord)
                    '*' -> {
                        parsedTargets.add(coord)
                        parsedBoxes.add(coord)
                    }

                    '@' -> parsedPlayer = coord
                    '+' -> {
                        parsedTargets.add(coord)
                        parsedPlayer = coord
                    }
                }
            }
        }

        return ParsedLevel(
            width = parsedWidth,
            height = parsedHeight,
            walls = parsedWalls,
            targets = parsedTargets,
            boxes = parsedBoxes,
            player = parsedPlayer ?: SokobanCoord(1, 1)
        )
    }

    private companion object {
        const val MAX_HISTORY_SIZE = 300
    }
}
