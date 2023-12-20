package com.example.game.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.game.view.components.MemoryCardItem
import com.example.game.view.components.generateCards
import com.example.game.viewModels.GameViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

data class MemoryCard(
    val id: Int,
    val content: Int,
    var isFaceUp: Boolean = false,
    var isMatched: Boolean = false,

    ) {
    fun isContentMatch(other: MemoryCard): Boolean {
        return this.content == other.content
    }
}

var countOpen: Int = 0
var countSucsesful: Int = 0


@Composable
fun MemoryGame(
    navController: NavHostController,
    gameViewModel: GameViewModel = viewModel()
) {
    var cards by remember { mutableStateOf(generateCards()) }
    var isGameOver by remember { mutableStateOf(false) }
    var selectedCards by remember { mutableStateOf(emptyList<MemoryCard>()) }
    var elapsedTime by remember { mutableStateOf(0L) }
    var earnedCoins by remember { mutableStateOf(0) }

    LaunchedEffect(isGameOver) {
        while (!isGameOver) {
            delay(1000)
            elapsedTime++

            if (elapsedTime <= 20) {
                gameViewModel.earnedCoins = maxOf(100 - (elapsedTime - 1) * 5, 10).toInt()
            }
        }
    }

    LaunchedEffect(cards) {
        if (cards.all { it.isFaceUp }) {
            isGameOver = true

        }
    }

    Text(
        "Time: ${formatTime(elapsedTime)}",
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.background(Color.Gray),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        LazyColumn {
            items(cards.chunked(4)) { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    row.forEach { card ->
                        MemoryCardItem(card) {
                            if (!card.isFaceUp && selectedCards.size < 2) {
                                selectedCards = selectedCards + card.copy(isFaceUp = true)
                                cards = cards.toMutableList().apply {
                                    this[indexOf(card)] = card.copy(isFaceUp = !card.isFaceUp)
                                }

                                if (selectedCards.size == 2) {
                                    val (firstCard, secondCard) = selectedCards

                                    if (firstCard.isContentMatch(secondCard)) {
                                        cards = cards.map {
                                            if (it == firstCard || it == secondCard) {
                                                it.copy(isMatched = true)
                                            } else {
                                                it
                                            }
                                        }
                                    } else {
                                        GlobalScope.launch {
                                            delay(300)
                                            cards = cards.map {
                                                if (it == firstCard || it == secondCard) {
                                                    it.copy(isFaceUp = false)
                                                } else {
                                                    it
                                                }
                                            }
                                        }
                                    }

                                    selectedCards = emptyList()
                                }
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (isGameOver) {
            Text(
                "Congratulations! You've won the game!",
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                "Earned Coins: ${gameViewModel.earnedCoins}",
                style = MaterialTheme.typography.bodyLarge
            )
            LaunchedEffect(isGameOver) {
                delay(1000)
                navController.navigate("finish")
            }
        }
    }
}


private fun formatTime(elapsedTime: Long): String {
    val minutes = TimeUnit.SECONDS.toMinutes(elapsedTime)
    val seconds = elapsedTime - TimeUnit.MINUTES.toSeconds(minutes)
    return String.format("%02d:%02d", minutes, seconds)
}
