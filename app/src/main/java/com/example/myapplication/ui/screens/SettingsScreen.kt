package com.example.myapplication.ui.screens

import android.content.pm.PackageManager
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.data.AppLanguage
import com.example.myapplication.data.ThemeMode
import com.example.myapplication.ui.components.BottomTabs
import com.example.myapplication.ui.components.ScreenTitle
import com.example.myapplication.ui.components.SectionLabel
import com.example.myapplication.ui.navigation.Routes
import com.example.myapplication.ui.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    onTabClick: (String) -> Unit,
    viewModel: SettingsViewModel = viewModel()
) {
    val settings by viewModel.settings.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val colors = MaterialTheme.colorScheme

    val versionName = remember {
        context.packageManager
            .getPackageInfo(context.packageName, PackageManager.PackageInfoFlags.of(0))
            .versionName
            .orEmpty()
    }

    var infoText by rememberSaveable { mutableStateOf<Int?>(null) }

    val currentThemeName = stringResource(
        when (settings.theme) {
            ThemeMode.LIGHT -> R.string.theme_light
            ThemeMode.DARK -> R.string.theme_dark
            ThemeMode.SYSTEM -> R.string.theme_system_short
        }
    )

    Scaffold(
        bottomBar = { BottomTabs(currentRoute = Routes.SETTINGS, onNavigate = onTabClick) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ScreenTitle(stringResource(R.string.tab_settings))

            SectionLabel(stringResource(R.string.section_appearance))
            SettingsBox {
                SettingsRow(text = stringResource(R.string.theme_title), value = currentThemeName)
                SettingsRow(
                    text = stringResource(R.string.theme_light),
                    selected = settings.theme == ThemeMode.LIGHT,
                    onClick = { viewModel.setTheme(ThemeMode.LIGHT) }
                )
                SettingsRow(
                    text = stringResource(R.string.theme_dark),
                    selected = settings.theme == ThemeMode.DARK,
                    onClick = { viewModel.setTheme(ThemeMode.DARK) }
                )
                SettingsRow(
                    text = stringResource(R.string.theme_system),
                    selected = settings.theme == ThemeMode.SYSTEM,
                    last = true,
                    onClick = { viewModel.setTheme(ThemeMode.SYSTEM) }
                )
            }

            SectionLabel(stringResource(R.string.section_language))
            SettingsBox {
                SettingsRow(
                    text = stringResource(R.string.lang_ru),
                    selected = settings.language == AppLanguage.RU,
                    onClick = { viewModel.setLanguage(AppLanguage.RU) }
                )
                SettingsRow(
                    text = stringResource(R.string.lang_en),
                    selected = settings.language == AppLanguage.EN,
                    last = true,
                    onClick = { viewModel.setLanguage(AppLanguage.EN) }
                )
            }

            SectionLabel(stringResource(R.string.section_about))
            SettingsBox {
                SettingsRow(text = stringResource(R.string.about_version), value = versionName)
                SettingsRow(
                    text = stringResource(R.string.about_privacy),
                    onClick = { infoText = R.string.privacy_text }
                )
                SettingsRow(
                    text = stringResource(R.string.about_feedback),
                    last = true,
                    onClick = { infoText = R.string.feedback_text }
                )
            }
        }
    }

    infoText?.let { textRes ->
        AlertDialog(
            onDismissRequest = { infoText = null },
            text = { Text(stringResource(textRes)) },
            confirmButton = {
                TextButton(onClick = { infoText = null }) {
                    Text(stringResource(R.string.btn_ok))
                }
            },
            shape = RectangleShape,
            containerColor = colors.surfaceVariant
        )
    }
}

@Composable
private fun SettingsBox(content: @Composable ColumnScope.() -> Unit) {
    val colors = MaterialTheme.colorScheme
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.surfaceVariant)
            .border(1.dp, colors.outline),
        content = content
    )
}

@Composable
private fun SettingsRow(
    text: String,
    value: String? = null,
    selected: Boolean = false,
    last: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    val colors = MaterialTheme.colorScheme

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = text)
            if (value != null) {
                Text(text = value, color = colors.onSurfaceVariant)
            }
            if (selected) {
                Text(text = "✓", color = colors.primary, fontWeight = FontWeight.Bold)
            }
        }
        if (!last) {
            HorizontalDivider(color = colors.outlineVariant)
        }
    }
}
