package com.example.minerva.data.repository

import com.example.minerva.data.local.ArticleDao
import com.example.minerva.data.sharedpref.MyPreference
import com.example.minerva.data.local.UserDao
import com.example.minerva.data.model.Article
import com.example.minerva.data.model.NewsDto
import com.example.minerva.data.model.User
import com.example.minerva.data.remote.RetrofitService
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class Repository @Inject constructor(
    private var retrofitService: RetrofitService,
    private var userDao: UserDao,
    private var articleDao: ArticleDao,
    private var sharedPref: MyPreference
) : RepositoryInterface {

    override suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    override suspend fun getUserByEmail(email: String, password: String): User? =
        userDao.getUserByEmail(email, password)

    override suspend fun updateUser(newUser: User) {
        userDao.updateUserData(newUser)
    }

    override suspend fun updateUser(updateUserPassword: String, email: String) {
        userDao.updateUserPassword(updateUserPassword, email)
    }

    override suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    override suspend fun getNewsResponseFromApi(country: String): Response<NewsDto> =
        retrofitService.getNewsFromApi(country).also {
            it.body()?.let { it1 -> insertListOfArticles(it1.articles) }
        }

    override suspend fun insertFavouriteArticle(favouriteArticle: Article) {
        articleDao.insertFavouriteArticle(favouriteArticle)
    }

    override suspend fun insertListOfArticles(articles: List<Article>) {
        articles.forEach {
            it.email = sharedPref.getUserEmail()
        }
        articleDao.insertListOfArticles(articles)
    }

    override fun getStoredFavouriteArticlesByUserEmail(userEmail: String): Flow<List<Article>> =
        articleDao.getAllFavouriteArticlesByUserEmail(userEmail)

    override fun getStoredFavouriteArticles(searchQuery: String): Flow<List<Article>> =
        articleDao.getAllFavouriteArticles(searchQuery)


    override fun getLocalArticle(searchQuery: String): Flow<List<Article>> =
        articleDao.getArticlesFromRoom(searchQuery)


}