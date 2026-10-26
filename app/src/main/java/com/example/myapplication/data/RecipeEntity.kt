package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// id нового рецепта, пока он не сохранён в базе
const val NEW_RECIPE_ID = 0L

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = NEW_RECIPE_ID,
    val title: String,
    val category: String,
    val cookingTime: Int,
    // ингредиенты записаны по одному в строке, например "Спагетти — 200 г"
    val ingredients: String,
    val instructions: String,
    // поле из плана, пока вместо фото показывается заглушка
    val image: String? = null,
    val isFavorite: Boolean = false
)
