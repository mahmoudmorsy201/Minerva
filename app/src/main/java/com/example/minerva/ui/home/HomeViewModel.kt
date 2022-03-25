package com.example.minerva.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minerva.data.model.Article
import com.example.minerva.data.model.NewsDto
import com.example.minerva.data.model.User
import com.example.minerva.data.repository.RepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: RepositoryInterface) : ViewModel() {

    val result: Flow<NewsDto> = flow {
        val data = repo.getNewsResponseFromApi("us")
        emit(data.body()!!)
    }

}