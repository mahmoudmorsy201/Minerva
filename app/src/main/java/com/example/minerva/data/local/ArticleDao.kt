package com.example.minerva.data.local

import androidx.room.*
import com.example.minerva.data.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertListOfArticles(articles: List<Article>)

    @Update
    suspend fun insertFavouriteArticle(favouriteArticle: Article)

    @Query("SELECT * FROM articles WHERE title LIKE '%' || :searchQuery || '%' AND isFavourite = :isFavourite")
    fun getAllFavouriteArticles(
        searchQuery: String,
        isFavourite: Boolean = true
    ): Flow<List<Article>>

    @Query("SELECT * FROM articles WHERE email = :userEmail")
    fun getAllFavouriteArticlesByUserEmail(userEmail: String): Flow<List<Article>>

    @Query("SELECT * FROM articles WHERE title LIKE '%' || :searchQuery || '%'")
    fun getArticlesFromRoom(searchQuery: String): Flow<List<Article>>

}