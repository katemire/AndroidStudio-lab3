package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

const val NEW_RECIPE_ID = 0L

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = NEW_RECIPE_ID,
    val title: String,
    val category: String,
    val cookingTime: Int,
    val ingredients: String,
    val instructions: String,
    val image: String? = null,
    val isFavorite: Boolean = false
)
