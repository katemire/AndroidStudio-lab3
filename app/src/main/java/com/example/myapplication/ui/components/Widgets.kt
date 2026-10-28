package com.example.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

@Composable
fun ScreenTitle(text: String) {
    Text(text = text, fontSize = 28.sp, fontWeight = FontWeight.Bold)
}

@Composable
fun SectionLabel(text: String) {
    Text(text = text, fontWeight = FontWeight.Bold)
}

@Composable
fun PhotoStub(modifier: Modifier = Modifier, dashed: Boolean = false) {
    val lineColor = MaterialTheme.colorScheme.outline
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .drawBehind {
                if (dashed) {
                    drawRect(
                        color = lineColor,
                        style = Stroke(width = 2f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f)))
                    )
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.photo_stub),
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Composable
fun fieldColors(): TextFieldColors = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
    errorContainerColor = MaterialTheme.colorScheme.surfaceVariant,
    focusedBorderColor = MaterialTheme.colorScheme.primary,
    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
    errorBorderColor = MaterialTheme.colorScheme.error
)

@Composable
fun SearchField(value: String, onValueChange: (String) -> Unit, hint: String) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(hint) },
        singleLine = true,
        shape = RectangleShape,
        colors = fieldColors()
    )
}

@Composable
fun CategoryChip(text: String, selected: Boolean, onClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    Box(
        modifier = Modifier
            .background(if (selected) colors.primary else colors.surfaceVariant)
            .border(1.dp, if (selected) colors.primary else colors.outline)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            color = if (selected) colors.onPrimary else colors.onSurface
        )
    }
}
