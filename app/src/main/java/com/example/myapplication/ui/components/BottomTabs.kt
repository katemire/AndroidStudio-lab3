package com.example.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.ui.navigation.Routes
@Composable
fun BottomTabs(currentRoute: String, onNavigate: (String) -> Unit) {
    val colors = MaterialTheme.colorScheme
    val tabs = listOf(
        Routes.HOME to R.string.tab_home,
        Routes.FAVORITES to R.string.tab_favorites,
        Routes.SETTINGS to R.string.tab_settings
    )

    Column(
        modifier = Modifier
            .background(colors.surfaceVariant)
            .navigationBarsPadding()
    ) {
        HorizontalDivider(color = colors.outline)
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)) {
            tabs.forEachIndexed { index, (route, label) ->
                val selected = route == currentRoute
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(if (selected) colors.secondary else Color.Transparent)
                        .clickable { onNavigate(route) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(label),
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                        color = if (selected) colors.onSecondary else colors.onSurface
                    )
                }
                if (index < tabs.lastIndex) {
                    VerticalDivider(color = colors.outline)
                }
            }
        }
    }
}
