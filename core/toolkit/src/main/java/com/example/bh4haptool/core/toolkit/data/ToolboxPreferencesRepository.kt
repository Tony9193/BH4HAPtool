package com.example.bh4haptool.core.toolkit.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.toolboxDataStore: DataStore<Preferences> by preferencesDataStore(name = "toolbox_settings")

/**
 * Central persistence gateway for all toolbox feature settings, home customizations and statistics.
 */
@Singleton
class ToolboxPreferencesRepository @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    val settingsFlow: Flow<ToolboxSettings> = context.toolboxDataStore.data.map { preferences ->
        preferences.toToolboxSettings()
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

    suspend fun updateHostModeKeepScreenOn(enabled: Boolean) {
        context.toolboxDataStore.edit { preferences ->
            preferences[HOST_MODE_KEEP_SCREEN_ON_KEY] = enabled
        }
    }

    suspend fun setToolFavorite(toolId: String, favorite: Boolean) {
        context.toolboxDataStore.edit { preferences ->
            val favorites = ToolboxProgressCodec.decodeIdList(
                preferences[FAVORITE_TOOL_IDS_KEY].orEmpty()
            ).toMutableList()

            if (favorite) {
                if (!favorites.contains(toolId)) {
                    favorites += toolId
                }
            } else {
                favorites.remove(toolId)
            }

            preferences[FAVORITE_TOOL_IDS_KEY] = ToolboxProgressCodec.encodeIdList(favorites)
        }
    }

    suspend fun setToolHidden(toolId: String, hidden: Boolean) {
        context.toolboxDataStore.edit { preferences ->
            val hiddenIds = ToolboxProgressCodec.decodeIdList(
                preferences[HIDDEN_TOOL_IDS_KEY].orEmpty()
            ).toMutableList()

            if (hidden) {
                if (!hiddenIds.contains(toolId)) {
                    hiddenIds += toolId
                }
            } else {
                hiddenIds.remove(toolId)
            }

            preferences[HIDDEN_TOOL_IDS_KEY] = ToolboxProgressCodec.encodeIdList(hiddenIds)
        }
    }

    suspend fun updateCustomToolOrder(toolIds: List<String>) {
        context.toolboxDataStore.edit { preferences ->
            preferences[CUSTOM_TOOL_ORDER_KEY] = ToolboxProgressCodec.encodeIdList(toolIds)
        }
    }

    suspend fun recordToolLaunch(
        toolId: String,
        launchedAtMillis: Long = System.currentTimeMillis()
    ) {
        context.toolboxDataStore.edit { preferences ->
            val recent = ToolboxProgressCodec.decodeIdList(
                preferences[RECENT_TOOL_IDS_KEY].orEmpty()
            )
            val updatedRecent = buildList {
                add(toolId)
                addAll(recent.filterNot { it == toolId })
            }.take(MAX_RECENT_TOOLS)

            val usageStats = ToolboxProgressCodec.decodeUsageStats(
                preferences[TOOL_USAGE_STATS_KEY].orEmpty()
            ).toMutableMap()
            usageStats[toolId] = (usageStats[toolId] ?: 0) + 1

            preferences[RECENT_TOOL_IDS_KEY] = ToolboxProgressCodec.encodeIdList(updatedRecent)
            preferences[LAST_USED_TOOL_ID_KEY] = toolId
            preferences[LAST_USED_AT_KEY] = launchedAtMillis
            preferences[TOOL_USAGE_STATS_KEY] = ToolboxProgressCodec.encodeUsageStats(usageStats)
            refreshAchievements(preferences)
        }
    }

    suspend fun clearHomeConfiguration() {
        context.toolboxDataStore.edit { preferences ->
            preferences.remove(FAVORITE_TOOL_IDS_KEY)
            preferences.remove(RECENT_TOOL_IDS_KEY)
            preferences.remove(HIDDEN_TOOL_IDS_KEY)
            preferences.remove(CUSTOM_TOOL_ORDER_KEY)
            preferences.remove(LAST_USED_TOOL_ID_KEY)
            preferences.remove(LAST_USED_AT_KEY)
        }
    }

