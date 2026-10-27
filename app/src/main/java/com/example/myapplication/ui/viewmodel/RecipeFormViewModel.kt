package com.example.myapplication.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.NEW_RECIPE_ID
import com.example.myapplication.data.RecipeEntity
import com.example.myapplication.domain.FormErrors
import com.example.myapplication.domain.RecipeValidator
import kotlinx.coroutines.launch

class RecipeFormViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val dao = AppDatabase.getInstance(application).recipeDao()
    private val recipeId = savedStateHandle.get<Long>("id") ?: NEW_RECIPE_ID

    private var original: RecipeEntity? = null
    private var saving = false

    val isEdit = recipeId != NEW_RECIPE_ID

    var title by mutableStateOf("")
    var category by mutableStateOf("")
    var time by mutableStateOf("")
    var ingredients by mutableStateOf("")
    var instructions by mutableStateOf("")
    var showDeleteDialog by mutableStateOf(false)

    private var showErrors by mutableStateOf(false)

    val errors: FormErrors
        get() = if (showErrors) {
            RecipeValidator.validate(title, category, time, ingredients, instructions)
        } else {
            FormErrors()
        }

    init {
        if (isEdit) {
            viewModelScope.launch {
                original = dao.getById(recipeId)
                original?.let { recipe ->
                    title = recipe.title
                    category = recipe.category
                    time = recipe.cookingTime.toString()
                    ingredients = recipe.ingredients
                    instructions = recipe.instructions
                }
            }
        }
    }

    fun save(onSaved: () -> Unit, onInvalid: () -> Unit) {
        showErrors = true
        if (RecipeValidator.validate(title, category, time, ingredients, instructions).hasErrors) {
            onInvalid()
            return
        }
        if (saving || (isEdit && original == null)) return
        saving = true

        val recipe = RecipeEntity(
            id = recipeId,
            title = title.trim(),
            category = category.trim(),
            cookingTime = RecipeValidator.parseTime(time) ?: 1,
            ingredients = ingredients.trim(),
            instructions = instructions.trim(),
            image = original?.image,
            isFavorite = original?.isFavorite ?: false
        )
        viewModelScope.launch {
            if (isEdit) dao.update(recipe) else dao.insert(recipe)
            onSaved()
        }
    }

    fun delete(onDeleted: () -> Unit) {
        val recipe = original ?: return
        showDeleteDialog = false
        viewModelScope.launch {
            dao.delete(recipe)
            onDeleted()
        }
    }
}
