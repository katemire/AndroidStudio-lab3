package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication.ui.screens.BubbleSortScreen

@Composable
fun AppRoot(modifier: Modifier = Modifier) {
    BubbleSortScreen(modifier = modifier)
}