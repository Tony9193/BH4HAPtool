package com.example.bh4haptool.feature.aasplitter.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bh4haptool.core.toolkit.ui.TabletTwoPane
import com.example.bh4haptool.core.toolkit.ui.rememberToolPaneMode
import com.example.bh4haptool.feature.aasplitter.R
import com.example.bh4haptool.feature.aasplitter.domain.CalculatorExpression
import com.example.bh4haptool.feature.aasplitter.domain.MoneyCodec
import com.example.bh4haptool.feature.aasplitter.domain.SettlementMode

@Composable
fun AaSplitterRoute(
    onBack: () -> Unit,
    viewModel: AaSplitterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    AaSplitterScreen(
        uiState = uiState,
        onBack = onBack,
        onModeChange = viewModel::updateMode,
        onManualTotalChange = viewModel::updateManualTotalInput,
        onNameChange = viewModel::updateParticipantName,
        onPaidChange = viewModel::updateParticipantPaid,
        onIncludedChange = viewModel::updateParticipantIncluded,
        onAddParticipant = viewModel::addParticipant,
        onRemoveParticipant = viewModel::removeParticipant,
        onSettle = viewModel::settle,
        onClearHistory = viewModel::clearHistory
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AaSplitterScreen(
    uiState: AaSplitterUiState,
    onBack: () -> Unit,
    onModeChange: (SettlementMode) -> Unit,
    onManualTotalChange: (String) -> Unit,
    onNameChange: (Int, String) -> Unit,
    onPaidChange: (Int, String) -> Unit,
    onIncludedChange: (Int, Boolean) -> Unit,
    onAddParticipant: () -> Unit,
    onRemoveParticipant: (Int) -> Unit,
    onSettle: () -> Unit,
    onClearHistory: () -> Unit
) {
    val paneMode = rememberToolPaneMode()
    var calculatorParticipantId by rememberSaveable { mutableStateOf<Int?>(null) }
    var calculatorInitialInput by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.aa_splitter_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.aa_splitter_back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (paneMode.isTabletMode) {
            TabletTwoPane(
                contentPadding = innerPadding,
                primary = {
                    ResultPane(uiState = uiState, onClearHistory = onClearHistory)
                },
                secondary = {
                    InputPane(
                        uiState = uiState,
                        onModeChange = onModeChange,
                        onManualTotalChange = onManualTotalChange,
                        onNameChange = onNameChange,
                        onPaidChange = onPaidChange,
                        onOpenCalculator = { participantId, input ->
                            calculatorParticipantId = participantId
                            calculatorInitialInput = input
                        },
                        onIncludedChange = onIncludedChange,
                        onAddParticipant = onAddParticipant,
                        onRemoveParticipant = onRemoveParticipant,
                        onSettle = onSettle
                    )
                }
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                InputPane(
                    uiState = uiState,
                    onModeChange = onModeChange,
                    onManualTotalChange = onManualTotalChange,
                    onNameChange = onNameChange,
                    onPaidChange = onPaidChange,
                    onOpenCalculator = { participantId, input ->
                        calculatorParticipantId = participantId
                        calculatorInitialInput = input
                    },
                    onIncludedChange = onIncludedChange,
                    onAddParticipant = onAddParticipant,
                    onRemoveParticipant = onRemoveParticipant,
                    onSettle = onSettle
                )
                ResultPane(
                    uiState = uiState,
                    onClearHistory = onClearHistory
                )
            }
        }
    }

    if (calculatorParticipantId != null) {
        AmountCalculatorDialog(
            initialExpression = calculatorInitialInput,
            onDismiss = { calculatorParticipantId = null },
            onApply = { value ->
                val participantId = calculatorParticipantId
                if (participantId != null) {
                    onPaidChange(participantId, value)
                }
                calculatorParticipantId = null
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputPane(
    uiState: AaSplitterUiState,
    onModeChange: (SettlementMode) -> Unit,
    onManualTotalChange: (String) -> Unit,
    onNameChange: (Int, String) -> Unit,
    onPaidChange: (Int, String) -> Unit,
    onOpenCalculator: (Int, String) -> Unit,
    onIncludedChange: (Int, Boolean) -> Unit,
    onAddParticipant: () -> Unit,
    onRemoveParticipant: (Int) -> Unit,
    onSettle: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.aa_splitter_mode_title),
                style = MaterialTheme.typography.titleMedium
            )
            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                val modes = listOf(
                    SettlementMode.AUTO_SUM to R.string.aa_splitter_mode_auto,
                    SettlementMode.MANUAL_TOTAL to R.string.aa_splitter_mode_manual
                )
                modes.forEachIndexed { index, (mode, labelRes) ->
                    SegmentedButton(
                        selected = uiState.mode == mode,
                        onClick = { onModeChange(mode) },
                        shape = SegmentedButtonDefaults.itemShape(index = index, count = modes.size)
                    ) {
                        Text(text = stringResource(labelRes))
                    }
                }
            }
            if (uiState.mode == SettlementMode.MANUAL_TOTAL) {
                OutlinedTextField(
                    value = uiState.manualTotalInput,
                    onValueChange = onManualTotalChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = stringResource(R.string.aa_splitter_manual_total_label)) },
                    singleLine = true
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.aa_splitter_participants_title),
                    style = MaterialTheme.typography.titleMedium
                )
                OutlinedButton(onClick = onAddParticipant) {
                    Text(text = stringResource(R.string.aa_splitter_add_member))
                }
            }

            uiState.participants.forEach { participant ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = participant.name,
                            onValueChange = { onNameChange(participant.id, it) },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text(text = stringResource(R.string.aa_splitter_member_name_label)) },
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = participant.paidInput,
                            onValueChange = { onPaidChange(participant.id, it) },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text(text = stringResource(R.string.aa_splitter_paid_label)) },
                            singleLine = true
                        )
                        OutlinedButton(
                            onClick = { onOpenCalculator(participant.id, participant.paidInput) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = stringResource(R.string.aa_splitter_open_calculator))
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.aa_splitter_included_switch),
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Switch(
                                checked = participant.includedInSplit,
                                onCheckedChange = { onIncludedChange(participant.id, it) }
                            )
                        }
                        OutlinedButton(
                            onClick = { onRemoveParticipant(participant.id) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = stringResource(R.string.aa_splitter_remove_member))
                        }
                    }
                }
            }

            Button(
                onClick = onSettle,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.aa_splitter_calculate))
            }
            if (uiState.message != null) {
                Text(
                    text = uiState.message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
private fun AmountCalculatorDialog(
    initialExpression: String,
    onDismiss: () -> Unit,
    onApply: (String) -> Unit
) {
    val invalidExpressionMessage = stringResource(R.string.aa_splitter_calculator_invalid_expr)
    val negativeNotAllowedMessage = stringResource(R.string.aa_splitter_calculator_negative_not_allowed)
    var expression by rememberSaveable {
        mutableStateOf(initialExpression.trim().ifBlank { "0" })
    }
    var errorMessage by rememberSaveable { mutableStateOf<String?>(null) }

    fun appendDigit(token: String) {
        expression = if (expression == "0") token else expression + token
        errorMessage = null
    }

    fun appendOperator(operator: String) {
        if (expression.isBlank()) {
            return
        }
        val last = expression.last()
        if (!last.isDigit() && last != '.') {
            return
        }
        expression += operator
        errorMessage = null
    }

    fun appendDecimalPoint() {
        val lastOperatorIndex = expression.lastIndexOfAny(charArrayOf('+', '-', '*', '/'))
        val currentNumber = expression.substring(lastOperatorIndex + 1)
        if (currentNumber.contains('.')) {
            return
        }
        expression += if (currentNumber.isEmpty()) "0." else "."
        errorMessage = null
    }

    fun backspace() {
        expression = expression.dropLast(1).ifBlank { "0" }
        errorMessage = null
    }

    fun applyResult() {
        val result = CalculatorExpression.evaluate(expression)
        if (result == null) {
            errorMessage = invalidExpressionMessage
            return
        }
        if (result.signum() < 0) {
            errorMessage = negativeNotAllowedMessage
            return
        }
        onApply(CalculatorExpression.formatToAmountInput(result))
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.aa_splitter_calculator_title)) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = expression,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 16.dp),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
                if (errorMessage != null) {
                    Text(
                        text = errorMessage.orEmpty(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                CalculatorKeyRow(
                    keys = listOf("C", "⌫", "/", "*"),
                    onKeyClick = { key ->
                        when (key) {
                            "C" -> {
                                expression = "0"
                                errorMessage = null
                            }

                            "⌫" -> backspace()
                            "/", "*" -> appendOperator(key)
                        }
                    }
                )
                CalculatorKeyRow(
                    keys = listOf("7", "8", "9", "-"),
                    onKeyClick = { key ->
                        if (key == "-") appendOperator("-") else appendDigit(key)
                    }
                )
                CalculatorKeyRow(
                    keys = listOf("4", "5", "6", "+"),
                    onKeyClick = { key ->
                        if (key == "+") appendOperator("+") else appendDigit(key)
                    }
                )
                CalculatorKeyRow(
                    keys = listOf("1", "2", "3", "="),
                    onKeyClick = { key ->
                        if (key == "=") applyResult() else appendDigit(key)
                    }
                )
                CalculatorKeyRow(
                    keys = listOf("0", "00", ".", ""),
                    onKeyClick = { key ->
                        when (key) {
                            "0", "00" -> appendDigit(key)
                            "." -> appendDecimalPoint()
                        }
                    }
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.aa_splitter_calculator_close))
            }
        },
        confirmButton = {}
    )
}

