package com.example.az.data.remote.services

import com.example.az.domain.model.airport.AirportResponse
import com.example.az.utils.endpoints.ApiEndpoints
import retrofit2.Response
import retrofit2.http.GET


interface AirportApiService {

    @GET(ApiEndpoints.AIRPORT)
    suspend fun getAirports(): Response<AirportResponse>

}