    suspend fun clearRecords() {
        context.toolboxDataStore.edit { preferences ->
            preferences.remove(RECENT_TOOL_IDS_KEY)
            preferences.remove(TOOL_USAGE_STATS_KEY)
            preferences.remove(ACHIEVEMENT_STATES_KEY)
            preferences.remove(LAST_USED_TOOL_ID_KEY)
            preferences.remove(LAST_USED_AT_KEY)
            preferences.remove(MINESWEEPER_WINS_KEY)
            preferences.remove(MINESWEEPER_LOSSES_KEY)
            preferences.remove(MINESWEEPER_BEST_EASY_KEY)
            preferences.remove(MINESWEEPER_BEST_NORMAL_KEY)
            preferences.remove(MINESWEEPER_BEST_HARD_KEY)
            preferences.remove(TETRIS_HIGH_SCORE_KEY)
            preferences.remove(TETRIS_BEST_LEVEL_KEY)
            preferences.remove(SOKOBAN_COMPLETED_LEVELS_KEY)
            preferences.remove(SOKOBAN_BEST_MOVES_KEY)
            preferences.remove(POMODORO_DAILY_COMPLETED_COUNT_KEY)
            preferences.remove(POMODORO_LAST_RECORD_DATE_KEY)
            preferences.remove(AA_SETTLEMENT_HISTORY_KEY)
            preferences.remove(AA_SETTLEMENT_COUNT_KEY)
            preferences.remove(AA_PREPAYMENT_SETTLEMENT_COUNT_KEY)
            preferences.remove(AA_TOTAL_SETTLED_AMOUNT_CENTS_KEY)
            refreshAchievements(preferences)
        }
    }

    suspend fun resetAllPreferences() {
        context.toolboxDataStore.edit { preferences ->
            preferences.clear()
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
            refreshAchievements(preferences)
        }
    }

    suspend fun incrementMinesweeperLosses() {
        context.toolboxDataStore.edit { preferences ->
            val current = preferences[MINESWEEPER_LOSSES_KEY] ?: 0
            preferences[MINESWEEPER_LOSSES_KEY] = current + 1
            refreshAchievements(preferences)
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

            refreshAchievements(preferences)
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
            refreshAchievements(preferences)
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
            refreshAchievements(preferences)
        }
    }

    suspend fun recordAaPrepaymentSettlement(
        totalCents: Long,
        snapshotEncoded: String
    ) {
        if (snapshotEncoded.isBlank()) {
            return
        }

        context.toolboxDataStore.edit { preferences ->
            val history = ToolboxProgressCodec.decodeOpaqueList(
                preferences[AA_SETTLEMENT_HISTORY_KEY].orEmpty()
            )
            val updatedHistory = buildList {
                add(snapshotEncoded)
                addAll(history)
            }.take(MAX_AA_SETTLEMENT_HISTORY)

            val currentCount = preferences[AA_SETTLEMENT_COUNT_KEY] ?: 0
            val currentPrepaymentCount = preferences[AA_PREPAYMENT_SETTLEMENT_COUNT_KEY] ?: 0
            val currentAmount = preferences[AA_TOTAL_SETTLED_AMOUNT_CENTS_KEY] ?: 0L

            preferences[AA_SETTLEMENT_HISTORY_KEY] = ToolboxProgressCodec.encodeOpaqueList(updatedHistory)
            preferences[AA_SETTLEMENT_COUNT_KEY] = currentCount + 1
            preferences[AA_PREPAYMENT_SETTLEMENT_COUNT_KEY] = currentPrepaymentCount + 1
            preferences[AA_TOTAL_SETTLED_AMOUNT_CENTS_KEY] = currentAmount + totalCents.coerceAtLeast(0L)
            refreshAchievements(preferences)
        }
    }

    suspend fun clearAaSettlementHistory() {
        context.toolboxDataStore.edit { preferences ->
            preferences.remove(AA_SETTLEMENT_HISTORY_KEY)
        }
    }

    private fun refreshAchievements(preferences: MutablePreferences) {
        val unlocked = ToolboxAchievements.computeUnlocked(preferences.toToolboxSettings())
        preferences[ACHIEVEMENT_STATES_KEY] = ToolboxProgressCodec.encodeAchievements(unlocked)
    }

