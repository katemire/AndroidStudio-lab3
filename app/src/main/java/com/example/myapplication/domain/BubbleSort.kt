package com.example.myapplication.domain

import com.example.myapplication.data.InputParser
import com.example.myapplication.data.ResultFormatter

data class SortResult(
    val sorted: List<Int>,
    val passes: Int,
    val swaps: Int,
)

object BubbleSort {
    fun sort(input: List<Int>): SortResult {
        val arr = input.toMutableList()
        val n = arr.size
        var passes = 0
        var swaps = 0
        for (i in 0 until n - 1) {
            var swapped = false
            for (j in 0 until n - 1 - i) {
                if (arr[j] > arr[j + 1]) {
                    val tmp = arr[j]
                    arr[j] = arr[j + 1]
                    arr[j + 1] = tmp
                    swaps++
                    swapped = true
                }
            }
            passes++
            if (!swapped) break
        }
        return SortResult(arr, passes, swaps)
    }

    fun runSort(text: String): String {
        val parsed = InputParser.parse(text) ?: return "Ошибка: Введите целые числа"
        if (parsed.isEmpty()) return ""
        return ResultFormatter.format(sort(parsed))
    }
}

