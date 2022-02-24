package com.example.az.data.repository.user

import com.example.az.data.local.AuthPrefsManager
import com.example.az.data.remote.datasources.TravelPlanDataSource
import com.example.az.data.remote.services.ApiResponse
import com.example.az.model.travel_plan.TravelPlanRequest
import com.example.az.model.travel_plan.TravelPlanResponse
import com.example.az.model.travel_plan.TravelPlanSingleResponse
import com.example.az.utils.Resource
import com.example.az.utils.handleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val dataSource: TravelPlanDataSource ,
    private val autoAuthPrefsManager: AuthPrefsManager
) : UserRepository {

    override suspend fun getTravelPlan(): Flow<Resource<TravelPlanResponse>> {
        return flow {
            emit(handleResponse { dataSource.getTravelPlan(autoAuthPrefsManager.readAuthToken()) })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun createTravelPlan(
        travelPlan: TravelPlanRequest
    ): Flow<Resource<TravelPlanSingleResponse>> {
        return flow {
            emit(Resource.Loading())
            val response =
                dataSource.createTravelPlan(autoAuthPrefsManager.readAuthToken() , travelPlan)
            emit(handleResponse { response })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun updateTravelPlan(
        travelPlan: TravelPlanRequest
    ): Flow<Resource<TravelPlanSingleResponse>> {
        return flow {
            emit(Resource.Loading())
            emit(handleResponse {
                dataSource.updateTravelPlan(
                    autoAuthPrefsManager.readAuthToken() ,
                    travelPlan.id!! ,
                    travelPlan.copy(id=null)
                )
            })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun deleteTravelPlan(id: String): Flow<Resource<ApiResponse>> {
        return flow {
            emit(Resource.Loading())
            emit(handleResponse {
                dataSource.deleteTravelPlan(
                    autoAuthPrefsManager.readAuthToken() ,
                    id
                )
            })
        }.flowOn(Dispatchers.IO)
    }
}

