package com.example.myapplication.data

import java.util.Locale

enum class ThemeMode(val key: String) {
    LIGHT("light"),
    DARK("dark"),
    SYSTEM("system");

    companion object {
        fun fromKey(key: String?): ThemeMode = entries.firstOrNull { it.key == key } ?: SYSTEM
    }
}

enum class AppLanguage(val tag: String) {
    RU("ru"),
    EN("en");

    companion object {
        fun fromTag(tag: String?): AppLanguage = entries.firstOrNull { it.tag == tag } ?: RU

        // язык, который сейчас используется в приложении
        fun current(): AppLanguage = fromTag(Locale.getDefault().language)
    }
}

data class AppSettings(
    val theme: ThemeMode = ThemeMode.SYSTEM,
    val language: AppLanguage = AppLanguage.current()
)
