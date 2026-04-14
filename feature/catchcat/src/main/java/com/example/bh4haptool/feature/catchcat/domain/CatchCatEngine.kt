package com.example.bh4haptool.feature.catchcat.domain

import kotlin.math.min
import kotlin.random.Random

data class HexCoord(
    val i: Int,
    val j: Int
)

enum class CatchCatStatus {
    PLAYING,
    PLAYER_WIN,
    CAT_ESCAPE
}

data class CatchCatSnapshot(
    val width: Int,
    val height: Int,
    val initialWallCount: Int,
    val cat: HexCoord,
    val walls: Set<HexCoord>,
    val status: CatchCatStatus,
    val statusMessage: String,
    val moveCount: Int
)

fun interface CatchCatSolver {
    fun nextDirection(
        width: Int,
        height: Int,
        walls: Set<HexCoord>,
        cat: HexCoord
    ): Int
}

class NearestPathSolver : CatchCatSolver {
    override fun nextDirection(
        width: Int,
        height: Int,
        walls: Set<HexCoord>,
        cat: HexCoord
    ): Int {
        val distance = Array(width) { IntArray(height) { INF } }
        val queue = ArrayDeque<HexCoord>()

        fun isInside(coord: HexCoord): Boolean {
            return coord.i in 0 until width && coord.j in 0 until height
        }

        fun isEdge(coord: HexCoord): Boolean {
            return coord.i <= 0 || coord.i >= width - 1 || coord.j <= 0 || coord.j >= height - 1
        }

        fun isBlocked(coord: HexCoord): Boolean {
            return coord in walls
        }

        for (i in 0 until width) {
            for (j in 0 until height) {
                val cell = HexCoord(i, j)
                if (isEdge(cell) && !isBlocked(cell)) {
                    distance[i][j] = 0
                    queue.addLast(cell)
                }
            }
        }

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            val currentDistance = distance[current.i][current.j]
            val neighbours = CatchCatEngine.neighbours(current.i, current.j)
            for (next in neighbours) {
                if (!isInside(next) || isBlocked(next)) {
                    continue
                }
                if (distance[next.i][next.j] > currentDistance + 1) {
                    distance[next.i][next.j] = currentDistance + 1
                    queue.addLast(next)
                }
            }
        }

        val catDistance = distance[cat.i][cat.j]
        if (catDistance >= INF) {
            return -1
        }

        val routesMemo = Array(width) { LongArray(height) { -1L } }

        fun routesCount(coord: HexCoord): Long {
            if (!isInside(coord) || isBlocked(coord)) {
                return 0L
            }
            val cached = routesMemo[coord.i][coord.j]
            if (cached >= 0L) {
                return cached
            }
            if (isEdge(coord)) {
                routesMemo[coord.i][coord.j] = 1L
                return 1L
            }

            val currentDistance = distance[coord.i][coord.j]
            if (currentDistance >= INF) {
                routesMemo[coord.i][coord.j] = 0L
                return 0L
            }

            var sum = 0L
            val neighbours = CatchCatEngine.neighbours(coord.i, coord.j)
            for (next in neighbours) {
                if (!isInside(next) || isBlocked(next)) {
                    continue
                }
                if (distance[next.i][next.j] < currentDistance) {
                    sum += routesCount(next)
                }
            }
            routesMemo[coord.i][coord.j] = sum
            return sum
        }

        val neighbours = CatchCatEngine.neighbours(cat.i, cat.j)
        var bestDirection = -1
        var bestRoutes = -1L

        for (direction in neighbours.indices) {
            val next = neighbours[direction]
            if (!isInside(next) || isBlocked(next)) {
                continue
            }
            if (distance[next.i][next.j] < catDistance) {
                val routes = routesCount(next)
                if (routes > bestRoutes) {
                    bestRoutes = routes
                    bestDirection = direction
                }
            }
        }

        if (bestDirection != -1) {
            return bestDirection
        }

        for (direction in neighbours.indices) {
            val next = neighbours[direction]
            if (isInside(next) && !isBlocked(next)) {
                return direction
            }
        }

        return -1
    }

    private companion object {
        const val INF = Int.MAX_VALUE / 4
    }
}

