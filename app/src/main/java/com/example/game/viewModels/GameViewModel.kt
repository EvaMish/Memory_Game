package com.example.game.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

    private val _shouldRedraw = mutableStateOf(false)
    val shouldRedraw: State<Boolean> = _shouldRedraw

    private val gameResults = mutableListOf<Int>()

    private var gameJob: Job? = null // Сохраняем ссылку на job для возможности отмены
    private var isGameActive = true
    private var elapsedTime = 0L
    private var coins = 0


    private fun startGame() {

        gameJob = viewModelScope.launch {
            elapsedTime = 0L
            while (isGameActive) {
                delay(1000)
                elapsedTime++

                if (elapsedTime > 20) {
                    val penalty = (elapsedTime - 20) * 5
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
    }


    fun resetGame() {
        // Сбрасываем все значения, кроме общей суммы
        _earnedCoins.value = 100
        elapsedTime = 0L
        isGameActive = true
        startGame()

    }

    // В вашей вьюмодели
    fun doubleResults() {
        val currentEarnedCoins = _earnedCoins.value
        val doubledValue = if (currentEarnedCoins > 50) {
            _earnedCoins.value * 2
        } else {
            currentEarnedCoins
        }
        _earnedCoins.value = doubledValue
        _totalEarnedCoins.value += doubledValue

        println("++++++++++++${_earnedCoins.value}")
    }




    fun updateGameResults(result: Int) {
        gameResults.add(result)
        _totalEarnedCoins.value = gameResults.sum()
        println("Total Earned Coins in ViewModel: ${_totalEarnedCoins.value}")
    }
}

