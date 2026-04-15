package com.example.bh4haptool.core.toolkit.draw

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Default draw engine that returns one uniformly random candidate.
 */
@Singleton
class RandomDrawEngine @Inject constructor() : DrawEngine {
    override fun draw(candidates: List<String>): DrawOutcome? {
        if (candidates.isEmpty()) {
            return null
        }

        val winner = candidates.random()
        return DrawOutcome(winner = winner, poolSize = candidates.size)
    }
}
