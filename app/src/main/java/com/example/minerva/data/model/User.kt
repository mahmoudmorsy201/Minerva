package com.example.minerva.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class User(
    @PrimaryKey
    val email: String,
    val name: String,
    val phoneNumber: String,
    val password: String
) {
    override fun toString(): String {
        return "User(email='$email', name='$name', phoneNumber='$phoneNumber', password='$password')"
    }
}