class CatchCatEngine(
    val width: Int = DEFAULT_WIDTH,
    val height: Int = DEFAULT_HEIGHT,
    val initialWallCount: Int = DEFAULT_INITIAL_WALL_COUNT,
    private val solver: CatchCatSolver = NearestPathSolver()
) {
    private val walls = linkedSetOf<HexCoord>()
    private val history = ArrayDeque<StepRecord>()

    private var cat: HexCoord = centerCoord()
    private var status: CatchCatStatus = CatchCatStatus.PLAYING
    private var statusMessage: String = DEFAULT_STATUS_MESSAGE

    init {
        reset()
    }

    fun snapshot(): CatchCatSnapshot {
        return CatchCatSnapshot(
            width = width,
            height = height,
            initialWallCount = initialWallCount,
            cat = cat,
            walls = walls.toSet(),
            status = status,
            statusMessage = statusMessage,
            moveCount = history.size
        )
    }

    fun reset(random: Random = Random.Default) {
        cat = centerCoord()
        walls.clear()
        history.clear()
        status = CatchCatStatus.PLAYING
        statusMessage = DEFAULT_STATUS_MESSAGE
        randomInitialWalls(random)
    }

    fun undo() {
        if (history.isEmpty()) {
            statusMessage = "无路可退"
            return
        }

        if (status != CatchCatStatus.PLAYING) {
            statusMessage = "游戏已经结束，重新开局"
            reset()
            return
        }

        val step = history.removeLast()
        walls.remove(step.wall)
        cat = step.previousCat
        statusMessage = "已撤销上一步"
    }

    fun placeWall(target: HexCoord) {
        if (status != CatchCatStatus.PLAYING) {
            statusMessage = "游戏已经结束，已自动重开"
            reset()
            return
        }

        if (!isInside(target)) {
            statusMessage = "点击位置超出棋盘"
            return
        }

        if (target == cat) {
            statusMessage = "不能点击小猫当前位置"
            return
        }

        if (target in walls) {
            statusMessage = "该位置已经是墙"
            return
        }

        walls.add(target)
        if (isCatCaught()) {
            status = CatchCatStatus.PLAYER_WIN
            statusMessage = "猫已经无路可走，你赢了"
            return
        }

        history.addLast(
            StepRecord(
                previousCat = cat,
                wall = target
            )
        )

        moveCat()
    }

    private fun moveCat() {
        val direction = solver.nextDirection(
            width = width,
            height = height,
            walls = walls,
            cat = cat
        )

        if (direction !in 0..5) {
            status = CatchCatStatus.PLAYER_WIN
            statusMessage = "猫认输，你赢了"
            return
        }

        val next = neighbours(cat.i, cat.j)[direction]
        if (!isInside(next) || next in walls) {
            status = CatchCatStatus.PLAYER_WIN
            statusMessage = "猫撞墙失败，你赢了"
            return
        }

        cat = next

        if (isEdge(cat)) {
            status = CatchCatStatus.CAT_ESCAPE
            statusMessage = "猫已经跑到地图边缘了，你输了"
            return
        }

        if (isCatCaught()) {
            status = CatchCatStatus.PLAYER_WIN
            statusMessage = "猫已经无路可走，你赢了"
            return
        }

        val recentWall = history.last().wall
        statusMessage = "你点击了 (${recentWall.i}, ${recentWall.j})"
    }

    private fun randomInitialWalls(random: Random) {
        val candidates = mutableListOf<HexCoord>()
        for (j in 0 until height) {
            for (i in 0 until width) {
                val cell = HexCoord(i, j)
                if (cell != cat) {
                    candidates += cell
                }
            }
        }

        val targetCount = min(initialWallCount, candidates.size)
        for (index in 0 until targetCount) {
            val randomIndex = index + random.nextInt(candidates.size - index)
            val selected = candidates[randomIndex]
            candidates[randomIndex] = candidates[index]
            candidates[index] = selected
            walls += selected
        }
    }

    private fun centerCoord(): HexCoord {
        return HexCoord(
            i = width / 2,
            j = height / 2
        )
    }

    private fun isInside(coord: HexCoord): Boolean {
        return coord.i in 0 until width && coord.j in 0 until height
    }

    private fun isEdge(coord: HexCoord): Boolean {
        return coord.i <= 0 || coord.i >= width - 1 || coord.j <= 0 || coord.j >= height - 1
    }

    private fun isCatCaught(): Boolean {
        val neighbours = neighbours(cat.i, cat.j)
        return neighbours.none { neighbour ->
            isInside(neighbour) && neighbour !in walls
        }
    }

    private data class StepRecord(
        val previousCat: HexCoord,
        val wall: HexCoord
    )

    companion object {
        const val DEFAULT_WIDTH = 11
        const val DEFAULT_HEIGHT = 11
        const val DEFAULT_INITIAL_WALL_COUNT = 8
        const val DEFAULT_STATUS_MESSAGE = "点击小圆点，围住小猫"

        fun neighbours(i: Int, j: Int): List<HexCoord> {
            val left = HexCoord(i - 1, j)
            val right = HexCoord(i + 1, j)

            val topLeft: HexCoord
            val topRight: HexCoord
            val bottomLeft: HexCoord
            val bottomRight: HexCoord

            if ((j and 1) == 0) {
                topLeft = HexCoord(i - 1, j - 1)
                topRight = HexCoord(i, j - 1)
                bottomLeft = HexCoord(i - 1, j + 1)
                bottomRight = HexCoord(i, j + 1)
            } else {
                topLeft = HexCoord(i, j - 1)
                topRight = HexCoord(i + 1, j - 1)
                bottomLeft = HexCoord(i, j + 1)
                bottomRight = HexCoord(i + 1, j + 1)
            }

            return listOf(
                left,
                topLeft,
                topRight,
                right,
                bottomRight,
                bottomLeft
            )
        }
    }
}
