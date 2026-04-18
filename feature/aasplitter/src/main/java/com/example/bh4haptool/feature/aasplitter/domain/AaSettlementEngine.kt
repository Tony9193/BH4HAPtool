package com.example.bh4haptool.feature.aasplitter.domain

import javax.inject.Inject

enum class SettlementMode {
    AUTO_SUM,
    MANUAL_TOTAL
}

data class ParticipantInput(
    val id: String,
    val name: String,
    val paidCents: Long,
    val includedInSplit: Boolean
)

data class MemberTarget(
    val participantId: String,
    val targetCents: Long
)

data class MemberNet(
    val participantId: String,
    val netCents: Long
)

data class SettlementTransfer(
    val fromParticipantId: String,
    val toParticipantId: String,
    val amountCents: Long
)

data class SettlementResult(
    val totalCents: Long,
    val perMemberTargets: List<MemberTarget>,
    val nets: List<MemberNet>,
    val transfers: List<SettlementTransfer>,
    val deltaCents: Long
)

class AaSettlementEngine @Inject constructor() {

    fun settle(
        participants: List<ParticipantInput>,
        totalCents: Long
    ): SettlementResult {
        require(participants.isNotEmpty()) { "participants must not be empty" }
        require(totalCents >= 0L) { "totalCents must be >= 0" }
        require(participants.none { it.paidCents < 0L }) { "paidCents must be >= 0" }

        val included = participants.filter { it.includedInSplit }
        require(included.isNotEmpty()) { "at least one included participant is required" }

        val includedCount = included.size
        val base = totalCents / includedCount
        val remainder = (totalCents % includedCount).toInt()

        val targetById = buildMap {
            var includedIndex = 0
            participants.forEach { participant ->
                if (!participant.includedInSplit) {
                    put(participant.id, 0L)
                } else {
                    val plusOne = if (includedIndex < remainder) 1L else 0L
                    put(participant.id, base + plusOne)
                    includedIndex += 1
                }
            }
        }

        val perMemberTargets = participants.map { participant ->
            MemberTarget(
                participantId = participant.id,
                targetCents = targetById[participant.id] ?: 0L
            )
        }

        val nets = participants.map { participant ->
            val target = targetById[participant.id] ?: 0L
            MemberNet(
                participantId = participant.id,
                netCents = participant.paidCents - target
            )
        }

        val transfers = buildTransfers(
            participants = participants,
            nets = nets
        )

        val paidSum = participants.sumOf { it.paidCents }
        return SettlementResult(
            totalCents = totalCents,
            perMemberTargets = perMemberTargets,
            nets = nets,
            transfers = transfers,
            deltaCents = totalCents - paidSum
        )
    }

    private fun buildTransfers(
        participants: List<ParticipantInput>,
        nets: List<MemberNet>
    ): List<SettlementTransfer> {
        val netById = nets.associateBy { it.participantId }

        val creditors = participants
            .mapNotNull { participant ->
                val net = netById[participant.id]?.netCents ?: 0L
                if (net > 0L) MutableBalance(participant.id, net) else null
            }
            .toMutableList()

        val debtors = participants
            .mapNotNull { participant ->
                val net = netById[participant.id]?.netCents ?: 0L
                if (net < 0L) MutableBalance(participant.id, -net) else null
            }
            .toMutableList()

        val transfers = mutableListOf<SettlementTransfer>()
        var debtorIndex = 0
        var creditorIndex = 0

        while (debtorIndex < debtors.size && creditorIndex < creditors.size) {
            val debtor = debtors[debtorIndex]
            val creditor = creditors[creditorIndex]
            val amount = minOf(debtor.remainingCents, creditor.remainingCents)

            if (amount > 0L) {
                transfers += SettlementTransfer(
                    fromParticipantId = debtor.participantId,
                    toParticipantId = creditor.participantId,
                    amountCents = amount
                )
                debtor.remainingCents -= amount
                creditor.remainingCents -= amount
            }

            if (debtor.remainingCents == 0L) {
                debtorIndex += 1
            }
            if (creditor.remainingCents == 0L) {
                creditorIndex += 1
            }
        }

        return transfers
    }

    private data class MutableBalance(
        val participantId: String,
        var remainingCents: Long
    )
}
