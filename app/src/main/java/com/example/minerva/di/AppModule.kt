package com.example.minerva.di

import android.app.Application
import androidx.room.Room
import com.example.minerva.data.local.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDataBase(
        app: Application,
        callback: AppDataBase.Callback
    ) = Room.databaseBuilder(app, AppDataBase::class.java, "minvera_database")
        .fallbackToDestructiveMigration()
        .addCallback(callback)
        .build()


    @Provides
    fun providesUserDao(db: AppDataBase) = db.userDao()

    @Provides
    fun providesNewsDao(db: AppDataBase) = db.newsDao()

    @Provides
    fun providesFavouriteArticleDao(db: AppDataBase) = db.favouriteArticleDao()

    @ApplicationScope
    @Provides
    @Singleton
    fun providesApplicationScope() =
        CoroutineScope(SupervisorJob())//when child of coroutine failed keep other child run

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class ApplicationScope
}