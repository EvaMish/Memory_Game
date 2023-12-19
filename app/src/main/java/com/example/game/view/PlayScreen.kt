package com.example.game.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.game.R

data class MemoryCard(
    val id: Int,
    val content: Int,
    var isFaceUp: Boolean = false,
    var isMatched: Boolean = false
)

var countOpen: Int = 0
val cardContent =
    listOf(
        R.drawable.ic_cat,
        R.drawable.ic_dog,
        R.drawable.ic_tree,
        R.drawable.ic_sun,
        R.drawable.ic_android,
        R.drawable.ic_flower,

        )

@Composable
fun MemoryGame() {
    var cards by remember { mutableStateOf(generateCards()) }
    var isGameOver by remember { mutableStateOf(false) }

    LaunchedEffect(cards) {
        if (cards.all { it.isFaceUp }) {
            isGameOver = true
        }
    }
  Text(text = "count open ${countOpen.toString()} ")

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
                    row.map { card ->
                        MemoryCardItem(card) {
                            if (!card.isFaceUp) {
                                cards = cards.toMutableList().apply {
                                    this[indexOf(card)] = card.copy(isFaceUp = !card.isFaceUp)
                                }
                                countOpen++
                                if(countOpen==2){

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
        }
    }
}

@Composable
fun MemoryCardItem(card: MemoryCard, onCardClicked: () -> Unit) {
    Card(
        modifier = Modifier
            .size(80.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable { onCardClicked() },
        colors = CardDefaults.cardColors(
            containerColor = if (card.isFaceUp) {
                Color.Gray
            } else Color.LightGray,
        )

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            if (card.isFaceUp) {
                Image(painter = painterResource(id = card.content), contentDescription = "")
            }
        }
    }
}


fun generateCards(): List<MemoryCard> {
    val cardContent =
        listOf(
            R.drawable.ic_cat,
            R.drawable.ic_dog,
            R.drawable.ic_tree,
            R.drawable.ic_sun,
            R.drawable.ic_android,
            R.drawable.ic_flower
        )

    val shuffledContent = (cardContent + cardContent).shuffled()

    return shuffledContent.mapIndexed { index, image ->
        MemoryCard(id = index, content = image)
    }
}

@Preview
@Composable
fun MemoryGamePreview() {
    MemoryGame()
}
