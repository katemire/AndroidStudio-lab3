package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.ui.components.BottomTabs
import com.example.myapplication.ui.components.ScreenTitle
import com.example.myapplication.ui.navigation.Routes

@Composable
fun SettingsScreen(onTabClick: (String) -> Unit) {
    Scaffold(
        bottomBar = { BottomTabs(currentRoute = Routes.SETTINGS, onNavigate = onTabClick) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            ScreenTitle(stringResource(R.string.tab_settings))
        }
    }
}
