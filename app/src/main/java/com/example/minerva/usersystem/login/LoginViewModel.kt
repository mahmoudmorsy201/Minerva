package com.example.minerva.usersystem.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minerva.data.local.MyPreference
import com.example.minerva.data.model.User
import com.example.minerva.data.repository.RepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: RepositoryInterface,
    private val sharedPreferences: MyPreference
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    suspend fun checkUserCredentials(email: String, password: String) {
        _user.value = repo.getUserByEmail(email, password)
    }

    fun saveUserDataInSharedPreferences(email: String, password: String) {
        sharedPreferences.setUserEmail(email)
        sharedPreferences.setUserPassword(password)
    }

}