package com.example.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [RecipeEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "recipes.db"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        // выполняется один раз, когда база создаётся
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            addStartRecipes(db)
                        }
                    })
                    .build()
                    .also { instance = it }
            }
        }
    }
}

// Рецепты из макетов лабораторной работы 2, чтобы список не был пустым
private fun addStartRecipes(db: SupportSQLiteDatabase) {
    val sql = "INSERT INTO recipes (title, category, cookingTime, ingredients, instructions, isFavorite) " +
            "VALUES (?, ?, ?, ?, ?, ?)"

    db.execSQL(
        sql, arrayOf<Any?>(
            "Паста Карбонара", "Паста", 25,
            listOf("Спагетти — 200 г", "Бекон — 100 г", "Яйца — 2 шт.", "Пармезан — 50 г").joinToString("\n"),
            listOf(
                "1. Отварите пасту.",
                "2. Обжарьте бекон.",
                "3. Смешайте яйца с сыром.",
                "4. Соедините всё с пастой."
            ).joinToString("\n"),
            1
        )
    )
    db.execSQL(
        sql, arrayOf<Any?>(
            "Сырники со сметаной", "Завтрак", 20,
            listOf(
                "Творог — 400 г",
                "Яйцо — 1 шт.",
                "Мука — 4 ст. л.",
                "Сахар — 2 ст. л.",
                "Сметана — 100 г"
            ).joinToString("\n"),
            listOf(
                "1. Смешайте творог, яйцо, сахар и муку.",
                "2. Сформируйте небольшие лепёшки.",
                "3. Обжарьте с двух сторон до золотистой корочки.",
                "4. Подавайте со сметаной."
            ).joinToString("\n"),
            0
        )
    )
    db.execSQL(
        sql, arrayOf<Any?>(
            "Яблочная шарлотка", "Десерт", 50,
            listOf("Яблоки — 4 шт.", "Яйца — 3 шт.", "Сахар — 1 стакан", "Мука — 1 стакан").joinToString("\n"),
            listOf(
                "1. Взбейте яйца с сахаром.",
                "2. Добавьте муку и перемешайте.",
                "3. Нарежьте яблоки и выложите в форму.",
                "4. Залейте тестом и выпекайте 40 минут при 180 градусах."
            ).joinToString("\n"),
            1
        )
    )
    db.execSQL(
        sql, arrayOf<Any?>(
            "Томатный суп", "Обед", 35,
            listOf("Помидоры — 600 г", "Лук — 1 шт.", "Чеснок — 2 зубчика", "Бульон — 500 мл").joinToString("\n"),
            listOf(
                "1. Обжарьте лук и чеснок.",
                "2. Добавьте нарезанные помидоры и тушите 10 минут.",
                "3. Влейте бульон и варите 15 минут.",
                "4. Измельчите суп блендером."
            ).joinToString("\n"),
            1
        )
    )
}
