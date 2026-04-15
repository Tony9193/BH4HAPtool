package com.example.bh4haptool.core.toolkit.draw

/** One draw result with winner and source pool size. */
data class DrawOutcome(
    val winner: String,
    val poolSize: Int
)

/** Strategy interface for drawing a winner from candidates. */
interface DrawEngine {
    fun draw(candidates: List<String>): DrawOutcome?
}
