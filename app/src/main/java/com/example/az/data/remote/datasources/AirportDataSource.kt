package com.example.az.data.remote.datasources

import com.example.az.data.remote.services.AirportApiService
import javax.inject.Inject

class AirportDataSource @Inject constructor(private val api: AirportApiService) {

    suspend fun getAirports() = api.getAirports()

}