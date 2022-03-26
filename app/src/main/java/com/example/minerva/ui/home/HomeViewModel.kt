package com.example.minerva.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.minerva.data.model.Article
import com.example.minerva.data.model.NewsDto
import com.example.minerva.data.repository.RepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: RepositoryInterface) : ViewModel() {

    val result: Flow<NewsDto> = flow {
        val data = repo.getNewsResponseFromApi("us")
        emit(data.body()!!)
    }

    val searchQuery = MutableStateFlow("")

    private val articlesFlow = searchQuery.flatMapLatest {
        repo.getLocalArticle(it)
    }

    val searchedArticles = articlesFlow.asLiveData()

//    fun getLocalArticles() = repo.getLocalArticle()

    suspend fun insertIntoFavourite(article: Article) {
        repo.insertFavouriteArticle(article)
    }

}