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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.minvera.util.*
import kotlinx.coroutines.flow.toList
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

    fun getLocalArticles() = repo.getLocalArticle()

    suspend fun insertIntoFavourite(article: Article) {
        repo.insertFavouriteArticle(article)
    }

    fun filterData(newsList: List<Article>, localList: List<Article>) {
        val sum = newsList + localList
        val result = sum.groupBy { it.isFavourite }
            .filter { it.value.size == 1 }
            .flatMap { it.value }
    }


}