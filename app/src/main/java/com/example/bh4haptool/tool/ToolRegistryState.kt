package com.example.bh4haptool.tool

import com.example.bh4haptool.core.toolkit.data.ToolboxProgressCodec
import com.example.bh4haptool.core.toolkit.data.ToolboxSettings

fun orderedToolEntries(settings: ToolboxSettings): List<ToolEntry> {
    val orderById = ToolboxProgressCodec.decodeIdList(settings.customToolOrderEncoded)
        .withIndex()
        .associate { it.value to it.index }

    return ToolRegistry.entries.sortedWith(
        compareBy<ToolEntry> { orderById[it.id] ?: Int.MAX_VALUE }
            .thenBy { it.order }
    )
}

fun favoriteToolIds(settings: ToolboxSettings): Set<String> {
    return ToolboxProgressCodec.decodeIdList(settings.favoriteToolIdsEncoded).toSet()
}

fun hiddenToolIds(settings: ToolboxSettings): Set<String> {
    return ToolboxProgressCodec.decodeIdList(settings.hiddenToolIdsEncoded).toSet()
}

fun recentToolIds(settings: ToolboxSettings): List<String> {
    return ToolboxProgressCodec.decodeIdList(settings.recentToolIdsEncoded)
}

fun recentToolEntries(settings: ToolboxSettings): List<ToolEntry> {
    val recentIds = recentToolIds(settings)
    return recentIds.mapNotNull { ToolRegistry.entryById(it) }
}

fun toolUsageStats(settings: ToolboxSettings): Map<String, Int> {
    return ToolboxProgressCodec.decodeUsageStats(settings.toolUsageStatsEncoded)
}
