package com.example.minerva.usersystem.signup

import androidx.lifecycle.ViewModel
import com.example.minerva.data.model.User
import com.example.minerva.data.repository.RepositoryInterface
import com.example.minerva.data.sharedpref.MyPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpFragmentViewModel @Inject constructor(
    private val repo: RepositoryInterface,
    private val sharedPreferences: MyPreference
) :
    ViewModel() {

    suspend fun addNewUser(user: User) {
        repo.insertUser(user)
    }

    fun saveUserDataInSharedPreferences(email: String, password: String) {
        sharedPreferences.setUserEmail(email)
        sharedPreferences.setUserPassword(password)
    }

}