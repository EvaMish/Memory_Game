package com.example.game.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {
    private val _earnedCoins = MutableStateFlow(100)
    val earnedCoins: StateFlow<Int> = _earnedCoins

    private val _totalEarnedCoins = MutableStateFlow(0)
    val totalEarnedCoins: StateFlow<Int> = _totalEarnedCoins

    private val gameResults = mutableListOf<Int>()
    private var gameJob: Job? = null // Сохраняем ссылку на job для возможности отмены
    private var isGameActive = true
    private var coins = 0

    init {
        startGame()
    }

    private fun startGame() {

        gameJob = viewModelScope.launch {
            var elapsedTime = 0L

            while (isGameActive) {
                delay(1000)
                elapsedTime++

                if (elapsedTime > 5) {
                    val penalty = (elapsedTime - 5) * 5
                    coins = maxOf(10, 100 - penalty).toInt()
                    _earnedCoins.value = coins
                }
            }
        }
    }

    fun endGame() {
        // Завершаем текущую игру
        isGameActive = false

        gameJob?.cancel()


        // Начинаем новую игру не работает

        _earnedCoins.value = 100
        coins = 0
        isGameActive = true
        startGame()


    }

    fun updateGameResults(result: Int) {
        gameResults.add(result)
        _totalEarnedCoins.value = gameResults.sum()
        println("Total Earned Coins in ViewModel: ${_totalEarnedCoins.value}")
    }
}

