package com.example.myapplication.ui

import android.app.LocaleManager
import android.content.Context
import android.os.LocaleList
import com.example.myapplication.data.AppLanguage
import java.util.Locale

object AppLocale {

    fun apply(context: Context, language: AppLanguage) {
        if (Locale.getDefault().language == language.tag) return

        val localeManager = context.getSystemService(LocaleManager::class.java)
        localeManager.applicationLocales = LocaleList.forLanguageTags(language.tag)
    }
}
