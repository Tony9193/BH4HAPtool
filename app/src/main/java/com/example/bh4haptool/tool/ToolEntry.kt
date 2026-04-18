package com.example.bh4haptool.tool

import androidx.annotation.StringRes

data class ToolEntry(
    val id: String,
    @param:StringRes val titleRes: Int,
    @param:StringRes val descriptionRes: Int,
    val route: String,
    val group: ToolGroup,
    val order: Int,
    val tags: List<String>,
    val iconRes: Int,
    val isFeatured: Boolean,
    val supportsTablet: Boolean
)
