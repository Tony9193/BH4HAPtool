package com.example.bh4haptool.feature.aasplitter.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AaSettlementEngineTest {

    private val engine = AaSettlementEngine()

    @Test
    fun settle_autoSum_balancesReceivableAndPayable() {
        val participants = listOf(
            ParticipantInput("1", "A", 2000, true),
            ParticipantInput("2", "B", 1000, true),
            ParticipantInput("3", "C", 0, true)
        )

        val result = engine.settle(participants, totalCents = participants.sumOf { it.paidCents })
        val netSum = result.nets.sumOf { it.netCents }
        val targets = result.perMemberTargets.sumOf { it.targetCents }
        val transferSum = result.transfers.sumOf { it.amountCents }
        val receivable = result.nets.filter { it.netCents > 0 }.sumOf { it.netCents }
        val payable = result.nets.filter { it.netCents < 0 }.sumOf { -it.netCents }

        assertEquals(0L, netSum)
        assertEquals(result.totalCents, targets)
        assertEquals(receivable, payable)
        assertEquals(receivable, transferSum)
    }

    @Test
    fun settle_excludedParticipantCanStillReceiveRefund() {
        val participants = listOf(
            ParticipantInput("1", "A", 0, true),
            ParticipantInput("2", "B", 0, true),
            ParticipantInput("3", "C", 3000, false)
        )

        val result = engine.settle(participants, totalCents = 3000)
        val targetOfExcluded = result.perMemberTargets.first { it.participantId == "3" }.targetCents
        val netOfExcluded = result.nets.first { it.participantId == "3" }.netCents

        assertEquals(0L, targetOfExcluded)
        assertEquals(3000L, netOfExcluded)
        assertTrue(result.transfers.any { it.toParticipantId == "3" })
    }

    @Test(expected = IllegalArgumentException::class)
    fun settle_throwsWhenNoIncludedParticipant() {
        val participants = listOf(
            ParticipantInput("1", "A", 1000, false),
            ParticipantInput("2", "B", 1000, false)
        )
        engine.settle(participants, totalCents = 2000)
    }

    @Test
    fun settle_remainderDistributedByOrder() {
        val participants = listOf(
            ParticipantInput("1", "A", 1, true),
            ParticipantInput("2", "B", 0, true),
            ParticipantInput("3", "C", 0, true)
        )

        val result = engine.settle(participants, totalCents = 1)
        val targets = result.perMemberTargets.associateBy { it.participantId }

        assertEquals(1L, targets["1"]?.targetCents)
        assertEquals(0L, targets["2"]?.targetCents)
        assertEquals(0L, targets["3"]?.targetCents)
    }

    @Test
    fun settle_isDeterministicForSameInput() {
        val participants = listOf(
            ParticipantInput("1", "A", 1000, true),
            ParticipantInput("2", "B", 200, true),
            ParticipantInput("3", "C", 300, true),
            ParticipantInput("4", "D", 0, true)
        )

        val first = engine.settle(participants, totalCents = 1500)
        val second = engine.settle(participants, totalCents = 1500)

        assertEquals(first, second)
    }

    @Test
    fun moneyCodec_roundTrips() {
        val cents = MoneyCodec.parseYuanToCents("10.05")
        assertEquals(1005L, cents)
        assertEquals("10.05", MoneyCodec.formatCentsToYuan(1005))
    }
}
