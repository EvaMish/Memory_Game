package com.example.game.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.game.viewModels.GameViewModel

@Composable
fun FinishScreen(gameViewModel: GameViewModel = viewModel()) {
    Text("Total Earned Coins: ${gameViewModel.earnedCoins}")
}