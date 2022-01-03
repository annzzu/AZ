package com.example.az.data.remote

import com.example.az.model.user.User
import javax.inject.Inject


class DataSource @Inject constructor(private val api: ApiService) {

    //Airport
    suspend fun getAirportData() = api.getAirportData()


    //Auth
    suspend fun loginUser(user: User) = api.loginUser(user)
    suspend fun signupUser(user: User) = api.signupUser(user)
    suspend fun getSelf(token: String) = api.getSelf(token)

}