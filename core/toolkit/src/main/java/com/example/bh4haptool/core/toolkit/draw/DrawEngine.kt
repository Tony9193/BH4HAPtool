package com.example.bh4haptool.core.toolkit.draw

data class DrawOutcome(
    val winner: String,
    val poolSize: Int
)

interface DrawEngine {
    fun draw(candidates: List<String>): DrawOutcome?
}
