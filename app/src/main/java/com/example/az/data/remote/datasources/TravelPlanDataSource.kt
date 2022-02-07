package com.example.az.data.remote.datasources

import com.example.az.data.remote.services.TravelPlanApiService
import com.example.az.domain.model.travel_plan.TravelPlanRequest
import javax.inject.Inject

class TravelPlanDataSource @Inject constructor(private val api: TravelPlanApiService) {

    suspend fun getTravelPlan(token: String) = api.getTravelPlan(token)

    suspend fun createTravelPlan(token: String , travelPlan: TravelPlanRequest) =
        api.createTravelPlan(token , travelPlan)

    suspend fun updateTravelPlan(token: String , id: String , travelPlanRequest: TravelPlanRequest) =
        api.updateTravelPlan(token , id , travelPlanRequest)

    suspend fun deleteTravelPlan(token: String , id: String) =
        api.deleteTravelPlan(token , id)

}