package com.example.game.view

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.game.R
import com.example.game.view.components.CenteredTextCard
import com.example.game.viewModels.GameViewModel

@Composable
fun FinishScreen(navController: NavHostController, gameViewModel: GameViewModel) {
    var isButtonClick by remember { mutableStateOf(false) }

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
                text = "Gameplay",
                fontSize = 25.sp,
                modifier = Modifier.wrapContentSize(),
                color = Color.Gray
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cup),
                    contentDescription = "won",
                    Modifier.size(150.dp)
                )

                Text(
                    text = "[Congratulations]",
                    fontSize = 25.sp,
                    modifier = Modifier.wrapContentSize(),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "[Great!You won!]",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(5.dp),
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    shape = RoundedCornerShape(15.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(id = R.color.gray)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.coins),
                            contentDescription = "coins",
                            Modifier.size(50.dp)
                        )

                        Text(
                            text = if (isButtonClick) " ${gameViewModel.totalEarnedCoinsDouble.collectAsState().value}"
                            else " ${gameViewModel.totalEarnedCoins.collectAsState().value}",
                            modifier = Modifier.padding(10.dp),
                            fontSize = 20.sp,
                        )
                    }
                }


                Spacer(modifier = Modifier.height(35.dp))

                Row {
                    Button(onClick = {
                        isButtonClick = true
                        gameViewModel.doubleResults()

                    }) {

                        Text(text = "Double Reward", fontSize = 20.sp, color = Color.White)
                    }
                    Spacer(modifier = Modifier.width(35.dp))
                    Button(
                        onClick = { navController.navigate("start") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.up),
                            contentDescription = "",

                            )
                    }
                }


            }
        }

    }
}


