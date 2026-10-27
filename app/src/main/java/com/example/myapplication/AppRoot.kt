package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.navigation.Routes
import com.example.myapplication.ui.screens.FavoritesScreen
import com.example.myapplication.ui.screens.HomeScreen
import com.example.myapplication.ui.screens.SettingsScreen

@Composable
fun AppRoot(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    val onTabClick: (String) -> Unit = { route ->
        navController.navigate(route) {
            popUpTo(Routes.HOME) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        modifier = modifier
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                onTabClick = onTabClick,
                onRecipeClick = { },
                onAddClick = { }
            )
        }
        composable(Routes.FAVORITES) {
            FavoritesScreen(
                onTabClick = onTabClick,
                onRecipeClick = { }
            )
        }
        composable(Routes.SETTINGS) {
            SettingsScreen(onTabClick = onTabClick)
        }
    }
}
