package com.example.az.data.repository.vaccine

import com.example.az.data.remote.DataSource
import com.example.az.model.airport.AirportResponse
import com.example.az.model.nationality.NationalityResponse
import com.example.az.model.vaccine.VaccineResponse
import com.example.az.utils.Resource
import com.example.az.utils.handleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class VaccineRepositoryImpl @Inject constructor(private val dataSource: DataSource) :
    VaccineRepository {

    override suspend fun getVaccines(): Flow<Resource<VaccineResponse>> {
        return flow {
            emit(Resource.Loading())
            emit(handleResponse { dataSource.getVaccines() })
        }.flowOn(Dispatchers.IO)
    }

}