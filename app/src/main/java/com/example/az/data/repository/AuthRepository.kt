package com.example.az.data.repository

import com.example.az.model.user.User
import com.example.az.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun login(user: User) : Flow<Resource<User>>

    suspend fun signup(user: User) : Flow<Resource<User>>

    suspend fun getSelf(token: String) : Flow<Resource<User>>

    fun logout()

}