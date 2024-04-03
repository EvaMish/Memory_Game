package com.example.game.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.game.R
import com.example.game.ui.theme.daysOneFontFamily
import com.example.game.view.components.CenteredTextCard
import com.example.game.viewModels.GameViewModel


@Composable
fun StartScreen(
    onClick: () -> Unit,
    gameViewModel: GameViewModel,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(R.color.teal_0))
            .padding(25.dp)
    ) {

        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(10.dp, 45.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(25.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.coins),
                    contentDescription = "coins",
                    modifier = Modifier.size(30.dp)
                )
                Text(
                    text = "${gameViewModel.totalEarnedCoins.collectAsState().value}",
                    color = colorResource(id = R.color.white ),
                    fontFamily = daysOneFontFamily
                )
            }
        }
        Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            CenteredTextCard()
            Spacer(modifier = Modifier.height(75.dp))
            Button(
                onClick = {
                    onClick()
                    gameViewModel.resetGame()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.teal_200)
                )

            ) {
                Text(
                    text = "Играть",
                    fontSize = 15.sp,
                    fontFamily = daysOneFontFamily,
                    modifier = Modifier.padding(7.dp)
                )
            }

        }
    }

}


