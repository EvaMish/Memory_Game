package com.example.game.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.game.R
import com.example.game.ui.theme.daysOneFontFamily

@Composable
fun CenteredTextCard() {
    Card(
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.teal_100)
        ),
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Игра\nнайди пару",
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
                fontFamily = daysOneFontFamily,
                color = Color.White,
                modifier = Modifier.padding(70.dp)

            )
        }
    }
}
//}

@Preview
@Composable
fun ссс() {
    CenteredTextCard()
}