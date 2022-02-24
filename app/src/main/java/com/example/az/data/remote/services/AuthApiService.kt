package com.example.az.data.remote.services

import com.example.az.model.user.User
import com.example.az.model.user.UserResponse
import com.example.az.utils.endpoints.ApiEndpoints
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {

    @POST(ApiEndpoints.LOGIN)
    suspend fun loginUser(@Body user: User): Response<User>

    @POST(ApiEndpoints.SIGNUP)
    suspend fun signupUser(@Body user: User): Response<User>

    @GET(ApiEndpoints.SELF)
    suspend fun getSelf(@Header(ApiEndpoints.TOKEN) token: String): Response<UserResponse>

}