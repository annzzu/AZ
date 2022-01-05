package com.example.az.data.repository.user

import android.util.Log
import com.example.az.model.user.User
import com.example.az.model.user.UserResponse
import com.example.az.utils.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getSelf(token: String): Flow<Resource<UserResponse>>
    suspend fun logout()
    suspend fun getUserInfo(): Flow<User>
    suspend fun getUserToken(): String

}