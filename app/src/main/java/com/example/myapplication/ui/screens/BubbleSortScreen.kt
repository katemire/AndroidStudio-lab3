package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.domain.BubbleSort
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun BubbleSortScreen(modifier: Modifier = Modifier) {
    var input by remember { mutableStateOf("") }
    var output by remember { mutableStateOf("") }
    var autoMode by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = input,
            onValueChange = { newValue ->
                input = newValue
                if (autoMode) {
                    output = BubbleSort.runSort(newValue)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Числа") },
        )

        OutlinedTextField(
            value = output,
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Результат") },
            minLines = 2
        )

        Button(
            onClick = {
                output = BubbleSort.runSort(input)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !autoMode
        ) {
            Text("Отсортировать")
        }

        Button(
            onClick = {
                autoMode = !autoMode
                if (autoMode) {
                    output = BubbleSort.runSort(input)
                }
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(if (autoMode) "Автоматический режим ВКЛ" else "Автоматический режим ВЫКЛ")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BubbleSortScreenPreview() {
    MyApplicationTheme {
        BubbleSortScreen()
    }
}