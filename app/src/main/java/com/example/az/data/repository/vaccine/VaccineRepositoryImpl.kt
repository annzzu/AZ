package com.example.az.data.repository.vaccine

import com.example.az.data.remote.datasources.VaccineDataSource
import com.example.az.model.vaccine.VaccineResponse
import com.example.az.utils.Resource
import com.example.az.utils.handleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class VaccineRepositoryImpl @Inject constructor(private val dataSource: VaccineDataSource) :
    VaccineRepository {

    override suspend fun getVaccines(): Flow<Resource<VaccineResponse>> {
        return flow {
            emit(handleResponse { dataSource.getVaccines() })
        }.flowOn(Dispatchers.IO)
    }

}