package com.example.bh4haptool.core.toolkit.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class ToolPaneMode(
    val isTabletMode: Boolean,
    val widthDp: Int,
    val heightDp: Int,
    val aspectRatio: Float
)

@Composable
fun rememberToolPaneMode(
    minLandscapeAspectRatio: Float = 1.45f
): ToolPaneMode {
    val configuration = LocalConfiguration.current
    val widthDp = configuration.screenWidthDp.coerceAtLeast(1)
    val heightDp = configuration.screenHeightDp.coerceAtLeast(1)
    val aspectRatio = widthDp.toFloat() / heightDp.toFloat()
    val isTabletMode = widthDp > heightDp && aspectRatio >= minLandscapeAspectRatio

    return remember(widthDp, heightDp, minLandscapeAspectRatio) {
        ToolPaneMode(
            isTabletMode = isTabletMode,
            widthDp = widthDp,
            heightDp = heightDp,
            aspectRatio = aspectRatio
        )
    }
}

@Composable
fun TabletTwoPane(
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    spacing: Dp = 12.dp,
    primaryWeight: Float = 0.7f,
    secondaryWeight: Float = 0.3f,
    primary: @Composable ColumnScope.() -> Unit,
    secondary: @Composable ColumnScope.() -> Unit
) {
    Row(
        modifier = modifier
            .padding(contentPadding)
            .fillMaxSize()
            .padding(spacing),
        horizontalArrangement = Arrangement.spacedBy(spacing)
    ) {
        Column(
            modifier = Modifier
                .weight(primaryWeight)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(spacing),
            content = primary
        )

        Column(
            modifier = Modifier
                .weight(secondaryWeight)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(spacing),
            content = secondary
        )
    }
}