@Composable
private fun CalculatorKeyRow(
    keys: List<String>,
    onKeyClick: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        keys.forEach { key ->
            if (key.isBlank()) {
                Spacer(modifier = Modifier.weight(1f))
            } else {
                OutlinedButton(
                    onClick = { onKeyClick(key) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = key)
                }
            }
        }
    }
}

@Composable
private fun ResultPane(
    uiState: AaSplitterUiState,
    onClearHistory: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(R.string.aa_splitter_summary_title),
                style = MaterialTheme.typography.titleMedium
            )
            Text(text = stringResource(R.string.aa_splitter_summary_total, MoneyCodec.formatCentsToYuan(uiState.totalCents)))
            Text(text = stringResource(R.string.aa_splitter_summary_paid_sum, MoneyCodec.formatCentsToYuan(uiState.paidSumCents)))
            Text(
                text = stringResource(
                    R.string.aa_splitter_summary_delta,
                    formatSigned(uiState.deltaCents)
                ),
                color = if (uiState.deltaCents == 0L) {
                    MaterialTheme.colorScheme.onSurfaceVariant
                } else {
                    MaterialTheme.colorScheme.primary
                }
            )
            if (uiState.mode == SettlementMode.MANUAL_TOTAL && uiState.deltaCents != 0L) {
                Text(
                    text = stringResource(
                        if (uiState.deltaCents > 0L) {
                            R.string.aa_splitter_delta_positive_hint
                        } else {
                            R.string.aa_splitter_delta_negative_hint
                        }
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }

    if (uiState.settlements.isNotEmpty()) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.aa_splitter_nets_title),
                    style = MaterialTheme.typography.titleMedium
                )
                uiState.settlements.forEach { settlement ->
                    Text(
                        text = stringResource(
                            R.string.aa_splitter_nets_item,
                            settlement.name,
                            MoneyCodec.formatCentsToYuan(settlement.paidCents),
                            MoneyCodec.formatCentsToYuan(settlement.targetCents),
                            formatNet(settlement.netCents)
                        )
                    )
                }
            }
        }
    }

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(R.string.aa_splitter_transfers_title),
                style = MaterialTheme.typography.titleMedium
            )
            if (uiState.transfers.isEmpty()) {
                Text(
                    text = stringResource(R.string.aa_splitter_transfers_empty),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                uiState.transfers.forEachIndexed { index, transfer ->
                    Text(
                        text = stringResource(
                            R.string.aa_splitter_transfers_item,
                            index + 1,
                            transfer.fromName,
                            transfer.toName,
                            MoneyCodec.formatCentsToYuan(transfer.amountCents)
                        )
                    )
                }
            }
        }
    }

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(R.string.aa_splitter_records_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(text = stringResource(R.string.aa_splitter_records_count, uiState.aaSettlementCount))
            Text(text = stringResource(R.string.aa_splitter_records_prepayment_count, uiState.aaPrepaymentSettlementCount))
            Text(
                text = stringResource(
                    R.string.aa_splitter_records_total_amount,
                    MoneyCodec.formatCentsToYuan(uiState.aaTotalSettledAmountCents)
                )
            )
        }
    }

    if (uiState.histories.isNotEmpty()) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.aa_splitter_history_title),
                        style = MaterialTheme.typography.titleMedium
                    )
                    OutlinedButton(onClick = onClearHistory) {
                        Text(text = stringResource(R.string.aa_splitter_history_clear))
                    }
                }
                uiState.histories.take(20).forEach { history ->
                    Text(
                        text = stringResource(
                            R.string.aa_splitter_history_headline,
                            formatHistoryTime(history.timestampMillis),
                            MoneyCodec.formatCentsToYuan(history.totalCents),
                            formatSigned(history.deltaCents)
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = history.snapshotText,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}
