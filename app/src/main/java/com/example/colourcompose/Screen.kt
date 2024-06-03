package com.example.colourcompose

sealed class Screen(val route: String) {
    object Main: Screen("MainScreen")
    object Detail: Screen("DetailScreen")
}
sealed class Screen2(val route: String) {
    object Detail: Screen("DetailScreen")
    object Game: Screen("Game")
}