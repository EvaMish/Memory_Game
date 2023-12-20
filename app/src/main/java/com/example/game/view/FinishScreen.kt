package com.example.game.view

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun FinishScreen(navController: NavHostController, earnedCoins: Int, totalEarnedCoins: Int) {
    Text("Total Earned Coins: $totalEarnedCoins+$earnedCoins")

    Button(onClick = { navController.navigate("start") }) {

    }
}


