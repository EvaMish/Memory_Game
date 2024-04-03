package com.example.game.model

data class MemoryCard(
    val id: Int,
    val content: Int,
    var isFaceUp: Boolean = false,
    var isMatched: Boolean = false) {
    fun isContentMatch(other: MemoryCard): Boolean {
        return this.content == other.content
    }
}