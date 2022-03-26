package com.example.minerva.data.sharedpref

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyPreference @Inject constructor(@ApplicationContext context: Context) : SharedPrefInterface {

    private val sharedPreferencesFileName = "SETTINGS"
    private val userEmailInSharedPreferences = "USER_EMAIL_IN_SHARED_PREFERENCES"
    private val userPasswordInSharedPreferences = "USER_PASSWORD_IN_SHARED_PREFERENCES"

    val prefs = context.getSharedPreferences(sharedPreferencesFileName, Context.MODE_PRIVATE)

    override fun getUserEmail(): String {
        return prefs.getString(userEmailInSharedPreferences, "")!!
    }

    override fun setUserEmail(query: String) {
        prefs.edit().putString(userEmailInSharedPreferences, query).apply()
    }

    override fun getUserPassword(): String {
        return prefs.getString(userPasswordInSharedPreferences, "")!!
    }

    override fun setUserPassword(query: String) {
        prefs.edit().putString(userPasswordInSharedPreferences, query).apply()
    }
}