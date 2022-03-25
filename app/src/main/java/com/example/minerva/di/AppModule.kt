package com.example.minerva.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.minerva.data.local.AppDatabase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesDatabase(
        @ApplicationContext app: Context,
    ) = Room.databaseBuilder(app,
        AppDatabase::class.java,
        "minerva_database")
        .build()


    @Provides
    @Singleton
    fun providesUserDao(db: AppDatabase) = db.userDao()

    @Provides
    @Singleton
    fun providesArticlesDao(db: AppDatabase) = db.articlesDao()
}