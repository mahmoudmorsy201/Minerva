package com.example.minerva.ui.details

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.minerva.data.model.Article
import com.example.minerva.data.repository.RepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repo: RepositoryInterface,
     val context: Application
) : AndroidViewModel(context) {
    fun openAtricleSource(url: String) {

        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.data = Uri.parse(url)
       context. startActivity(intent)
    }
    suspend fun insertIntoFavourite(article: Article) {
        repo.insertFavouriteArticle(article)
    }
}