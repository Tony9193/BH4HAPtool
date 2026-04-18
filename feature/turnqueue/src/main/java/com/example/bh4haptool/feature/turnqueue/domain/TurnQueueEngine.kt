package com.example.bh4haptool.feature.turnqueue.domain

import javax.inject.Inject
import kotlin.random.Random

class TurnQueueEngine @Inject constructor() {
    fun parseParticipants(rawInput: String): List<String> {
        return rawInput
            .lines()
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .distinct()
    }

    fun buildInitialQueue(
        participants: List<String>,
        randomFirst: Boolean,
        random: Random = Random.Default
    ): List<String> {
        if (!randomFirst) {
            return participants
        }

        return participants.shuffled(random)
    }
}
