package com.example.az.domain.model.travel_plan


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
    val transfer: String? = null,
    @Json(name = "travel_date")
    var travelDate: String? = null,
) : Parcelable


data class TravelPlanSingleResponse(
    var success: Boolean?,
    var travelPlan: TravelPlan?,
)

data class TravelPlanRequest(
    @Json(name = "_id")
    var id: String? = null,
    var source: String? = null,
    var destination: String?= null,
    var transfer: String? = null,
    var date: String? = null,
    @Json(name = "travel_date")
    var travelDate: String? = null,
)