package com.example.minerva.ui.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.minerva.data.model.Article
import com.example.minerva.data.repository.RepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(private val repo: RepositoryInterface) : ViewModel() {

    val searchQuery = MutableStateFlow("")

    private val articlesFlow = searchQuery.flatMapLatest {
        repo.getStoredFavouriteArticles(it)
    }

    val searchedFavArticles = articlesFlow.asLiveData()

    suspend fun updateFavouriteArticle(article: Article) {
        repo.insertFavouriteArticle(article)
    }


}