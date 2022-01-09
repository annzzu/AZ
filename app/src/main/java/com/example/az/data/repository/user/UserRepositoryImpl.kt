package com.example.az.data.repository.user

import android.util.Log.d
import com.example.az.data.local.AuthPrefsManager
import com.example.az.data.remote.ApiResponse
import com.example.az.data.remote.DataSource
import com.example.az.model.airport.AirportResponse
import com.example.az.model.travel_plan.TravelPlan
import com.example.az.model.travel_plan.TravelPlanResponse
import com.example.az.model.travel_plan.TravelPlanSingleResponse
import com.example.az.model.user.User
import com.example.az.model.user.UserResponse
import com.example.az.utils.Resource
import com.example.az.utils.handleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val dataSource: DataSource ,
    private val autoAuthPrefsManager: AuthPrefsManager
) : UserRepository {

    override suspend fun getTravelPlan(): Flow<Resource<TravelPlanResponse>> {
        return flow {
            emit(handleResponse { dataSource.getTravelPlan( autoAuthPrefsManager.readAuthToken()) })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun createTravelPlan(
        travelPlan: TravelPlan
    ): Flow<Resource<TravelPlanSingleResponse>> {
        return flow {
            val response = dataSource.createTravelPlan(autoAuthPrefsManager.readAuthToken(), travelPlan)
            response
            emit(handleResponse {response })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun updateTravelPlan(
        travelPlan: TravelPlan
    ): Flow<Resource<TravelPlanSingleResponse>> {
        return flow {
            emit(handleResponse { dataSource.updateTravelPlan(autoAuthPrefsManager.readAuthToken(), travelPlan.id!!, travelPlan) })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun deleteTravelPlan( id: String): Flow<Resource<ApiResponse>> {
        return flow {
            emit(handleResponse { dataSource.deleteTravelPlan(autoAuthPrefsManager.readAuthToken(), id) })
        }.flowOn(Dispatchers.IO)
    }

}

