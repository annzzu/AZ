package com.example.az.data.repository.restriction

import android.util.Log
import com.example.az.data.local.AuthPrefsManager
import com.example.az.data.remote.DataSource
import com.example.az.model.airport.AirportResponse
import com.example.az.model.nationality.NationalityResponse
import com.example.az.model.restriction.*
import com.example.az.utils.Resource
import com.example.az.utils.handleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RestrictionRepositoryImpl @Inject constructor(
    private val dataSource: DataSource ,
    private val autoAuthPrefsManager: AuthPrefsManager
) :
    RestrictionRepository {

    override suspend fun getRestriction(restrictionRequest: RestrictionRequest): Flow<Resource<RestrictionResponse>> {
        return flow {
            restrictionRequest.apply {
                vaccine = autoAuthPrefsManager.readVaccine()
                nationality = autoAuthPrefsManager.readNationality()
            }
            emit(handleResponse { dataSource.getRestriction(restrictionRequest) })
        }.flowOn(Dispatchers.IO)
    }
}