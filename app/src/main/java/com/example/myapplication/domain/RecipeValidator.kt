package com.example.myapplication.domain

data class FormErrors(
    val title: Boolean = false,
    val category: Boolean = false,
    val time: Boolean = false,
    val ingredients: Boolean = false,
    val instructions: Boolean = false
) {
    val hasErrors: Boolean
        get() = title || category || time || ingredients || instructions
}

object RecipeValidator {

    fun parseTime(text: String): Int? = text.trim().toIntOrNull()?.takeIf { it > 0 }

    fun validate(
        title: String,
        category: String,
        time: String,
        ingredients: String,
        instructions: String
    ) = FormErrors(
        title = title.isBlank(),
        category = category.isBlank(),
        time = parseTime(time) == null,
        ingredients = ingredients.isBlank(),
        instructions = instructions.isBlank()
    )
}
