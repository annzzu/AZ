package com.example.az.data.remote


import com.example.az.model.airport.AirportResponse
import com.example.az.model.nationality.NationalityResponse
import com.example.az.model.restriction.RestrictionResponse
import com.example.az.model.travel_plan.TravelPlan
import com.example.az.model.travel_plan.TravelPlanRequest
import com.example.az.model.travel_plan.TravelPlanResponse
import com.example.az.model.travel_plan.TravelPlanSingleResponse
import com.example.az.model.user.User
import com.example.az.model.user.UserResponse
import com.example.az.model.vaccine.VaccineResponse
import com.example.az.utils.ApiEndpoints
import retrofit2.Response
import retrofit2.http.*

// local room
interface ApiService {

    @GET(ApiEndpoints.AIRPORT)
    suspend fun getAirports(): Response<AirportResponse>

    @GET(ApiEndpoints.NATIONALITY)
    suspend fun getNationalities(): Response<NationalityResponse>

    @GET(ApiEndpoints.VACCINE)
    suspend fun getVaccines(): Response<VaccineResponse>

    // Auth
    @POST(ApiEndpoints.LOGIN)
    suspend fun loginUser(@Body user: User): Response<User>

    @POST(ApiEndpoints.SIGNUP)
    suspend fun signupUser(@Body user: User): Response<User>

    @GET(ApiEndpoints.SELF)
    suspend fun getSelf(@Header(ApiEndpoints.TOKEN) token: String): Response<UserResponse>

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

    //postRestriction
    //TBS/GVA?nationality=georgian&vaccine=pfizer&transfer=BER
    @GET(ApiEndpoints.RESTRICTION)
    suspend fun getRestriction(
        @Path("from") from: String ,
        @Path("to") to: String ,
        @Query("nationality") nationality: String ,
        @Query("vaccine") vaccine: String ,
        @Query("transfer") transfer: String
    ): Response<RestrictionResponse>

}

data class ApiResponse(
    val success: Boolean? ,
)