package com.example.az.data.repository.user

import android.util.Log.d
import com.example.az.data.local.AuthPrefsManager
import com.example.az.data.remote.ApiResponse
import com.example.az.data.remote.DataSource
import com.example.az.model.airport.AirportResponse
import com.example.az.model.travel_plan.TravelPlan
import com.example.az.model.travel_plan.TravelPlanResponse
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

    override suspend fun getTravelPlan(token: String): Flow<Resource<TravelPlanResponse>> {
        return flow {
            emit(handleResponse { dataSource.getTravelPlan(token) })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun createTravelPlan(
        token: String ,
        travelPlan: TravelPlan
    ): Flow<Resource<TravelPlan>> {
        return flow {
            emit(handleResponse { dataSource.createTravelPlan(token, travelPlan) })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun updateTravelPlan(
        token: String ,
        id: String ,
        travelPlan: TravelPlan
    ): Flow<Resource<TravelPlan>> {
        return flow {
            emit(handleResponse { dataSource.updateTravelPlan(token, id, travelPlan) })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun deleteTravelPlan(token: String , id: String): Flow<Resource<ApiResponse>> {
        return flow {
            emit(handleResponse { dataSource.deleteTravelPlan(token, id) })
        }.flowOn(Dispatchers.IO)
    }

}

