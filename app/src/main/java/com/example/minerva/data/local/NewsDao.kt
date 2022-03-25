package com.example.minerva.data.local

import androidx.room.*
import com.example.minerva.data.model.NewsDto
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(newsDto: NewsDto) : Long

    @Query("SELECT * FROM news")
    fun getNewsFromDB() : Flow<NewsDto>
}