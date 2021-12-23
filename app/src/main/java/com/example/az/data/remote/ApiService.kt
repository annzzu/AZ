package com.example.az.data.remote


import com.example.az.model.airport.AirportResponse
import com.example.az.utils.ApiEndpoints
import retrofit2.Response
import retrofit2.http.GET

// local room
interface ApiService {

    @GET(ApiEndpoints.AIRPORT)
    suspend fun getAirportData(): Response<AirportResponse>

}


