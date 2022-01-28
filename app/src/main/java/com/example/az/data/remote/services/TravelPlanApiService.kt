package com.example.az.data.remote.services

import com.example.az.model.travel_plan.TravelPlanRequest
import com.example.az.model.travel_plan.TravelPlanResponse
import com.example.az.model.travel_plan.TravelPlanSingleResponse
import com.example.az.utils.ApiEndpoints
import retrofit2.Response
import retrofit2.http.*

interface TravelPlanApiService {

    @GET(ApiEndpoints.TRAVEL_PLAN)
    suspend fun getTravelPlan(@Header(ApiEndpoints.TOKEN) token: String): Response<TravelPlanResponse>

    @POST(ApiEndpoints.TRAVEL_PLAN)
    suspend fun createTravelPlan(
        @Header(ApiEndpoints.TOKEN) token: String ,
        @Body travelPlan: TravelPlanRequest
    ): Response<TravelPlanSingleResponse>

    @PUT(ApiEndpoints.TRAVEL_PLAN_ID)
    suspend fun updateTravelPlan(
        @Header(ApiEndpoints.TOKEN) token: String ,
        @Path("id") id: String ,
        @Body travelPlanRequest: TravelPlanRequest
    ): Response<TravelPlanSingleResponse>

    @DELETE(ApiEndpoints.TRAVEL_PLAN_ID)
    suspend fun deleteTravelPlan(
        @Header(ApiEndpoints.TOKEN) token: String ,
        @Path("id") id: String
    ): Response<ApiResponse>

}