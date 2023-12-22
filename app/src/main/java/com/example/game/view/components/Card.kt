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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.game.R

@Composable
fun CenteredTextCard() {
//    Column(
//        modifier = Modifier
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
        Card(
//            modifier = Modifier
//                .padding(25.dp), // Отступы внутри карточки
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.gray )
            ),
        ) {
            Box(
//                modifier = Modifier
//                    .padding(70.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Game\n\nLogo №1",
                    textAlign = TextAlign.Center,
                    fontSize =35.sp,
                    color=Color.Black,
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