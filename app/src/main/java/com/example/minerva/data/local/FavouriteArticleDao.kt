package com.example.minerva.data.local

import androidx.room.*
import com.example.minerva.data.model.FavouriteArticle
import kotlinx.coroutines.flow.Flow


@Dao
interface FavouriteArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteArticle(favouriteArticle: FavouriteArticle)

    @Query("SELECT * FROM favourites WHERE email = :userEmail")
    fun getAllFavouriteArticles(userEmail: String): Flow<List<FavouriteArticle>>

    @Delete
    suspend fun deleteFavouriteArticle(favouriteArticle: FavouriteArticle)

}