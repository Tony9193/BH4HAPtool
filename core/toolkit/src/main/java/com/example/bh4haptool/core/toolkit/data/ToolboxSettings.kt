package com.example.bh4haptool.core.toolkit.data

data class ToolboxSettings(
    val simpleDrawCandidates: String = "",
    val shakeDrawCandidates: String = "",
    val shakeGyroscopeThreshold: Float = DEFAULT_GYROSCOPE_THRESHOLD,
    val shakeAccelerometerThreshold: Float = DEFAULT_ACCELEROMETER_THRESHOLD,
    val shakeCooldownMs: Int = DEFAULT_SHAKE_COOLDOWN_MS,
    val darkThemeConfig: DarkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
    val appThemeColor: AppThemeColor = AppThemeColor.DEFAULT,
    val minesweeperDifficulty: MinesweeperDifficulty = MinesweeperDifficulty.NORMAL,
    val minesweeperBoardWidth: Int = DEFAULT_MINESWEEPER_BOARD_WIDTH,
    val minesweeperBoardHeight: Int = DEFAULT_MINESWEEPER_BOARD_HEIGHT,
    val minesweeperMineCount: Int = DEFAULT_MINESWEEPER_MINE_COUNT,
    val minesweeperFirstClickSafe: Boolean = true,
    val minesweeperQuestionMarkEnabled: Boolean = true,
    val minesweeperAutoExpandEnabled: Boolean = true,
    val minesweeperSoundEnabled: Boolean = false,
    val minesweeperVibrationEnabled: Boolean = true,
    val minesweeperWins: Int = 0,
    val minesweeperLosses: Int = 0,
    val minesweeperBestTimeEasySec: Int = 0,
    val minesweeperBestTimeNormalSec: Int = 0,
    val minesweeperBestTimeHardSec: Int = 0,
    val tetrisStartLevel: Int = DEFAULT_TETRIS_START_LEVEL,
    val tetrisVibrationEnabled: Boolean = true,
    val tetrisHighScore: Int = 0,
    val tetrisBestLevel: Int = DEFAULT_TETRIS_START_LEVEL,
    val sokobanVibrationEnabled: Boolean = true,
    val sokobanCompletedLevels: Int = 0,
    val sokobanBestMovesEncoded: String = "",
    val sokobanLastLevelIndex: Int = DEFAULT_SOKOBAN_LAST_LEVEL_INDEX,
    val pomodoroWorkDurationMin: Int = DEFAULT_POMODORO_WORK_DURATION_MIN,
    val pomodoroBreakDurationMin: Int = DEFAULT_POMODORO_BREAK_DURATION_MIN,
    val pomodoroVibrationEnabled: Boolean = true,
    val pomodoroAutoSwitchEnabled: Boolean = true,
    val pomodoroDailyCompletedCount: Int = 0,
    val pomodoroLastRecordDate: String = ""
)

enum class MinesweeperDifficulty {
    EASY,
    NORMAL,
    HARD,
    CUSTOM
}

const val DEFAULT_GYROSCOPE_THRESHOLD = 2.5f
const val DEFAULT_ACCELEROMETER_THRESHOLD = 9.8f
const val DEFAULT_SHAKE_COOLDOWN_MS = 1200
const val DEFAULT_MINESWEEPER_BOARD_WIDTH = 10
const val DEFAULT_MINESWEEPER_BOARD_HEIGHT = 10
const val DEFAULT_MINESWEEPER_MINE_COUNT = 20
const val DEFAULT_TETRIS_START_LEVEL = 1
const val DEFAULT_SOKOBAN_LAST_LEVEL_INDEX = 0
const val DEFAULT_POMODORO_WORK_DURATION_MIN = 25
const val DEFAULT_POMODORO_BREAK_DURATION_MIN = 5