    private fun Preferences.toToolboxSettings(): ToolboxSettings {
        return ToolboxSettings(
            simpleDrawCandidates = this[SIMPLE_DRAW_CANDIDATES_KEY].orEmpty(),
            shakeDrawCandidates = this[SHAKE_DRAW_CANDIDATES_KEY].orEmpty(),
            shakeGyroscopeThreshold =
                this[SHAKE_GYROSCOPE_THRESHOLD_KEY] ?: DEFAULT_GYROSCOPE_THRESHOLD,
            shakeAccelerometerThreshold =
                this[SHAKE_ACCELEROMETER_THRESHOLD_KEY] ?: DEFAULT_ACCELEROMETER_THRESHOLD,
            shakeCooldownMs = this[SHAKE_COOLDOWN_MS_KEY] ?: DEFAULT_SHAKE_COOLDOWN_MS,
            darkThemeConfig = runCatching {
                DarkThemeConfig.valueOf(
                    this[DARK_THEME_CONFIG_KEY] ?: DarkThemeConfig.FOLLOW_SYSTEM.name
                )
            }.getOrDefault(DarkThemeConfig.FOLLOW_SYSTEM),
            appThemeColor = runCatching {
                AppThemeColor.valueOf(
                    this[APP_THEME_COLOR_KEY] ?: AppThemeColor.DEFAULT.name
                )
            }.getOrDefault(AppThemeColor.DEFAULT),
            favoriteToolIdsEncoded = this[FAVORITE_TOOL_IDS_KEY].orEmpty(),
            recentToolIdsEncoded = this[RECENT_TOOL_IDS_KEY].orEmpty(),
            hiddenToolIdsEncoded = this[HIDDEN_TOOL_IDS_KEY].orEmpty(),
            customToolOrderEncoded = this[CUSTOM_TOOL_ORDER_KEY].orEmpty(),
            toolUsageStatsEncoded = this[TOOL_USAGE_STATS_KEY].orEmpty(),
            achievementStatesEncoded = this[ACHIEVEMENT_STATES_KEY].orEmpty(),
            lastUsedToolId = this[LAST_USED_TOOL_ID_KEY].orEmpty(),
            lastUsedAtMillis = this[LAST_USED_AT_KEY] ?: 0L,
            hostModeKeepScreenOn = this[HOST_MODE_KEEP_SCREEN_ON_KEY] ?: true,
            minesweeperDifficulty = runCatching {
                MinesweeperDifficulty.valueOf(
                    this[MINESWEEPER_DIFFICULTY_KEY] ?: MinesweeperDifficulty.NORMAL.name
                )
            }.getOrDefault(MinesweeperDifficulty.NORMAL),
            minesweeperBoardWidth =
                this[MINESWEEPER_BOARD_WIDTH_KEY] ?: DEFAULT_MINESWEEPER_BOARD_WIDTH,
            minesweeperBoardHeight =
                this[MINESWEEPER_BOARD_HEIGHT_KEY] ?: DEFAULT_MINESWEEPER_BOARD_HEIGHT,
            minesweeperMineCount =
                this[MINESWEEPER_MINE_COUNT_KEY] ?: DEFAULT_MINESWEEPER_MINE_COUNT,
            minesweeperFirstClickSafe =
                this[MINESWEEPER_FIRST_CLICK_SAFE_KEY] ?: true,
            minesweeperQuestionMarkEnabled =
                this[MINESWEEPER_QUESTION_MARK_ENABLED_KEY] ?: true,
            minesweeperAutoExpandEnabled =
                this[MINESWEEPER_AUTO_EXPAND_ENABLED_KEY] ?: true,
            minesweeperSoundEnabled =
                this[MINESWEEPER_SOUND_ENABLED_KEY] ?: false,
            minesweeperVibrationEnabled =
                this[MINESWEEPER_VIBRATION_ENABLED_KEY] ?: true,
            minesweeperWins = this[MINESWEEPER_WINS_KEY] ?: 0,
            minesweeperLosses = this[MINESWEEPER_LOSSES_KEY] ?: 0,
            minesweeperBestTimeEasySec = this[MINESWEEPER_BEST_EASY_KEY] ?: 0,
            minesweeperBestTimeNormalSec = this[MINESWEEPER_BEST_NORMAL_KEY] ?: 0,
            minesweeperBestTimeHardSec = this[MINESWEEPER_BEST_HARD_KEY] ?: 0,
            tetrisStartLevel = this[TETRIS_START_LEVEL_KEY] ?: DEFAULT_TETRIS_START_LEVEL,
            tetrisVibrationEnabled = this[TETRIS_VIBRATION_ENABLED_KEY] ?: true,
            tetrisHighScore = this[TETRIS_HIGH_SCORE_KEY] ?: 0,
            tetrisBestLevel = this[TETRIS_BEST_LEVEL_KEY] ?: DEFAULT_TETRIS_START_LEVEL,
            sokobanVibrationEnabled = this[SOKOBAN_VIBRATION_ENABLED_KEY] ?: true,
            sokobanCompletedLevels = this[SOKOBAN_COMPLETED_LEVELS_KEY] ?: 0,
            sokobanBestMovesEncoded = this[SOKOBAN_BEST_MOVES_KEY].orEmpty(),
            sokobanLastLevelIndex =
                this[SOKOBAN_LAST_LEVEL_INDEX_KEY] ?: DEFAULT_SOKOBAN_LAST_LEVEL_INDEX,
            pomodoroWorkDurationMin =
                this[POMODORO_WORK_DURATION_MIN_KEY] ?: DEFAULT_POMODORO_WORK_DURATION_MIN,
            pomodoroBreakDurationMin =
                this[POMODORO_BREAK_DURATION_MIN_KEY] ?: DEFAULT_POMODORO_BREAK_DURATION_MIN,
            pomodoroVibrationEnabled =
                this[POMODORO_VIBRATION_ENABLED_KEY] ?: true,
            pomodoroAutoSwitchEnabled =
                this[POMODORO_AUTO_SWITCH_ENABLED_KEY] ?: true,
            pomodoroDailyCompletedCount =
                this[POMODORO_DAILY_COMPLETED_COUNT_KEY] ?: 0,
            pomodoroLastRecordDate =
                this[POMODORO_LAST_RECORD_DATE_KEY].orEmpty(),
            aaSettlementHistoryEncoded =
                this[AA_SETTLEMENT_HISTORY_KEY].orEmpty(),
            aaSettlementCount =
                this[AA_SETTLEMENT_COUNT_KEY] ?: 0,
            aaPrepaymentSettlementCount =
                this[AA_PREPAYMENT_SETTLEMENT_COUNT_KEY] ?: 0,
            aaTotalSettledAmountCents =
                this[AA_TOTAL_SETTLED_AMOUNT_CENTS_KEY] ?: 0L
        )
    }

