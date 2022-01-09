package com.example.az.model.travel_plan


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class TravelPlan(
    var source: String?,
    val destination: String? ,
    val date: String? ,
    @Json(name = "_id")
    val id: String? = null,
    var days: Int?= null,
    val success: Boolean? = null,
) : Parcelable


data class TravelPlanSingleResponse(
    var source: String?,
    var travelPlan: TravelPlan?,
)