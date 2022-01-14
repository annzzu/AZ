package com.example.az.model.travel_plan


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class TravelPlan(
    var source: String?,
    var destination: String? ,
    var date: String? ,
    @Json(name = "_id")
    var id: String? = null,
    var days: Int?= null,
    val success: Boolean? = null,
) : Parcelable


data class TravelPlanSingleResponse(
    var success: Boolean?,
    var travelPlan: TravelPlan?,
)