package com.example.minerva.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.minerva.data.model.Article
import com.example.minerva.data.model.User
import com.example.minerva.di.AppModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [User::class,
        Article::class], version = 1
)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun articlesDao(): ArticleDao

    class Callback @Inject constructor(
        private val dataBase: Provider<AppDataBase>,
        @AppModule.ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val userDao = dataBase.get().userDao()
            applicationScope.launch {
                // dao.insert()
            }
        }

    }

}