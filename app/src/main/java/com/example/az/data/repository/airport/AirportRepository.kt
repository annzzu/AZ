package com.example.az.data.repository.airport

import com.example.az.domain.model.airport.AirportResponse
import com.example.az.utils.Resource
import kotlinx.coroutines.flow.Flow


interface AirportRepository {

    suspend fun getAirports(): Flow<Resource<AirportResponse>>

}