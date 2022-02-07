package com.example.az.domain.model.travel_plan


data class TravelPlanResponse(
    val success: Boolean? ,
    val travelPlans: List<TravelPlan>?
)