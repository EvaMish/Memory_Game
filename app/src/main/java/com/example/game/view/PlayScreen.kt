package com.example.game.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.game.R
import com.example.game.model.MemoryCard
import com.example.game.ui.theme.daysOneFontFamily
import com.example.game.view.components.MemoryCardItem
import com.example.game.view.components.generateCards
import com.example.game.viewModels.GameViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


@Composable
fun MemoryGame(
    gameViewModel: GameViewModel,
    countCards: Int,
    onClick: () -> Unit
) {
    var cards by remember { mutableStateOf(generateCards(countCards)) }
    var isGameOver by remember { mutableStateOf(false) }
    var selectedCards by remember { mutableStateOf(emptyList<MemoryCard>()) }
    var elapsedTime by remember { mutableStateOf(0L) }
    val earnedCoinsState = gameViewModel.earnedCoins.collectAsState()
    var earnedCoins by remember { mutableStateOf(earnedCoinsState.value) }
    var totalEarnedCoins by remember { mutableStateOf(gameViewModel.totalEarnedCoins.value) }

    LaunchedEffect(cards) {
        if (cards.all { it.isFaceUp }) {
            isGameOver = true
            // gameViewModel.updateGameResults(earnedCoins)
            totalEarnedCoins = gameViewModel.totalEarnedCoins.value
            gameViewModel.endGame()

        }
    }

    LaunchedEffect(isGameOver) {
        while (!isGameOver) {
            delay(1000)
            elapsedTime++
        }
        earnedCoins = gameViewModel.earnedCoins.value
    }

    LaunchedEffect(earnedCoinsState.value) {
        if (!isGameOver) {
            earnedCoins = earnedCoinsState.value
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(R.color.teal_0))
            .padding(20.dp)
    ) {

        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(20.dp, 85.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.timer),
                        contentDescription = "",
                        modifier = Modifier.size(50.dp)
                    )
                    Text(
                        " ${formatTime(elapsedTime)}",
                        fontFamily = daysOneFontFamily,
                        color = Color.White,
                        modifier = Modifier.padding(10.dp),
                        fontSize = 20.sp
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.End,
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.coins),
                        contentDescription = "coins",
                        modifier = Modifier.size(30.dp)
                    )
                    Text(
                        text = " ${gameViewModel.totalEarnedCoins.collectAsState().value}",
                        fontFamily = daysOneFontFamily,
                        color = Color.White,
                        fontSize = 20.sp,
                    )

                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
         Text(text = "$earnedCoins")
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
        if (isGameOver) {

            val openDialog = remember { mutableStateOf(false) }
            LaunchedEffect(openDialog.value) {
                delay(1000)
                openDialog.value = true
            }

            if (openDialog.value) {
                AlertDialog(
                    onDismissRequest = {
                        openDialog.value = false
                    },
                    title = {
                        Text(
                            text = "Вы выиграли $earnedCoins монет",
                            fontSize = 15.sp,
                            fontFamily = daysOneFontFamily,
                            textAlign = TextAlign.Center,
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                openDialog.value = false
                                gameViewModel.updateGameResults(earnedCoins)
                                onClick()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.teal_200)
                            )
                        ) {
                            Text(
                                "OK",
                                fontSize = 20.sp,
                                fontFamily = daysOneFontFamily,
                            )
                        }
                    }
                )
            }


        }

    }

}


private fun formatTime(elapsedTime: Long): String {
    val minutes = TimeUnit.SECONDS.toMinutes(elapsedTime)
    val seconds = elapsedTime - TimeUnit.MINUTES.toSeconds(minutes)
    return String.format("%02d:%02d", minutes, seconds)
}
