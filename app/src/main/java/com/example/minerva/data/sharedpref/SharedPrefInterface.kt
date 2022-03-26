package com.example.minerva.data.sharedpref

interface SharedPrefInterface {
    fun getUserEmail(): String
    fun setUserEmail(query: String)
    fun getUserPassword(): String
    fun setUserPassword(query: String)
}