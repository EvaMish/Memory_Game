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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.game.R
import com.example.game.view.components.CenteredTextCard
import com.example.game.viewModels.GameViewModel


@Composable
fun StartScreen(
    onClick: () -> Unit,
    gameViewModel: GameViewModel
    ) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(25.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(15.dp)
        ) {
            Text(
                text = "Background#3",
                fontSize = 25.sp,
                modifier = Modifier.wrapContentSize(),
                color = Color.Gray
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(10.dp, 45.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.padding(25.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.coins),
                    contentDescription = "coins",
                    modifier = Modifier.size(30.dp)
                )

                Text("${gameViewModel.totalEarnedCoinsDouble.collectAsState().value}")
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
            ) {
                Text(
                    text = "Play",
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(7.dp)
                )
            }
        }
    }
}

