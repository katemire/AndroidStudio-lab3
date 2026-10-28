package com.example.myapplication.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsStore(private val context: Context) {

    private val themeKey = stringPreferencesKey("theme")
    private val languageKey = stringPreferencesKey("language")

    val settings: Flow<AppSettings> = context.dataStore.data.map { prefs ->
        AppSettings(
            theme = ThemeMode.fromKey(prefs[themeKey]),
            // если язык ещё не выбирали, берётся текущий язык приложения
            language = prefs[languageKey]?.let { AppLanguage.fromTag(it) } ?: AppLanguage.current()
        )
    }

    suspend fun saveTheme(theme: ThemeMode) {
        context.dataStore.edit { prefs -> prefs[themeKey] = theme.key }
    }

    suspend fun saveLanguage(language: AppLanguage) {
        context.dataStore.edit { prefs -> prefs[languageKey] = language.tag }
    }
}
