package com.example.minerva.ui.setting

import androidx.lifecycle.ViewModel
import com.example.minerva.data.repository.RepositoryInterface
import com.example.minerva.data.sharedpref.MyPreference
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingFragmentViewModel @Inject constructor(
    private val pref: MyPreference,
    private val repo: RepositoryInterface
) : ViewModel() {

    fun clearSharedPreferences() {
        pref.clearAllCache()
    }

    suspend fun changePassword(newPassword: String) {
        val email = pref.getUserEmail()
        repo.updateUser(newPassword, email)
        pref.setUserPassword(newPassword)
    }


}