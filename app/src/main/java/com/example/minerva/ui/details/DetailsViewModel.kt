package com.example.minerva.ui.details

import androidx.lifecycle.ViewModel
import com.example.minerva.data.repository.RepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: RepositoryInterface
) : ViewModel() {
    // TODO: Implement the ViewModel
}