    private fun decodeBestMoves(encoded: String): Map<Int, Int> {
        if (encoded.isBlank()) {
            return emptyMap()
        }

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
        return values
            .toSortedMap()
            .entries
            .joinToString(separator = ",") { (level, moves) -> "$level:$moves" }
    }

    private companion object {
        private const val MAX_RECENT_TOOLS = 6
        private const val MAX_AA_SETTLEMENT_HISTORY = 20

        val SIMPLE_DRAW_CANDIDATES_KEY = stringPreferencesKey("simple_draw_candidates")
        val SHAKE_DRAW_CANDIDATES_KEY = stringPreferencesKey("shake_draw_candidates")
        val SHAKE_GYROSCOPE_THRESHOLD_KEY = floatPreferencesKey("shake_gyroscope_threshold")
        val SHAKE_ACCELEROMETER_THRESHOLD_KEY =
            floatPreferencesKey("shake_accelerometer_threshold")
        val SHAKE_COOLDOWN_MS_KEY = intPreferencesKey("shake_cooldown_ms")
        val DARK_THEME_CONFIG_KEY = stringPreferencesKey("dark_theme_config")
        val APP_THEME_COLOR_KEY = stringPreferencesKey("app_theme_color")
        val FAVORITE_TOOL_IDS_KEY = stringPreferencesKey("favorite_tool_ids")
        val RECENT_TOOL_IDS_KEY = stringPreferencesKey("recent_tool_ids")
        val HIDDEN_TOOL_IDS_KEY = stringPreferencesKey("hidden_tool_ids")
        val CUSTOM_TOOL_ORDER_KEY = stringPreferencesKey("custom_tool_order")
        val TOOL_USAGE_STATS_KEY = stringPreferencesKey("tool_usage_stats")
        val ACHIEVEMENT_STATES_KEY = stringPreferencesKey("achievement_states")
        val LAST_USED_TOOL_ID_KEY = stringPreferencesKey("last_used_tool_id")
        val LAST_USED_AT_KEY = longPreferencesKey("last_used_at")
        val HOST_MODE_KEEP_SCREEN_ON_KEY = booleanPreferencesKey("host_mode_keep_screen_on")
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
        val POMODORO_AUTO_SWITCH_ENABLED_KEY =
            booleanPreferencesKey("pomodoro_auto_switch_enabled")
        val POMODORO_DAILY_COMPLETED_COUNT_KEY =
            intPreferencesKey("pomodoro_daily_completed_count")
        val POMODORO_LAST_RECORD_DATE_KEY = stringPreferencesKey("pomodoro_last_record_date")
        val AA_SETTLEMENT_HISTORY_KEY = stringPreferencesKey("aa_settlement_history")
        val AA_SETTLEMENT_COUNT_KEY = intPreferencesKey("aa_settlement_count")
        val AA_PREPAYMENT_SETTLEMENT_COUNT_KEY =
            intPreferencesKey("aa_prepayment_settlement_count")
        val AA_TOTAL_SETTLED_AMOUNT_CENTS_KEY =
            longPreferencesKey("aa_total_settled_amount_cents")
    }
}
