package com.example.minerva.data.local

import androidx.room.*
import com.example.minerva.data.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUserData(newUser: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    suspend fun getUserByEmail(email: String, password: String): User?

    @Query("UPDATE users SET password=:password where email = :email")
    suspend fun updateUserPassword(password: String, email: String)
}