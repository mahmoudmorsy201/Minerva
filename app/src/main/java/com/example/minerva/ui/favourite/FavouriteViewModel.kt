package com.example.minerva.ui.favourite

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import com.example.minerva.R
import com.example.minerva.data.model.Article
import com.example.minerva.data.repository.RepositoryInterface
import com.example.minerva.util.Constants
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

    fun NavigateToDetails(it: Article, view: View) {
        val nav = Navigation.findNavController(view)
        val bundle = Bundle()
        bundle.putSerializable(Constants.ARTICLE_KEY, it)
        nav.navigate(R.id.action_navigation_favourite_to_navigation_details, bundle)
    }


}