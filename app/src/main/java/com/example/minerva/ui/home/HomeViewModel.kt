package com.example.minerva.ui.home

import androidx.lifecycle.ViewModel
import com.example.minerva.data.model.NewsDto
import com.example.minerva.data.repository.RepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.minvera.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: RepositoryInterface) : ViewModel() {

    val result: Flow<NewsDto> = flow {
        val data = repo.getNewsResponseFromApi("us")
        emit(data.body()!!)
    }

}