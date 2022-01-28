package com.example.az.data.repository.user

import com.example.az.data.remote.services.ApiResponse
import com.example.az.model.travel_plan.TravelPlanRequest
import com.example.az.model.travel_plan.TravelPlanResponse
import com.example.az.model.travel_plan.TravelPlanSingleResponse
import com.example.az.utils.Resource
import kotlinx.coroutines.flow.Flow


interface UserRepository {

    suspend fun getTravelPlan(): Flow<Resource<TravelPlanResponse>>
    suspend fun createTravelPlan(travelPlan: TravelPlanRequest): Flow<Resource<TravelPlanSingleResponse>>
    suspend fun updateTravelPlan(travelPlan: TravelPlanRequest): Flow<Resource<TravelPlanSingleResponse>>
    suspend fun deleteTravelPlan(id: String): Flow<Resource<ApiResponse>>

}