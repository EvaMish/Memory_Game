package com.example.game.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.game.R
import com.example.game.view.components.MemoryCardItem
import com.example.game.view.components.generateCards
import com.example.game.viewModels.GameViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import androidx.compose.material3.AlertDialog as AlertDialog

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

@Composable
fun MemoryGame(navController: NavHostController, gameViewModel: GameViewModel) {
    var cards by remember { mutableStateOf(generateCards()) }
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
            .background(Color.White)
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(15.dp)
        ) {
            Text(
                text = "Background №1",
                fontSize = 25.sp,
                modifier = Modifier.wrapContentSize(),
                color = Color.Gray
            )
        }

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
                    Card(
                        shape = RoundedCornerShape(15.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = colorResource(id = R.color.gray)
                        ),
                    ) {
                        Text(
                            " ${formatTime(elapsedTime)}",
                            modifier = Modifier.padding(10.dp),
                            fontSize = 20.sp,

                            )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.End,
                ) {


                    Image(
                        painter = painterResource(id = R.drawable.coins),
                        contentDescription = "coins",
                        modifier = Modifier.size(30.dp),

                        )

                    Text(
                       text =" ${gameViewModel.totalEarnedCoins.collectAsState().value}",
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
       // Text(text = "$earnedCoins")

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
        Spacer(modifier = Modifier.height(35.dp))

        Text(
            "Keep matching two identical objects\n" +
                    "until you open all the cards",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )

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
                            text = "Congratulations! You've won the game!\n" +
                                    "Earned Coins: $earnedCoins",
                            textAlign = TextAlign.Center,
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                openDialog.value = false
                                navController.navigate("finish") {
                                    popUpTo("play") {
                                        inclusive = true
                                    }
                                }
                            }
                        ) {
                            Text("OK", fontSize = 22.sp)
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
