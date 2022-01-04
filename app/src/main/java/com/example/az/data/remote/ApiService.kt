package com.example.az.data.remote


import com.example.az.extensions.STRINGS
import com.example.az.model.airport.AirportResponse
import com.example.az.model.user.User
import com.example.az.utils.ApiEndpoints
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

// local room
interface ApiService {

    @GET(ApiEndpoints.AIRPORT)
    suspend fun getAirportData(): Response<AirportResponse>

    // Auth
    @POST(ApiEndpoints.LOGIN)
    suspend fun loginUser(@Body user: User): Response<User>

    @POST(ApiEndpoints.SIGNUP)
    suspend fun signupUser(@Body user: User): Response<User>

    @GET(ApiEndpoints.SELF)
    suspend fun getSelf(@Header(STRINGS.x_session_id.toString()) token: String): Response<User>

}


