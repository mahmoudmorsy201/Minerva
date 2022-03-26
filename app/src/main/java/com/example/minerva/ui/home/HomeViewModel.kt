package com.example.minerva.ui.home


import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.minerva.R
import com.example.minerva.data.model.Article
import com.example.minerva.data.model.NewsDto
import com.example.minerva.data.repository.RepositoryInterface
import com.example.minerva.util.Constants.ARTICLE_KEY
import com.example.minerva.util.InternetConnectivity
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
    fun NavigateToDetails(it: Article, view: View) {
        val nav = Navigation.findNavController(view)
        val bundle = Bundle()
        bundle.putSerializable(ARTICLE_KEY, it)
        nav.navigate(R.id.action_navigation_home_to_navigation_details, bundle)
    }

    val result: Flow<NewsDto> = flow {
        val data = repo.getNewsResponseFromApi("us")
        emit(data.body()!!)
    }

    val searchQuery = MutableStateFlow("")

    private val articlesFlow = searchQuery.flatMapLatest {
        repo.getLocalArticle(it)
    }


    val searchedArticles = articlesFlow.asLiveData()

    suspend fun insertIntoFavourite(article: Article) {
        repo.insertFavouriteArticle(article)
    }

}