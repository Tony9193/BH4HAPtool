package com.example.bh4haptool.core.toolkit.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.toolboxDataStore: DataStore<Preferences> by preferencesDataStore(name = "toolbox_settings")

/**
 * Central persistence gateway for all toolbox feature settings and statistics.
 */
@Singleton
class ToolboxPreferencesRepository @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    val settingsFlow: Flow<ToolboxSettings> = context.toolboxDataStore.data.map { preferences ->
        ToolboxSettings(
            simpleDrawCandidates = preferences[SIMPLE_DRAW_CANDIDATES_KEY].orEmpty(),
            shakeDrawCandidates = preferences[SHAKE_DRAW_CANDIDATES_KEY].orEmpty(),
            shakeGyroscopeThreshold =
                preferences[SHAKE_GYROSCOPE_THRESHOLD_KEY] ?: DEFAULT_GYROSCOPE_THRESHOLD,
            shakeAccelerometerThreshold =
                preferences[SHAKE_ACCELEROMETER_THRESHOLD_KEY] ?: DEFAULT_ACCELEROMETER_THRESHOLD,
            shakeCooldownMs = preferences[SHAKE_COOLDOWN_MS_KEY] ?: DEFAULT_SHAKE_COOLDOWN_MS,
            darkThemeConfig = DarkThemeConfig.valueOf(
                preferences[DARK_THEME_CONFIG_KEY] ?: DarkThemeConfig.FOLLOW_SYSTEM.name
            ),
            appThemeColor = AppThemeColor.valueOf(
                preferences[APP_THEME_COLOR_KEY] ?: AppThemeColor.DEFAULT.name
            ),
            minesweeperDifficulty = runCatching {
                MinesweeperDifficulty.valueOf(
                    preferences[MINESWEEPER_DIFFICULTY_KEY]
                        ?: MinesweeperDifficulty.NORMAL.name
                )
            }.getOrDefault(MinesweeperDifficulty.NORMAL),
            minesweeperBoardWidth =
                preferences[MINESWEEPER_BOARD_WIDTH_KEY] ?: DEFAULT_MINESWEEPER_BOARD_WIDTH,
            minesweeperBoardHeight =
                preferences[MINESWEEPER_BOARD_HEIGHT_KEY] ?: DEFAULT_MINESWEEPER_BOARD_HEIGHT,
            minesweeperMineCount =
                preferences[MINESWEEPER_MINE_COUNT_KEY] ?: DEFAULT_MINESWEEPER_MINE_COUNT,
            minesweeperFirstClickSafe =
                preferences[MINESWEEPER_FIRST_CLICK_SAFE_KEY] ?: true,
            minesweeperQuestionMarkEnabled =
                preferences[MINESWEEPER_QUESTION_MARK_ENABLED_KEY] ?: true,
            minesweeperAutoExpandEnabled =
                preferences[MINESWEEPER_AUTO_EXPAND_ENABLED_KEY] ?: true,
            minesweeperSoundEnabled =
                preferences[MINESWEEPER_SOUND_ENABLED_KEY] ?: false,
            minesweeperVibrationEnabled =
                preferences[MINESWEEPER_VIBRATION_ENABLED_KEY] ?: true,
            minesweeperWins = preferences[MINESWEEPER_WINS_KEY] ?: 0,
            minesweeperLosses = preferences[MINESWEEPER_LOSSES_KEY] ?: 0,
            minesweeperBestTimeEasySec = preferences[MINESWEEPER_BEST_EASY_KEY] ?: 0,
            minesweeperBestTimeNormalSec = preferences[MINESWEEPER_BEST_NORMAL_KEY] ?: 0,
            minesweeperBestTimeHardSec = preferences[MINESWEEPER_BEST_HARD_KEY] ?: 0,
            tetrisStartLevel =
                preferences[TETRIS_START_LEVEL_KEY] ?: DEFAULT_TETRIS_START_LEVEL,
            tetrisVibrationEnabled =
                preferences[TETRIS_VIBRATION_ENABLED_KEY] ?: true,
            tetrisHighScore = preferences[TETRIS_HIGH_SCORE_KEY] ?: 0,
            tetrisBestLevel = preferences[TETRIS_BEST_LEVEL_KEY] ?: DEFAULT_TETRIS_START_LEVEL,
            sokobanVibrationEnabled =
                preferences[SOKOBAN_VIBRATION_ENABLED_KEY] ?: true,
            sokobanCompletedLevels = preferences[SOKOBAN_COMPLETED_LEVELS_KEY] ?: 0,
            sokobanBestMovesEncoded = preferences[SOKOBAN_BEST_MOVES_KEY].orEmpty(),
            sokobanLastLevelIndex =
                preferences[SOKOBAN_LAST_LEVEL_INDEX_KEY] ?: DEFAULT_SOKOBAN_LAST_LEVEL_INDEX,
            pomodoroWorkDurationMin =
                preferences[POMODORO_WORK_DURATION_MIN_KEY] ?: DEFAULT_POMODORO_WORK_DURATION_MIN,
            pomodoroBreakDurationMin =
                preferences[POMODORO_BREAK_DURATION_MIN_KEY] ?: DEFAULT_POMODORO_BREAK_DURATION_MIN,
            pomodoroVibrationEnabled =
                preferences[POMODORO_VIBRATION_ENABLED_KEY] ?: true,
            pomodoroAutoSwitchEnabled =
                preferences[POMODORO_AUTO_SWITCH_ENABLED_KEY] ?: true,
            pomodoroDailyCompletedCount =
                preferences[POMODORO_DAILY_COMPLETED_COUNT_KEY] ?: 0,
            pomodoroLastRecordDate =
                preferences[POMODORO_LAST_RECORD_DATE_KEY].orEmpty()
        )
    }

    suspend fun updateSimpleDrawCandidates(value: String) {
        context.toolboxDataStore.edit { preferences ->
            preferences[SIMPLE_DRAW_CANDIDATES_KEY] = value
        }
    }

    suspend fun updateShakeDrawCandidates(value: String) {
        context.toolboxDataStore.edit { preferences ->
            preferences[SHAKE_DRAW_CANDIDATES_KEY] = value
        }
    }

    suspend fun updateShakeGyroscopeThreshold(value: Float) {
        context.toolboxDataStore.edit { preferences ->
            preferences[SHAKE_GYROSCOPE_THRESHOLD_KEY] = value
        }
    }

    suspend fun updateShakeAccelerometerThreshold(value: Float) {
        context.toolboxDataStore.edit { preferences ->
            preferences[SHAKE_ACCELEROMETER_THRESHOLD_KEY] = value
        }
    }

    suspend fun updateShakeCooldownMs(value: Int) {
        context.toolboxDataStore.edit { preferences ->
            preferences[SHAKE_COOLDOWN_MS_KEY] = value
        }
    }

    suspend fun updateDarkThemeConfig(config: DarkThemeConfig) {
        context.toolboxDataStore.edit { preferences ->
            preferences[DARK_THEME_CONFIG_KEY] = config.name
        }
    }

    suspend fun updateAppThemeColor(color: AppThemeColor) {
        context.toolboxDataStore.edit { preferences ->
            preferences[APP_THEME_COLOR_KEY] = color.name
        }
    }

    suspend fun updateMinesweeperConfig(
        difficulty: MinesweeperDifficulty,
        boardWidth: Int,
        boardHeight: Int,
        mineCount: Int,
        firstClickSafe: Boolean,
        questionMarkEnabled: Boolean,
        autoExpandEnabled: Boolean,
        soundEnabled: Boolean,
        vibrationEnabled: Boolean
    ) {
        context.toolboxDataStore.edit { preferences ->
            preferences[MINESWEEPER_DIFFICULTY_KEY] = difficulty.name
            preferences[MINESWEEPER_BOARD_WIDTH_KEY] = boardWidth
            preferences[MINESWEEPER_BOARD_HEIGHT_KEY] = boardHeight
            preferences[MINESWEEPER_MINE_COUNT_KEY] = mineCount
            preferences[MINESWEEPER_FIRST_CLICK_SAFE_KEY] = firstClickSafe
            preferences[MINESWEEPER_QUESTION_MARK_ENABLED_KEY] = questionMarkEnabled
            preferences[MINESWEEPER_AUTO_EXPAND_ENABLED_KEY] = autoExpandEnabled
            preferences[MINESWEEPER_SOUND_ENABLED_KEY] = soundEnabled
            preferences[MINESWEEPER_VIBRATION_ENABLED_KEY] = vibrationEnabled
        }
    }

    suspend fun incrementMinesweeperWins() {
        context.toolboxDataStore.edit { preferences ->
            val current = preferences[MINESWEEPER_WINS_KEY] ?: 0
            preferences[MINESWEEPER_WINS_KEY] = current + 1
        }
    }

    suspend fun incrementMinesweeperLosses() {
        context.toolboxDataStore.edit { preferences ->
            val current = preferences[MINESWEEPER_LOSSES_KEY] ?: 0
            preferences[MINESWEEPER_LOSSES_KEY] = current + 1
        }
    }

    suspend fun updateMinesweeperBestTime(
        difficulty: MinesweeperDifficulty,
        elapsedSeconds: Int
    ) {
        if (elapsedSeconds <= 0) {
            return
        }
        context.toolboxDataStore.edit { preferences ->
            when (difficulty) {
                MinesweeperDifficulty.EASY -> {
                    val current = preferences[MINESWEEPER_BEST_EASY_KEY] ?: 0
                    if (current == 0 || elapsedSeconds < current) {
                        preferences[MINESWEEPER_BEST_EASY_KEY] = elapsedSeconds
                    }
                }

                MinesweeperDifficulty.NORMAL,
                MinesweeperDifficulty.CUSTOM -> {
                    val current = preferences[MINESWEEPER_BEST_NORMAL_KEY] ?: 0
                    if (current == 0 || elapsedSeconds < current) {
                        preferences[MINESWEEPER_BEST_NORMAL_KEY] = elapsedSeconds
                    }
                }

                MinesweeperDifficulty.HARD -> {
                    val current = preferences[MINESWEEPER_BEST_HARD_KEY] ?: 0
                    if (current == 0 || elapsedSeconds < current) {
                        preferences[MINESWEEPER_BEST_HARD_KEY] = elapsedSeconds
                    }
                }
            }
        }
    }

    suspend fun updateTetrisSettings(
        startLevel: Int,
        vibrationEnabled: Boolean
    ) {
        context.toolboxDataStore.edit { preferences ->
            preferences[TETRIS_START_LEVEL_KEY] = startLevel.coerceIn(1, 10)
            preferences[TETRIS_VIBRATION_ENABLED_KEY] = vibrationEnabled
        }
    }

    suspend fun updateTetrisBestRecord(
        highScore: Int,
        bestLevel: Int
    ) {
        context.toolboxDataStore.edit { preferences ->
            val currentHighScore = preferences[TETRIS_HIGH_SCORE_KEY] ?: 0
            if (highScore > currentHighScore) {
                preferences[TETRIS_HIGH_SCORE_KEY] = highScore
            }

            val currentBestLevel = preferences[TETRIS_BEST_LEVEL_KEY] ?: DEFAULT_TETRIS_START_LEVEL
            if (bestLevel > currentBestLevel) {
                preferences[TETRIS_BEST_LEVEL_KEY] = bestLevel
            }
        }
    }

    suspend fun updateSokobanVibrationEnabled(enabled: Boolean) {
        context.toolboxDataStore.edit { preferences ->
            preferences[SOKOBAN_VIBRATION_ENABLED_KEY] = enabled
        }
    }

    suspend fun incrementSokobanCompletedLevels() {
        context.toolboxDataStore.edit { preferences ->
            val current = preferences[SOKOBAN_COMPLETED_LEVELS_KEY] ?: 0
            preferences[SOKOBAN_COMPLETED_LEVELS_KEY] = current + 1
        }
    }

    suspend fun updateSokobanBestMoves(
        levelIndex: Int,
        moves: Int
    ) {
        if (levelIndex < 0 || moves <= 0) {
            return
        }

        context.toolboxDataStore.edit { preferences ->
            val currentEncoded = preferences[SOKOBAN_BEST_MOVES_KEY].orEmpty()
            val bestMoves = decodeBestMoves(currentEncoded).toMutableMap()
            val currentBest = bestMoves[levelIndex]

            if (currentBest == null || moves < currentBest) {
                bestMoves[levelIndex] = moves
                preferences[SOKOBAN_BEST_MOVES_KEY] = encodeBestMoves(bestMoves)
            }
        }
    }

    suspend fun updateSokobanLastLevelIndex(levelIndex: Int) {
        context.toolboxDataStore.edit { preferences ->
            preferences[SOKOBAN_LAST_LEVEL_INDEX_KEY] = levelIndex.coerceAtLeast(0)
        }
    }

    suspend fun updatePomodoroSettings(
        workDurationMin: Int,
        breakDurationMin: Int,
        vibrationEnabled: Boolean,
        autoSwitchEnabled: Boolean
    ) {
        context.toolboxDataStore.edit { preferences ->
            preferences[POMODORO_WORK_DURATION_MIN_KEY] = workDurationMin.coerceAtLeast(1)
            preferences[POMODORO_BREAK_DURATION_MIN_KEY] = breakDurationMin.coerceAtLeast(1)
            preferences[POMODORO_VIBRATION_ENABLED_KEY] = vibrationEnabled
            preferences[POMODORO_AUTO_SWITCH_ENABLED_KEY] = autoSwitchEnabled
        }
    }

    suspend fun incrementPomodoroDailyCompletedCount(todayDate: String) {
        context.toolboxDataStore.edit { preferences ->
            val lastDate = preferences[POMODORO_LAST_RECORD_DATE_KEY].orEmpty()
            val currentCount = if (lastDate == todayDate) {
                preferences[POMODORO_DAILY_COMPLETED_COUNT_KEY] ?: 0
            } else {
                0
            }
            preferences[POMODORO_DAILY_COMPLETED_COUNT_KEY] = currentCount + 1
            preferences[POMODORO_LAST_RECORD_DATE_KEY] = todayDate
        }
    }

    private fun decodeBestMoves(encoded: String): Map<Int, Int> {
        if (encoded.isBlank()) {
            return emptyMap()
        }

        // Encoded format: level:moves,level:moves
        return encoded
            .split(',')
            .mapNotNull { token ->
                val pair = token.split(':')
                if (pair.size != 2) {
                    return@mapNotNull null
                }

                val level = pair[0].trim().toIntOrNull() ?: return@mapNotNull null
                val moves = pair[1].trim().toIntOrNull() ?: return@mapNotNull null
                level to moves
            }
            .toMap()
    }

    private fun encodeBestMoves(values: Map<Int, Int>): String {
        // Keep encoded data stable for diff-friendly storage.
        return values
            .toSortedMap()
            .entries
            .joinToString(separator = ",") { (level, moves) -> "$level:$moves" }
    }

    private companion object {
        val SIMPLE_DRAW_CANDIDATES_KEY = stringPreferencesKey("simple_draw_candidates")
        val SHAKE_DRAW_CANDIDATES_KEY = stringPreferencesKey("shake_draw_candidates")
        val SHAKE_GYROSCOPE_THRESHOLD_KEY = floatPreferencesKey("shake_gyroscope_threshold")
        val SHAKE_ACCELEROMETER_THRESHOLD_KEY =
            floatPreferencesKey("shake_accelerometer_threshold")
        val SHAKE_COOLDOWN_MS_KEY = intPreferencesKey("shake_cooldown_ms")
        val DARK_THEME_CONFIG_KEY = stringPreferencesKey("dark_theme_config")
        val APP_THEME_COLOR_KEY = stringPreferencesKey("app_theme_color")
        val MINESWEEPER_DIFFICULTY_KEY = stringPreferencesKey("minesweeper_difficulty")
        val MINESWEEPER_BOARD_WIDTH_KEY = intPreferencesKey("minesweeper_board_width")
        val MINESWEEPER_BOARD_HEIGHT_KEY = intPreferencesKey("minesweeper_board_height")
        val MINESWEEPER_MINE_COUNT_KEY = intPreferencesKey("minesweeper_mine_count")
        val MINESWEEPER_FIRST_CLICK_SAFE_KEY =
            booleanPreferencesKey("minesweeper_first_click_safe")
        val MINESWEEPER_QUESTION_MARK_ENABLED_KEY =
            booleanPreferencesKey("minesweeper_question_mark_enabled")
        val MINESWEEPER_AUTO_EXPAND_ENABLED_KEY =
            booleanPreferencesKey("minesweeper_auto_expand_enabled")
        val MINESWEEPER_SOUND_ENABLED_KEY =
            booleanPreferencesKey("minesweeper_sound_enabled")
        val MINESWEEPER_VIBRATION_ENABLED_KEY =
            booleanPreferencesKey("minesweeper_vibration_enabled")
        val MINESWEEPER_WINS_KEY = intPreferencesKey("minesweeper_wins")
        val MINESWEEPER_LOSSES_KEY = intPreferencesKey("minesweeper_losses")
        val MINESWEEPER_BEST_EASY_KEY = intPreferencesKey("minesweeper_best_easy")
        val MINESWEEPER_BEST_NORMAL_KEY = intPreferencesKey("minesweeper_best_normal")
        val MINESWEEPER_BEST_HARD_KEY = intPreferencesKey("minesweeper_best_hard")
        val TETRIS_START_LEVEL_KEY = intPreferencesKey("tetris_start_level")
        val TETRIS_VIBRATION_ENABLED_KEY = booleanPreferencesKey("tetris_vibration_enabled")
        val TETRIS_HIGH_SCORE_KEY = intPreferencesKey("tetris_high_score")
        val TETRIS_BEST_LEVEL_KEY = intPreferencesKey("tetris_best_level")
        val SOKOBAN_VIBRATION_ENABLED_KEY = booleanPreferencesKey("sokoban_vibration_enabled")
        val SOKOBAN_COMPLETED_LEVELS_KEY = intPreferencesKey("sokoban_completed_levels")
        val SOKOBAN_BEST_MOVES_KEY = stringPreferencesKey("sokoban_best_moves")
        val SOKOBAN_LAST_LEVEL_INDEX_KEY = intPreferencesKey("sokoban_last_level_index")
        val POMODORO_WORK_DURATION_MIN_KEY = intPreferencesKey("pomodoro_work_duration_min")
        val POMODORO_BREAK_DURATION_MIN_KEY = intPreferencesKey("pomodoro_break_duration_min")
        val POMODORO_VIBRATION_ENABLED_KEY = booleanPreferencesKey("pomodoro_vibration_enabled")
        val POMODORO_AUTO_SWITCH_ENABLED_KEY = booleanPreferencesKey("pomodoro_auto_switch_enabled")
        val POMODORO_DAILY_COMPLETED_COUNT_KEY = intPreferencesKey("pomodoro_daily_completed_count")
        val POMODORO_LAST_RECORD_DATE_KEY = stringPreferencesKey("pomodoro_last_record_date")
    }
}
