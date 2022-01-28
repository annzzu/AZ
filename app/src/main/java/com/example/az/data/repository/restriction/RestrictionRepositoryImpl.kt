package com.example.az.data.repository.restriction

import com.example.az.data.local.AuthPrefsManager
import com.example.az.data.remote.datasources.RestrictionDataSource
import com.example.az.model.restriction.*
import com.example.az.utils.Resource
import com.example.az.utils.handleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RestrictionRepositoryImpl @Inject constructor(
    private val dataSource: RestrictionDataSource ,
    private val autoAuthPrefsManager: AuthPrefsManager
) :
    RestrictionRepository {

    override suspend fun getRestriction(restrictionRequest: RestrictionRequest): Flow<Resource<RestrictionResponse>> {
        return flow {
            emit(Resource.Loading())
            restrictionRequest.apply {
                vaccine = autoAuthPrefsManager.readVaccine()
                nationality = autoAuthPrefsManager.readNationality()
            }
            emit(handleResponse { dataSource.getRestriction(restrictionRequest) })
        }.flowOn(Dispatchers.IO)
    }
}