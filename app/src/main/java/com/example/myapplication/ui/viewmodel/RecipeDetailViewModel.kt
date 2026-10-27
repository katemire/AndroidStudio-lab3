package com.example.myapplication.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.NEW_RECIPE_ID
import com.example.myapplication.data.RecipeEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecipeDetailViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val dao = AppDatabase.getInstance(application).recipeDao()
    private val recipeId = savedStateHandle.get<Long>("id") ?: NEW_RECIPE_ID

    val recipe: StateFlow<RecipeEntity?> = dao.observe(recipeId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun toggleFavorite() {
        val current = recipe.value ?: return
        viewModelScope.launch {
            dao.setFavorite(current.id, !current.isFavorite)
        }
    }
}
