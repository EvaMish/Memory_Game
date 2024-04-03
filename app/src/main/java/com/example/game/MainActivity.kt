package com.example.game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.game.ui.theme.GameTheme
import com.example.game.view.FinishScreen
import com.example.game.view.MemoryGame
import com.example.game.view.StartScreen
import com.example.game.viewModels.GameViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val gameViewModel: GameViewModel by lazy {
                ViewModelProvider.NewInstanceFactory().create(GameViewModel::class.java)
            }

            // A surface container using the 'background' color from the theme
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = "start"
            ) {
                composable("start") {
                    StartScreen(
                        onClick = { navController.navigate("play") },
                        gameViewModel,
                    )
                }
                composable("play") {
                    MemoryGame(navController, gameViewModel)
                }
                composable("finish") {

                    FinishScreen(navController = navController, gameViewModel)

                }


            }

        }
    }
}

