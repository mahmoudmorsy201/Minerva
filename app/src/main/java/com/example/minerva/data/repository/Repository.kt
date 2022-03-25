package com.example.minerva.data.repository

import com.example.minerva.data.local.FavouriteArticleDao
import com.example.minerva.data.local.NewsDao
import com.example.minerva.data.local.UserDao
import com.example.minerva.data.remote.RetrofitService
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class Repository (
    private var retrofitService: RetrofitService,
    private var userDao: UserDao,
    private var newsDao: NewsDao,
    private var favouriteArticleDao: FavouriteArticleDao
        ) : RepositoryInterface {

}