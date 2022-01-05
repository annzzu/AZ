package com.example.az.data.remote


import com.example.az.extensions.STRINGS
import com.example.az.model.airport.AirportResponse
import com.example.az.model.nationality.NationalityResponse
import com.example.az.model.restriction.RestrictionResponse
import com.example.az.model.travel_plan.TravelPlan
import com.example.az.model.travel_plan.TravelPlanResponse
import com.example.az.model.user.User
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
    suspend fun getSelf(@Header(ApiEndpoints.TOKEN) token: String): Response<User>

    @GET(ApiEndpoints.TRAVEL_PLAN)
    suspend fun getTravelPlan(@Header(ApiEndpoints.TOKEN) token: String): Response<TravelPlanResponse>

    @POST(ApiEndpoints.TRAVEL_PLAN)
    suspend fun createTravelPlan(
        @Header(STRINGS.x_session_id.toString()) token: String ,
        @Body travelPlan: TravelPlan
    ): Response<ApiResponse>

    @PUT(ApiEndpoints.TRAVEL_PLAN)
    suspend fun updateTravelPlan(
        @Header(ApiEndpoints.TOKEN) token: String ,
        @Path("id") id: String ,
        @Body travelPlan: TravelPlan
    ): Response<ApiResponse>

    @DELETE(ApiEndpoints.TRAVEL_PLAN)
    suspend fun deleteTravelPlan(
        @Header(ApiEndpoints.TOKEN) token: String ,
        @Path("id") id: String
    ): Response<ApiResponse>

    //postRestriction
    //TBS/GVA?nationality=georgian&vaccine=pfizer&transfer=BER
    @GET(ApiEndpoints.RESTRICTIONS)
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