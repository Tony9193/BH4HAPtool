package com.example.bh4haptool.feature.frisbeegroup.domain

import kotlin.math.ceil
import kotlin.math.floor
import kotlin.random.Random

enum class FrisbeePalette {
    RAINBOW,
    BLUE_PURPLE,
    GREEN,
    GRAY,
    SUNSET,
    OCEAN,
    NIGHT
}

data class TeamStyle(
    val name: String,
    val emoji: String,
    val argb: Long
)

object FrisbeeGroupingEngine {
    fun parseNames(input: String): List<String> {
        return input
            .lineSequence()
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .toList()
    }

    fun buildPlayers(useNames: Boolean, namesInput: String, count: Int): List<String> {
        val safeCount = count.coerceIn(2, 100)
        if (useNames) {
            val names = parseNames(namesInput)
            if (names.size >= 2) {
                return names
            }
        }
        return List(safeCount) { index -> "第 ${index + 1} 位" }
    }

    fun buildAssignments(totalPlayers: Int, totalTeams: Int, random: Random = Random.Default): List<Int> {
        val players = totalPlayers.coerceAtLeast(2)
        val teams = totalTeams.coerceIn(2, 8)
        val list = MutableList(players) { index -> index % teams }
        for (i in list.lastIndex downTo 1) {
            val j = random.nextInt(i + 1)
            val tmp = list[i]
            list[i] = list[j]
            list[j] = tmp
        }
        return list
    }

    fun teamSizeHint(totalPlayers: Int, totalTeams: Int): String {
        val p = totalPlayers.coerceAtLeast(2)
        val t = totalTeams.coerceIn(2, 8)
        val min = floor(p.toDouble() / t.toDouble()).toInt()
        val max = ceil(p.toDouble() / t.toDouble()).toInt()
        return if (min == max) "$min" else "$min-$max"
    }

    fun pointerPlayerIndex(totalPlayers: Int, heading: Float): Int {
        val players = totalPlayers.coerceAtLeast(1)
        val normalized = ((heading % 360f) + 360f) % 360f
        val segment = 360f / players
        val index = kotlin.math.floor(((360f - normalized) % 360f) / segment).toInt()
        return index.coerceIn(0, players - 1)
    }

    fun palettes(): Map<FrisbeePalette, List<TeamStyle>> {
        return mapOf(
            FrisbeePalette.RAINBOW to listOf(
                TeamStyle("橙队", "🟠", 0xFFF97316),
                TeamStyle("蓝队", "🔵", 0xFF38BDF8),
                TeamStyle("紫队", "🟣", 0xFFA78BFA),
                TeamStyle("绿队", "🟢", 0xFF4ADE80),
                TeamStyle("粉队", "🌸", 0xFFFB7185),
                TeamStyle("黄队", "🟡", 0xFFFBBF24),
                TeamStyle("翠队", "💚", 0xFF34D399),
                TeamStyle("紫红队", "🩷", 0xFFE879F9)
            ),
            FrisbeePalette.BLUE_PURPLE to listOf(
                TeamStyle("星云蓝", "🔷", 0xFF1D4ED8),
                TeamStyle("深海蓝", "🔵", 0xFF0369A1),
                TeamStyle("电光紫", "🟣", 0xFF7C3AED),
                TeamStyle("暮光紫", "💜", 0xFF9333EA),
                TeamStyle("冰晶蓝", "🧊", 0xFF0EA5E9),
                TeamStyle("靛青", "🟦", 0xFF4F46E5),
                TeamStyle("薰衣草", "🪻", 0xFFA855F7),
                TeamStyle("夜空", "🌌", 0xFF312E81)
            ),
            FrisbeePalette.GREEN to listOf(
                TeamStyle("薄荷", "🌿", 0xFF10B981),
                TeamStyle("青柠", "🍋", 0xFF84CC16),
                TeamStyle("松林", "🌲", 0xFF166534),
                TeamStyle("玉石", "🧪", 0xFF14B8A6),
                TeamStyle("草地", "🍀", 0xFF22C55E),
                TeamStyle("墨绿", "🌳", 0xFF065F46),
                TeamStyle("鼠尾草", "🌱", 0xFF4D7C0F),
                TeamStyle("青苔", "🟢", 0xFF15803D)
            ),
            FrisbeePalette.GRAY to listOf(
                TeamStyle("石墨", "⬛", 0xFF111827),
                TeamStyle("深灰", "⚫", 0xFF374151),
                TeamStyle("中灰", "🔘", 0xFF6B7280),
                TeamStyle("银灰", "⚪", 0xFF9CA3AF),
                TeamStyle("雾灰", "🌫️", 0xFFD1D5DB),
                TeamStyle("炭灰", "◼️", 0xFF1F2937),
                TeamStyle("云灰", "◻️", 0xFFE5E7EB),
                TeamStyle("夜灰", "🌑", 0xFF4B5563)
            ),
            FrisbeePalette.SUNSET to listOf(
                TeamStyle("珊瑚", "🪸", 0xFFFB7185),
                TeamStyle("晚霞", "🌇", 0xFFF97316),
                TeamStyle("琥珀", "🟠", 0xFFF59E0B),
                TeamStyle("蜜桃", "🍑", 0xFFFB923C),
                TeamStyle("玫瑰", "🌹", 0xFFE11D48),
                TeamStyle("暖金", "✨", 0xFFEAB308),
                TeamStyle("浆果", "🍓", 0xFFDB2777),
                TeamStyle("余晖", "🌅", 0xFFEA580C)
            ),
            FrisbeePalette.OCEAN to listOf(
                TeamStyle("海浪", "🌊", 0xFF0EA5E9),
                TeamStyle("礁石", "🪨", 0xFF0369A1),
                TeamStyle("湛蓝", "🔷", 0xFF0284C7),
                TeamStyle("水母", "🪼", 0xFF06B6D4),
                TeamStyle("泻湖", "🏝️", 0xFF0F766E),
                TeamStyle("海沫", "💧", 0xFF67E8F9),
                TeamStyle("深湾", "🌌", 0xFF1E40AF),
                TeamStyle("航海", "⛵", 0xFF0891B2)
            ),
            FrisbeePalette.NIGHT to listOf(
                TeamStyle("霓虹粉", "💗", 0xFFF472B6),
                TeamStyle("电光蓝", "⚡", 0xFF60A5FA),
                TeamStyle("极光绿", "🧬", 0xFF34D399),
                TeamStyle("赛博紫", "🟪", 0xFFC084FC),
                TeamStyle("夜焰橙", "🔥", 0xFFFB923C),
                TeamStyle("亮黄", "💡", 0xFFFDE047),
                TeamStyle("冷白", "🤍", 0xFFE2E8F0),
                TeamStyle("暗蓝", "🌃", 0xFF1E293B)
            )
        )
    }
}
