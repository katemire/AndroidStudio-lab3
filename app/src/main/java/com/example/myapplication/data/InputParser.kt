package com.example.myapplication.data

object InputParser {
    fun parse(text: String): List<Int>? {

        if (text.isBlank()) return emptyList()


        return try {
            text.trim().split(Regex("[,\\s]+")).map { it.toInt() }
        } catch (e: NumberFormatException) {
            null
        }

    }
}