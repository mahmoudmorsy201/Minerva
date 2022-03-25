package com.example.minvera.usersystem.signup

import androidx.lifecycle.ViewModel
import com.example.minerva.data.model.User
import com.example.minerva.data.repository.RepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpFragmentViewModel @Inject constructor(private val repo: RepositoryInterface) :
    ViewModel() {

    suspend fun addNewUser(user: User) {
        repo.insertUser(user)
    }
}