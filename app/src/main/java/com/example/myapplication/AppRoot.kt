package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.data.NEW_RECIPE_ID
import com.example.myapplication.ui.navigation.Routes
import com.example.myapplication.ui.screens.FavoritesScreen
import com.example.myapplication.ui.screens.HomeScreen
import com.example.myapplication.ui.screens.RecipeFormScreen
import com.example.myapplication.ui.screens.RecipeScreen
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
                onRecipeClick = { id -> navController.navigate(Routes.recipe(id)) },
                onAddClick = { navController.navigate(Routes.form(NEW_RECIPE_ID)) }
            )
        }
        composable(Routes.FAVORITES) {
            FavoritesScreen(
                onTabClick = onTabClick,
                onRecipeClick = { id -> navController.navigate(Routes.recipe(id)) }
            )
        }
        composable(Routes.SETTINGS) {
            SettingsScreen(onTabClick = onTabClick)
        }

        composable(
            route = Routes.RECIPE,
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) {
            RecipeScreen(
                onBack = { navController.popBackStack() },
                onEdit = { id -> navController.navigate(Routes.form(id)) }
            )
        }

        composable(
            route = Routes.FORM,
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getLong("id") ?: NEW_RECIPE_ID
            RecipeFormScreen(
                onFinished = {
                    if (recipeId == NEW_RECIPE_ID) {
                        navController.popBackStack()
                    } else {
                        navController.popBackStack(Routes.RECIPE, inclusive = true)
                    }
                }
            )
        }
    }
}
