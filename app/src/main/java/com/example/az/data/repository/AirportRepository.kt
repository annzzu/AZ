package com.example.az.data.repository

import com.example.az.data.remote.DataSource
import com.example.az.model.airport.AirportResponse
import com.example.az.utils.Resource
import com.example.az.utils.handleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AirportRepository @Inject constructor(private val dataSource: DataSource) {

    suspend fun getAirports(): Flow<Resource<AirportResponse>> {
        return flow {
            emit(handleResponse { dataSource.getAirportData() })
        }.flowOn(Dispatchers.IO)
    }

}