package com.example.az.model.travel_plan


import com.squareup.moshi.Json

data class TravelPlan(
    val success: Boolean?,
    val date: String? ,
    val destination: String? ,
    @Json(name = "_id")
    val id: String? ,
    val source: String?,
)