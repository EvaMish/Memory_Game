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
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.game.R
import com.example.game.ui.theme.daysOneFontFamily
import com.example.game.viewModels.GameViewModel

@Composable
fun FinishScreen(
    navController: NavHostController,
    gameViewModel: GameViewModel,
) {
    val earnedCoins by gameViewModel.earnedCoins.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(R.color.teal_0))
            .padding(25.dp)
    ) {
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

                Card(
                    shape = RoundedCornerShape(15.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(id = R.color.teal_100)
                    ),
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Поздравляем! Вы выиграли",
                        fontFamily = daysOneFontFamily,
                        color = Color.White,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }

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
                        Modifier.size(30.dp)
                    )
                    Text(
                        " $earnedCoins",
                        modifier = Modifier.padding(10.dp),
                        fontSize = 20.sp,
                        fontFamily = daysOneFontFamily,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(35.dp))
                Row {
                    Button(
                        onClick = { navController.navigate("start") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.teal_200)
                        ),
                        shape = RoundedCornerShape(15.dp)
                    ) {
                        Text(
                            text = "Следующий уровень",
                            fontSize = 20.sp,
                            fontFamily = daysOneFontFamily,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}


