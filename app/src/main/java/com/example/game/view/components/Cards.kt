package com.example.game.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.game.R
import com.example.game.model.MemoryCard


@Composable
fun MemoryCardItem(card: MemoryCard, onCardClicked: () -> Unit) {
    Card(
        modifier = Modifier
            .size(80.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable { onCardClicked() },
        colors = CardDefaults.cardColors(
            containerColor = if (card.isFaceUp) {
                Color.White
            } else colorResource(id = R.color.teal_100),
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


fun generateCards(countCards:Int): List<MemoryCard> {
    val cardContent =
        listOf(
            R.drawable.ic_cat,
            R.drawable.ic_dog,
            R.drawable.ic_tree,
            R.drawable.ic_sun,
            R.drawable.ic_android,
            R.drawable.ic_flower,
            R.drawable.ic_blueberry,
            R.drawable.ic_camera,
            R.drawable.ic_emoji,
            R.drawable.ic_cheese)

    val subList = cardContent.subList(0, countCards)
    val shuffledContent = (subList + subList).shuffled()

    return shuffledContent.mapIndexed { index, image ->
        MemoryCard(id = index, content = image)
    }
}