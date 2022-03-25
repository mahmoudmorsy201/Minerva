package com.example.minerva.di

import com.example.minerva.data.local.ArticleDao
import com.example.minerva.data.local.UserDao
import com.example.minerva.data.remote.RetrofitService
import com.example.minerva.data.repository.Repository
import com.example.minerva.data.repository.RepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideMainRepository(
        retrofit: RetrofitService,
        userDao: UserDao,
        articlesDao: ArticleDao
    ): RepositoryInterface {
        return Repository(retrofit, userDao,articlesDao)
    }
}


