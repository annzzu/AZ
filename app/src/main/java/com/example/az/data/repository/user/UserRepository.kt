package com.example.az.data.repository.user

import android.util.Log
import com.example.az.data.remote.ApiResponse
import com.example.az.model.travel_plan.TravelPlan
import com.example.az.model.travel_plan.TravelPlanResponse
import com.example.az.model.user.User
import com.example.az.model.user.UserResponse
import com.example.az.utils.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface UserRepository {

    suspend fun getTravelPlan(): Flow<Resource<TravelPlanResponse>>
    suspend fun createTravelPlan(travelPlan: TravelPlan): Flow<Resource<TravelPlan>>
    suspend fun updateTravelPlan(
        id: String ,
        travelPlan: TravelPlan
    ): Flow<Resource<TravelPlan>>

    suspend fun deleteTravelPlan(id: String): Flow<Resource<ApiResponse>>

}