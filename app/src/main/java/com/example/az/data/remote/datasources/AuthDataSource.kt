package com.example.az.data.remote.datasources

import com.example.az.data.remote.services.AuthApiService
import com.example.az.domain.model.user.User
import javax.inject.Inject

class AuthDataSource @Inject constructor(private val api: AuthApiService) {

    //Auth
    suspend fun loginUser(user: User) = api.loginUser(user)

    suspend fun signupUser(user: User) = api.signupUser(user)

    //User
    suspend fun getSelf(token: String) = api.getSelf(token)
}