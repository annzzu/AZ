package com.example.az.data.repository.restriction

import com.example.az.data.remote.DataSource
import com.example.az.model.airport.AirportResponse
import com.example.az.model.nationality.NationalityResponse
import com.example.az.model.restriction.RestrictionRequest
import com.example.az.model.restriction.RestrictionResponse
import com.example.az.utils.Resource
import com.example.az.utils.handleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RestrictionRepositoryImpl @Inject constructor(private val dataSource: DataSource) :
    RestrictionRepository {

    override suspend fun getRestriction(restrictionRequest: RestrictionRequest): Flow<Resource<RestrictionResponse>> {
        return flow {
            emit(handleResponse { dataSource.getRestriction(restrictionRequest) })
        }.flowOn(Dispatchers.IO)
    }

}