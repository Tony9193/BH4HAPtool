package com.example.bh4haptool.feature.quickdecide.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bh4haptool.feature.quickdecide.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material3.IconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickDecideRoute(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: QuickDecideViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val haptic = LocalHapticFeedback.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.quick_decide_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.quick_decide_back)
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DecisionCard(
                title = stringResource(R.string.quick_decide_dice),
                icon = {
                    Icon(
                        imageVector = Icons.Default.Casino,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                result = uiState.diceResult?.toString(),
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    viewModel.rollDice()
                }
            )

            DecisionCard(
                title = stringResource(R.string.quick_decide_coin),
                icon = {
                    Icon(
                        imageVector = Icons.Default.AttachMoney,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                result = uiState.coinResult?.let {
                    stringResource(
                        if (it == CoinResult.HEADS) R.string.quick_decide_heads else R.string.quick_decide_tails
                    )
                },
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    viewModel.flipCoin()
                }
            )

            DecisionCard(
                title = stringResource(R.string.quick_decide_yes_no),
                icon = {
                    Icon(
                        imageVector = Icons.Default.HelpOutline,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                result = uiState.yesNoResult?.let {
                    stringResource(
                        if (it == YesNoResult.YES) R.string.quick_decide_yes else R.string.quick_decide_no
                    )
                },
                resultColor = when (uiState.yesNoResult) {
                    YesNoResult.YES -> MaterialTheme.colorScheme.primary
                    YesNoResult.NO -> MaterialTheme.colorScheme.error
                    null -> MaterialTheme.colorScheme.onSurface
                },
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    viewModel.decideYesNo()
                }
            )
        }
    }
}

@Composable
private fun DecisionCard(
    title: String,
    icon: @Composable () -> Unit,
    result: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    resultColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSurface
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            icon()
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = result ?: "?",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = resultColor,
                textAlign = TextAlign.Center
            )
        }
    }
}
