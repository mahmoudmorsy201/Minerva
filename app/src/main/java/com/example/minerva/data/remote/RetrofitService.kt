package com.example.minerva.data.remote

import com.example.minerva.data.model.NewsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


const val API_KEY = "cfb6154624504c3a9e93bc4ab969e941"

interface RetrofitService {
    @GET("/top-headlines?")
    suspend fun getAllStatusWeatherByLatLon(
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsDto>
}