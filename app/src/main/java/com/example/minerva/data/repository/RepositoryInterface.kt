package com.example.minerva.data.repository

import com.example.minerva.data.model.FavouriteArticle
import com.example.minerva.data.model.NewsDto
import com.example.minerva.data.model.User
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RepositoryInterface {

    //For User table
    suspend fun insertUser(user: User)
    fun getUserByEmail(email: String): Flow<User>
    suspend fun updateUser(newUser: User)
    suspend fun deleteUser(user: User)

    //For News Api Response
    suspend fun getNewsResponseFromApi(country: String): Response<NewsDto>

    //For News table database
    suspend fun insertNews(newsDto: NewsDto)
    fun getStoredNewsResponse(): Flow<NewsDto>

    //For favourite articles
    suspend fun insertFavouriteArticle(favouriteArticle: FavouriteArticle)
    fun getStoredFavouriteArticlesByUserEmail(userEmail: String): Flow<List<FavouriteArticle>>
    suspend fun deleteFavouriteArticle(favouriteArticle: FavouriteArticle)
}