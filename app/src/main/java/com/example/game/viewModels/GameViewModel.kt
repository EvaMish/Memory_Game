package com.example.game.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    init {
        var coins: Int = 0
        viewModelScope.launch {
            var elapsedTime = 0L

            while (true) {
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

}

