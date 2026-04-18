package com.example.bh4haptool.feature.aasplitter.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bh4haptool.core.toolkit.data.ToolboxPreferencesRepository
import com.example.bh4haptool.core.toolkit.data.ToolboxProgressCodec
import com.example.bh4haptool.feature.aasplitter.domain.AaSettlementEngine
import com.example.bh4haptool.feature.aasplitter.domain.MoneyCodec
import com.example.bh4haptool.feature.aasplitter.domain.ParticipantInput
import com.example.bh4haptool.feature.aasplitter.domain.SettlementMode
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Base64
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AaSplitterViewModel @Inject constructor(
    private val engine: AaSettlementEngine,
    private val repository: ToolboxPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AaSplitterUiState())
    val uiState: StateFlow<AaSplitterUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.settingsFlow.collect { settings ->
                _uiState.update { state ->
                    state.copy(
                        histories = ToolboxProgressCodec
                            .decodeOpaqueList(settings.aaSettlementHistoryEncoded)
                            .mapNotNull(::decodeHistory),
                        aaSettlementCount = settings.aaSettlementCount,
                        aaPrepaymentSettlementCount = settings.aaPrepaymentSettlementCount,
                        aaTotalSettledAmountCents = settings.aaTotalSettledAmountCents
                    )
                }
            }
        }
    }

    fun updateMode(mode: SettlementMode) {
        _uiState.update { it.copy(mode = mode, message = null) }
    }

    fun updateManualTotalInput(value: String) {
        _uiState.update { it.copy(manualTotalInput = value, message = null) }
    }

    fun updateParticipantName(participantId: Int, value: String) {
        _uiState.update { state ->
            state.copy(
                participants = state.participants.map { participant ->
                    if (participant.id == participantId) {
                        participant.copy(name = value)
                    } else {
                        participant
                    }
                },
                message = null
            )
        }
    }

    fun updateParticipantPaid(participantId: Int, value: String) {
        _uiState.update { state ->
            state.copy(
                participants = state.participants.map { participant ->
                    if (participant.id == participantId) {
                        participant.copy(paidInput = value)
                    } else {
                        participant
                    }
                },
                message = null
            )
        }
    }

    fun updateParticipantIncluded(participantId: Int, included: Boolean) {
        _uiState.update { state ->
            state.copy(
                participants = state.participants.map { participant ->
                    if (participant.id == participantId) {
                        participant.copy(includedInSplit = included)
                    } else {
                        participant
                    }
                },
                message = null
            )
        }
    }

    fun addParticipant() {
        _uiState.update { state ->
            if (state.participants.size >= MAX_PARTICIPANTS) {
                state.copy(message = "最多支持 $MAX_PARTICIPANTS 位成员")
            } else {
                val nextId = (state.participants.maxOfOrNull { it.id } ?: 0) + 1
                state.copy(
                    participants = state.participants + ParticipantDraft(
                        id = nextId,
                        name = "成员${state.participants.size + 1}"
                    ),
                    message = null
                )
            }
        }
    }

    fun removeParticipant(participantId: Int) {
        _uiState.update { state ->
            if (state.participants.size <= MIN_PARTICIPANTS) {
                state.copy(message = "至少保留 $MIN_PARTICIPANTS 位成员")
            } else {
                state.copy(
                    participants = state.participants.filterNot { it.id == participantId },
                    message = null
                )
            }
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            repository.clearAaSettlementHistory()
        }
    }

    fun settle() {
        val state = _uiState.value
        if (state.participants.size !in MIN_PARTICIPANTS..MAX_PARTICIPANTS) {
            _uiState.update { it.copy(message = "成员数量需要在 $MIN_PARTICIPANTS-$MAX_PARTICIPANTS 之间") }
            return
        }

        val parsedParticipants = state.participants.mapIndexed { index, participant ->
            val paidInput = participant.paidInput.trim()
            val paidCents = when {
                paidInput.isBlank() -> 0L
                else -> MoneyCodec.parseYuanToCents(paidInput) ?: run {
                    _uiState.update {
                        it.copy(message = "金额格式不正确：${participant.name.ifBlank { "成员${index + 1}" }}")
                    }
                    return
                }
            }

            ParticipantInput(
                id = participant.id.toString(),
                name = participant.name.trim().ifBlank { "成员${index + 1}" },
                paidCents = paidCents,
                includedInSplit = participant.includedInSplit
            )
        }

        if (parsedParticipants.none { it.includedInSplit }) {
            _uiState.update { it.copy(message = "至少需要 1 位成员参与均摊") }
            return
        }

        val paidSumCents = parsedParticipants.sumOf { it.paidCents }
        val totalCents = when (state.mode) {
            SettlementMode.AUTO_SUM -> paidSumCents
            SettlementMode.MANUAL_TOTAL -> {
                MoneyCodec.parseYuanToCents(state.manualTotalInput)?.also {
                    if (it < 0L) {
                        _uiState.update { current ->
                            current.copy(message = "总金额不能小于 0")
                        }
                        return
                    }
                } ?: run {
                    _uiState.update { it.copy(message = "请填写正确的总金额") }
                    return
                }
            }
        }

        val result = runCatching {
            engine.settle(
                participants = parsedParticipants,
                totalCents = totalCents
            )
        }.getOrElse { throwable ->
            _uiState.update { it.copy(message = throwable.message ?: "结算失败，请检查输入") }
            return
        }

        val targetById = result.perMemberTargets.associateBy { it.participantId }
        val netById = result.nets.associateBy { it.participantId }
        val nameById = parsedParticipants.associate { it.id to it.name }

        val settlements = parsedParticipants.map { participant ->
            ParticipantSettlement(
                id = participant.id.toInt(),
                name = participant.name,
                paidCents = participant.paidCents,
                targetCents = targetById[participant.id]?.targetCents ?: 0L,
                netCents = netById[participant.id]?.netCents ?: 0L,
                includedInSplit = participant.includedInSplit
            )
        }

        val transfers = result.transfers.map { transfer ->
            TransferSettlement(
                fromName = nameById[transfer.fromParticipantId] ?: transfer.fromParticipantId,
                toName = nameById[transfer.toParticipantId] ?: transfer.toParticipantId,
                amountCents = transfer.amountCents
            )
        }

        val timestampMillis = System.currentTimeMillis()
        val history = AaSettlementHistory(
            timestampMillis = timestampMillis,
            totalCents = result.totalCents,
            deltaCents = result.deltaCents,
            participantCount = parsedParticipants.size,
            includedCount = parsedParticipants.count { it.includedInSplit },
            transferCount = transfers.size,
            snapshotText = buildSnapshotText(
                settlements = settlements,
                transfers = transfers,
                totalCents = result.totalCents,
                deltaCents = result.deltaCents
            )
        )

        _uiState.update {
            it.copy(
                totalCents = result.totalCents,
                paidSumCents = paidSumCents,
                deltaCents = result.deltaCents,
                settlements = settlements,
                transfers = transfers,
                message = null
            )
        }

        viewModelScope.launch {
            repository.recordAaPrepaymentSettlement(
                totalCents = result.totalCents,
                snapshotEncoded = encodeHistory(history)
            )
        }
    }

    private fun buildSnapshotText(
        settlements: List<ParticipantSettlement>,
        transfers: List<TransferSettlement>,
        totalCents: Long,
        deltaCents: Long
    ): String {
        val membersText = settlements.joinToString(separator = "\n") { settlement ->
            val role = if (settlement.includedInSplit) "参与均摊" else "不均摊"
            "${settlement.name} | $role | 垫付 ${MoneyCodec.formatCentsToYuan(settlement.paidCents)} | " +
                "应承担 ${MoneyCodec.formatCentsToYuan(settlement.targetCents)} | " +
                "净额 ${formatNet(settlement.netCents)}"
        }
        val transferText = if (transfers.isEmpty()) {
            "无需转账"
        } else {
            transfers.joinToString(separator = "\n") { transfer ->
                "${transfer.fromName} -> ${transfer.toName} ${MoneyCodec.formatCentsToYuan(transfer.amountCents)}"
            }
        }

        return buildString {
            append("总额 ${MoneyCodec.formatCentsToYuan(totalCents)}，差额 ${formatSigned(deltaCents)}")
            append('\n')
            append(membersText)
            append('\n')
            append("转账：")
            append('\n')
            append(transferText)
        }
    }

    private fun encodeHistory(history: AaSettlementHistory): String {
        val snapshotEncoded = Base64.getEncoder()
            .encodeToString(history.snapshotText.toByteArray(Charsets.UTF_8))
        return listOf(
            history.timestampMillis.toString(),
            history.totalCents.toString(),
            history.deltaCents.toString(),
            history.participantCount.toString(),
            history.includedCount.toString(),
            history.transferCount.toString(),
            snapshotEncoded
        ).joinToString(separator = "|")
    }

    private fun decodeHistory(encoded: String): AaSettlementHistory? {
        val parts = encoded.split('|', limit = 7)
        if (parts.size != 7) {
            return null
        }

        val snapshot = runCatching {
            String(Base64.getDecoder().decode(parts[6]), Charsets.UTF_8)
        }.getOrNull() ?: return null

        return AaSettlementHistory(
            timestampMillis = parts[0].toLongOrNull() ?: return null,
            totalCents = parts[1].toLongOrNull() ?: return null,
            deltaCents = parts[2].toLongOrNull() ?: return null,
            participantCount = parts[3].toIntOrNull() ?: return null,
            includedCount = parts[4].toIntOrNull() ?: return null,
            transferCount = parts[5].toIntOrNull() ?: return null,
            snapshotText = snapshot
        )
    }

    companion object {
        private const val MIN_PARTICIPANTS = 2
        private const val MAX_PARTICIPANTS = 20
    }
}

internal fun formatNet(value: Long): String {
    return when {
        value > 0L -> "应收 ${MoneyCodec.formatCentsToYuan(value)}"
        value < 0L -> "应付 ${MoneyCodec.formatCentsToYuan(-value)}"
        else -> "已平账"
    }
}

internal fun formatSigned(value: Long): String {
    return if (value >= 0L) {
        "+${MoneyCodec.formatCentsToYuan(value)}"
    } else {
        "-${MoneyCodec.formatCentsToYuan(-value)}"
    }
}

internal fun formatHistoryTime(timestampMillis: Long): String {
    val local = Instant.ofEpochMilli(timestampMillis)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
    return local.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
}
