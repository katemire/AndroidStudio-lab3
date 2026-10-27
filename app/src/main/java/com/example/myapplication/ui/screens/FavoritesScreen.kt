package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.domain.RecipeFilter
import com.example.myapplication.ui.components.BottomTabs
import com.example.myapplication.ui.components.RecipeCard
import com.example.myapplication.ui.components.ScreenTitle
import com.example.myapplication.ui.components.SearchField
import com.example.myapplication.ui.components.SectionLabel
import com.example.myapplication.ui.navigation.Routes
import com.example.myapplication.ui.viewmodel.RecipesViewModel

@Composable
fun FavoritesScreen(
    onTabClick: (String) -> Unit,
    onRecipeClick: (Long) -> Unit,
    viewModel: RecipesViewModel = viewModel()
) {
    val recipes by viewModel.recipes.collectAsStateWithLifecycle()
    var query by rememberSaveable { mutableStateOf("") }

    val favorites = recipes.orEmpty().filter { it.isFavorite }
    val shownRecipes = RecipeFilter.filter(favorites, query, null)

    Scaffold(
        bottomBar = { BottomTabs(currentRoute = Routes.FAVORITES, onNavigate = onTabClick) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { ScreenTitle(stringResource(R.string.tab_favorites)) }
            item {
                SearchField(
                    value = query,
                    onValueChange = { query = it },
                    hint = stringResource(R.string.search_hint_short)
                )
            }
            item { SectionLabel(stringResource(R.string.saved_label)) }

            if (recipes != null && shownRecipes.isEmpty()) {
                item {
                    Text(
                        text = stringResource(
                            if (favorites.isEmpty()) R.string.empty_favorites else R.string.nothing_found
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            items(shownRecipes, key = { it.id }) { recipe ->
                RecipeCard(recipe = recipe, onClick = { onRecipeClick(recipe.id) })
            }
        }
    }
}
