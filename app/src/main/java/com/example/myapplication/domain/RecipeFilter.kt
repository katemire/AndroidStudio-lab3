package com.example.myapplication.domain

import com.example.myapplication.data.RecipeEntity

// Категории-кнопки на главном экране. Рецепт относится к категории,
// если его категория начинается с одного из слов, например "Завтрак" и "Завтраки"
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

    // Оставляет рецепты, в названии которых есть строка поиска (регистр не важен),
    // и только выбранной категории, если она выбрана
    fun filter(recipes: List<RecipeEntity>, query: String, category: MealCategory?): List<RecipeEntity> =
        recipes.filter { recipe ->
            (category == null || category.matches(recipe.category)) &&
                    recipe.title.contains(query.trim(), ignoreCase = true)
        }
}
