package com.example.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.data.RecipeEntity

@Composable
fun RecipeCard(recipe: RecipeEntity, onClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.surfaceVariant)
            .border(1.dp, colors.outline)
            .clickable(onClick = onClick)
    ) {
        PhotoStub(modifier = Modifier.height(110.dp))
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = recipe.title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = recipe.category, fontSize = 13.sp, color = colors.onSurfaceVariant)
                Text(
                    text = stringResource(R.string.minutes, recipe.cookingTime),
                    fontSize = 13.sp,
                    color = colors.onSurfaceVariant
                )
            }
        }
    }
}
