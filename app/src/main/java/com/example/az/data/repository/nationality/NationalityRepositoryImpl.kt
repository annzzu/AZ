package com.example.az.data.repository.nationality

import com.example.az.data.remote.DataSource
import com.example.az.model.airport.AirportResponse
import com.example.az.model.nationality.NationalityResponse
import com.example.az.utils.Resource
import com.example.az.utils.handleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NationalityRepositoryImpl @Inject constructor(private val dataSource: DataSource) :
    NationalityRepository {

    override suspend fun getNationalities(): Flow<Resource<NationalityResponse>> {
        return flow {
            emit(Resource.Loading())
            emit(handleResponse { dataSource.getNationalities() })
        }.flowOn(Dispatchers.IO)
    }

}