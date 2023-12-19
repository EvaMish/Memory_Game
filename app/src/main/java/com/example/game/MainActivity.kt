package com.example.game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.game.ui.theme.GameTheme
import com.example.game.view.MemoryGame
import com.example.game.view.StartScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "start"
                ) {
                    composable("start") {
                        StartScreen(navController = navController) {
                            navController.navigate("play")
                        }
                    }
                    composable("play") {
                        MemoryGame()
                    }

                }
            }
        }
    }
}

