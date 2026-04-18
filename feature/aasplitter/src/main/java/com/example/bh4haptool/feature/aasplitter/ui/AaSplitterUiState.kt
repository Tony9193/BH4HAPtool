package com.example.bh4haptool.feature.aasplitter.ui

import com.example.bh4haptool.feature.aasplitter.domain.SettlementMode

data class ParticipantDraft(
    val id: Int,
    val name: String = "",
    val paidInput: String = "",
    val includedInSplit: Boolean = true
)

data class ParticipantSettlement(
    val id: Int,
    val name: String,
    val paidCents: Long,
    val targetCents: Long,
    val netCents: Long,
    val includedInSplit: Boolean
)

data class TransferSettlement(
    val fromName: String,
    val toName: String,
    val amountCents: Long
)

data class AaSettlementHistory(
    val timestampMillis: Long,
    val totalCents: Long,
    val deltaCents: Long,
    val participantCount: Int,
    val includedCount: Int,
    val transferCount: Int,
    val snapshotText: String
)

data class AaSplitterUiState(
    val mode: SettlementMode = SettlementMode.AUTO_SUM,
    val manualTotalInput: String = "",
    val participants: List<ParticipantDraft> = defaultParticipants(),
    val totalCents: Long = 0L,
    val paidSumCents: Long = 0L,
    val deltaCents: Long = 0L,
    val settlements: List<ParticipantSettlement> = emptyList(),
    val transfers: List<TransferSettlement> = emptyList(),
    val message: String? = null,
    val histories: List<AaSettlementHistory> = emptyList(),
    val aaSettlementCount: Int = 0,
    val aaPrepaymentSettlementCount: Int = 0,
    val aaTotalSettledAmountCents: Long = 0L
)

private fun defaultParticipants(): List<ParticipantDraft> {
    return listOf(
        ParticipantDraft(id = 1, name = "成员1"),
        ParticipantDraft(id = 2, name = "成员2"),
        ParticipantDraft(id = 3, name = "成员3")
    )
}
