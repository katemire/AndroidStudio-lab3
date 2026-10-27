package com.example.myapplication.ui.navigation

object Routes {
    const val HOME = "home"
    const val FAVORITES = "favorites"
    const val SETTINGS = "settings"

    const val RECIPE = "recipe/{id}"
    const val FORM = "form/{id}"

    fun recipe(id: Long) = "recipe/$id"

    fun form(id: Long) = "form/$id"
}
