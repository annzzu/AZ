package com.example.az.data.remote

import com.example.az.model.restriction.RestrictionRequest
import com.example.az.model.travel_plan.TravelPlan
import com.example.az.model.user.User
import javax.inject.Inject


class DataSource @Inject constructor(private val api: ApiService) {

    //Airport
    suspend fun getAirports() = api.getAirports()

    //Nationality
    suspend fun getNationalities() = api.getNationalities()

    //Vaccine
    suspend fun getVaccines() = api.getVaccines()

    //Auth
    suspend fun loginUser(user: User) = api.loginUser(user)

    suspend fun signupUser(user: User) = api.signupUser(user)

    //User
    suspend fun getSelf(token: String) = api.getSelf(token)

    suspend fun getTravelPlan(token: String) = api.getTravelPlan(token)

    suspend fun createTravelPlan(token: String , travelPlan: TravelPlan) =
        api.createTravelPlan(token , travelPlan)

    suspend fun updateTravelPlan(token: String , id: String , travelPlan: TravelPlan) =
        api.updateTravelPlan(token , id , travelPlan)

    suspend fun deleteTravelPlan(token: String , id: String) =
        api.deleteTravelPlan(token , id)

    //Restriction
    suspend fun getRestriction(restrictionRequest: RestrictionRequest) =
        api.getRestriction(
            from = restrictionRequest.from ,
            to = restrictionRequest.to ,
            nationality = restrictionRequest.nationality ?: "" ,
            vaccine = restrictionRequest.vaccine ?: "" ,
            transfer = restrictionRequest.transfer ?: ""
        )

}