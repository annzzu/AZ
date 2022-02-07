package com.example.az.data.repository.airport

import com.example.az.data.remote.datasources.AirportDataSource
import com.example.az.domain.model.airport.AirportResponse
import com.example.az.utils.Resource
import com.example.az.utils.handleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AirportRepositoryImpl @Inject constructor(private val dataSource: AirportDataSource) :
    AirportRepository {

    override suspend fun getAirports(): Flow<Resource<AirportResponse>> {
        return flow {
            emit(Resource.Loading())
            emit(handleResponse { dataSource.getAirports() })
        }.flowOn(Dispatchers.IO)
    }

}