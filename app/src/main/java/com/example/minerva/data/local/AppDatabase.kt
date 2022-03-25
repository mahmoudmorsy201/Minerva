package com.example.minerva.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.minerva.data.model.Article
import com.example.minerva.data.model.User

@Database(
    entities = [User::class,
        Article::class], version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun articlesDao(): ArticleDao
}