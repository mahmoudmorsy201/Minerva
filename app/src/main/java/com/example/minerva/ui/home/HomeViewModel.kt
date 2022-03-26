package com.example.minerva.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.minerva.data.model.Article
import com.example.minerva.data.model.NewsDto
import com.example.minerva.data.repository.RepositoryInterface
import com.example.minerva.util.InternetConnectivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.minvera.util.*
import kotlinx.coroutines.flow.toList
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: RepositoryInterface) : ViewModel() {

    val result: Flow<NewsDto> = flow {
        val data = repo.getNewsResponseFromApi("us")
        emit(data.body()!!)
    }

    fun getLocalArticles() = repo.getLocalArticle()

    suspend fun insertIntoFavourite(article: Article) {
        repo.insertFavouriteArticle(article)
    }

    fun filterData(newsList: List<Article> , localList: List<Article>) {
        val sum = newsList + localList
        val result = sum.groupBy{it.isFavourite}
            .filter { it.value.size == 1 }
            .flatMap { it.value }
    }


}