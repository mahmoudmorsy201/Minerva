package com.example.minerva.usersystem.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minerva.data.model.User
import com.example.minerva.data.repository.RepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repo: RepositoryInterface) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    suspend fun checkUserCredentials(email: String, password: String) {
        _user.value = repo.getUserByEmail(email, password)
    }

}