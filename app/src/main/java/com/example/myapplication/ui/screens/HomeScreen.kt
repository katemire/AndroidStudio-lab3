package com.example.myapplication.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.domain.MealCategory
import com.example.myapplication.domain.RecipeFilter
import com.example.myapplication.ui.components.BottomTabs
import com.example.myapplication.ui.components.CategoryChip
import com.example.myapplication.ui.components.RecipeCard
import com.example.myapplication.ui.components.ScreenTitle
import com.example.myapplication.ui.components.SearchField
import com.example.myapplication.ui.components.SectionLabel
import com.example.myapplication.ui.navigation.Routes
import com.example.myapplication.ui.viewmodel.RecipesViewModel

@Composable
fun HomeScreen(
    onTabClick: (String) -> Unit,
    onRecipeClick: (Long) -> Unit,
    onAddClick: () -> Unit,
    viewModel: RecipesViewModel = viewModel()
) {
    val recipes by viewModel.recipes.collectAsStateWithLifecycle()
    var query by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf<MealCategory?>(null) }

    val shownRecipes = RecipeFilter.filter(recipes.orEmpty(), query, category)

    Scaffold(
        bottomBar = { BottomTabs(currentRoute = Routes.HOME, onNavigate = onTabClick) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                shape = RectangleShape,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Text(text = "+", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 88.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { ScreenTitle(stringResource(R.string.app_name)) }
            item {
                SearchField(
                    value = query,
                    onValueChange = { query = it },
                    hint = stringResource(R.string.search_hint)
                )
            }
            item { CategoryRow(selected = category, onSelect = { category = it }) }
            item { SectionLabel(stringResource(R.string.recipes_label)) }

            if (recipes != null && shownRecipes.isEmpty()) {
                item {
                    Text(
                        text = stringResource(
                            if (recipes.orEmpty().isEmpty()) R.string.empty_recipes else R.string.nothing_found
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

@Composable
private fun CategoryRow(selected: MealCategory?, onSelect: (MealCategory?) -> Unit) {
    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CategoryChip(
            text = stringResource(R.string.cat_all),
            selected = selected == null,
            onClick = { onSelect(null) }
        )
        CategoryChip(
            text = stringResource(R.string.cat_breakfast),
            selected = selected == MealCategory.BREAKFAST,
            onClick = { onSelect(MealCategory.BREAKFAST) }
        )
        CategoryChip(
            text = stringResource(R.string.cat_lunch),
            selected = selected == MealCategory.LUNCH,
            onClick = { onSelect(MealCategory.LUNCH) }
        )
        CategoryChip(
            text = stringResource(R.string.cat_dessert),
            selected = selected == MealCategory.DESSERT,
            onClick = { onSelect(MealCategory.DESSERT) }
        )
    }
}
