package com.example.myapplication.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.ui.components.FormField
import com.example.myapplication.ui.components.OutlineButton
import com.example.myapplication.ui.components.PhotoStub
import com.example.myapplication.ui.components.PrimaryButton
import com.example.myapplication.ui.components.ScreenTitle
import com.example.myapplication.ui.viewmodel.RecipeFormViewModel

@Composable
fun RecipeFormScreen(
    onFinished: () -> Unit,
    viewModel: RecipeFormViewModel = viewModel()
) {
    val context = LocalContext.current
    val colors = MaterialTheme.colorScheme
    val errors = viewModel.errors
    val required = stringResource(R.string.error_required)

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ScreenTitle(
                stringResource(if (viewModel.isEdit) R.string.form_edit else R.string.form_new)
            )

            PhotoStub(modifier = Modifier.height(100.dp), dashed = true)

            FormField(
                label = stringResource(R.string.label_title),
                value = viewModel.title,
                onValueChange = { viewModel.title = it },
                hint = stringResource(R.string.hint_title),
                errorText = if (errors.title) required else null
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                FormField(
                    label = stringResource(R.string.label_category),
                    value = viewModel.category,
                    onValueChange = { viewModel.category = it },
                    hint = stringResource(R.string.hint_category),
                    modifier = Modifier.weight(1f),
                    errorText = if (errors.category) required else null
                )
                FormField(
                    label = stringResource(R.string.label_time),
                    // в поле можно вводить только цифры, не больше четырёх
                    value = viewModel.time,
                    onValueChange = { text ->
                        if (text.length <= 4 && text.all { it.isDigit() }) {
                            viewModel.time = text
                        }
                    },
                    hint = stringResource(R.string.hint_time),
                    modifier = Modifier.weight(1f),
                    errorText = when {
                        !errors.time -> null
                        viewModel.time.isBlank() -> required
                        else -> stringResource(R.string.error_time)
                    },
                    keyboardType = KeyboardType.Number,
                    suffix = { Text(stringResource(R.string.unit_min)) }
                )
            }

            FormField(
                label = stringResource(R.string.label_ingredients),
                value = viewModel.ingredients,
                onValueChange = { viewModel.ingredients = it },
                hint = stringResource(R.string.hint_ingredients),
                errorText = if (errors.ingredients) required else null,
                minLines = 4
            )

            FormField(
                label = stringResource(R.string.label_instructions),
                value = viewModel.instructions,
                onValueChange = { viewModel.instructions = it },
                hint = stringResource(R.string.hint_instructions),
                errorText = if (errors.instructions) required else null,
                minLines = 4
            )

            PrimaryButton(
                text = stringResource(if (viewModel.isEdit) R.string.btn_save_changes else R.string.btn_save),
                onClick = {
                    viewModel.save(
                        onSaved = onFinished,
                        onInvalid = {
                            Toast.makeText(context, R.string.toast_fill, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            )

            if (viewModel.isEdit) {
                OutlineButton(
                    text = stringResource(R.string.btn_delete),
                    onClick = { viewModel.showDeleteDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    color = colors.error
                )
            } else {
                Text(
                    text = stringResource(R.string.required_note),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 13.sp,
                    color = colors.onSurfaceVariant
                )
            }
        }
    }

    if (viewModel.showDeleteDialog) {
        DeleteDialog(
            onCancel = { viewModel.showDeleteDialog = false },
            onConfirm = { viewModel.delete(onFinished) }
        )
    }
}

@Composable
private fun DeleteDialog(onCancel: () -> Unit, onConfirm: () -> Unit) {
    val colors = MaterialTheme.colorScheme

    Dialog(onDismissRequest = onCancel) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(colors.surfaceVariant)
                .border(1.dp, colors.outline)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.dialog_title),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.dialog_text),
                textAlign = TextAlign.Center,
                color = colors.onSurfaceVariant
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlineButton(
                    text = stringResource(R.string.btn_cancel),
                    onClick = onCancel,
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = onConfirm,
                    modifier = Modifier.weight(1f),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.error,
                        contentColor = colors.onError
                    )
                ) {
                    Text(text = stringResource(R.string.btn_delete_confirm), fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
