package com.example.game.model.interfaceGame

interface GameFinishCallback {
    fun onGameFinish(earnedCoins: Int, totalEarnedCoins: Int)
}
