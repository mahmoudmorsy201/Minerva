package com.example.minerva.data.repository

import com.example.minerva.data.local.FavouriteArticleDao
import com.example.minerva.data.local.NewsDao
import com.example.minerva.data.local.UserDao
import com.example.minerva.data.model.FavouriteArticle
import com.example.minerva.data.model.NewsDto
import com.example.minerva.data.model.User
import com.example.minerva.data.remote.RetrofitService
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import retrofit2.Response


@Module
@InstallIn(SingletonComponent::class)
class Repository(
    private var retrofitService: RetrofitService,
    private var userDao: UserDao,
    private var newsDao: NewsDao,
    private var favouriteArticleDao: FavouriteArticleDao
) : RepositoryInterface {
    override suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    override fun getUserByEmail(email: String): Flow<User> = userDao.getUserByEmail(email)

    override suspend fun updateUser(newUser: User) {
        userDao.updateUserData(newUser)
    }

    override suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    override suspend fun getNewsResponseFromApi(country: String): Response<NewsDto> =
        retrofitService.getAllStatusWeatherByLatLon(country)


    override suspend fun insertNews(newsDto: NewsDto) {
        newsDao.insertNews(newsDto)
    }

    override fun getStoredNewsResponse(): Flow<NewsDto> = newsDao.getNewsFromDB()


    override suspend fun insertFavouriteArticle(favouriteArticle: FavouriteArticle) {
        favouriteArticleDao.insertFavouriteArticle(favouriteArticle)
    }

    override fun getStoredFavouriteArticlesByUserEmail(userEmail: String): Flow<List<FavouriteArticle>> =
        favouriteArticleDao.getAllFavouriteArticlesByUserEmail(userEmail)


    override suspend fun deleteFavouriteArticle(favouriteArticle: FavouriteArticle) {
        favouriteArticleDao.deleteFavouriteArticle(favouriteArticle)
    }

}