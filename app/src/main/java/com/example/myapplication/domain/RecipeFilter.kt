package com.example.myapplication.domain

import com.example.myapplication.data.RecipeEntity

enum class MealCategory(private val words: List<String>) {
    BREAKFAST(listOf("завтрак", "breakfast")),
    LUNCH(listOf("обед", "lunch")),
    DESSERT(listOf("десерт", "dessert"));

    fun matches(category: String): Boolean {
        val text = category.trim().lowercase()
        return words.any { text.startsWith(it) }
    }
}

object RecipeFilter {

    fun filter(recipes: List<RecipeEntity>, query: String, category: MealCategory?): List<RecipeEntity> =
        recipes.filter { recipe ->
            (category == null || category.matches(recipe.category)) &&
                    recipe.title.contains(query.trim(), ignoreCase = true)
        }
}
