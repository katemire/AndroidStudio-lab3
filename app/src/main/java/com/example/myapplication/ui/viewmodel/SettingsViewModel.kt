package com.example.myapplication.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AppLanguage
import com.example.myapplication.data.AppSettings
import com.example.myapplication.data.SettingsStore
import com.example.myapplication.data.ThemeMode
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val store = SettingsStore(application)

    val settings: StateFlow<AppSettings> = store.settings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), AppSettings())

    fun setTheme(theme: ThemeMode) {
        viewModelScope.launch { store.saveTheme(theme) }
    }

    fun setLanguage(language: AppLanguage) {
        viewModelScope.launch { store.saveLanguage(language) }
    }
}
