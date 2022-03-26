package com.example.minerva.data.repository

import com.example.minerva.data.model.Article
import com.example.minerva.data.model.NewsDto
import com.example.minerva.data.model.User
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RepositoryInterface {

    //For User table
    suspend fun insertUser(user: User)
    suspend fun getUserByEmail(email: String, password: String): User?
    suspend fun updateUser(newUser: User)
    suspend fun deleteUser(user: User)
    suspend fun updateUser(updateUserPassword: String, email: String)

    //For News Api Response
    suspend fun getNewsResponseFromApi(country: String): Response<NewsDto>

    //For favourite articles
    suspend fun insertFavouriteArticle(favouriteArticle: Article)
    suspend fun insertListOfArticles(articles: List<Article>)
    fun getStoredFavouriteArticlesByUserEmail(userEmail: String): Flow<List<Article>>
    fun getStoredFavouriteArticles(searchQuery: String): Flow<List<Article>>

    //for room articles
    fun getLocalArticle(): Flow<List<Article>>

}