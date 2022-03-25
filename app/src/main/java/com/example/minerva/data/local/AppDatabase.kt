package com.example.minerva.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.minerva.data.model.FavouriteArticle
import com.example.minerva.data.model.NewsDto
import com.example.minerva.data.model.User
import com.example.minerva.di.AppModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [User::class,
        NewsDto::class,
               FavouriteArticle::class], version = 1
)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun newsDao(): NewsDao
    abstract fun favouriteArticleDao() : FavouriteArticleDao
    class Callback @Inject constructor(
        private val dataBase: Provider<AppDataBase>,
        @AppModule.ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val newsDao = dataBase.get().newsDao()
            val userDao = dataBase.get().userDao()
            applicationScope.launch {
                // dao.insert()
            }
        }

    }

}