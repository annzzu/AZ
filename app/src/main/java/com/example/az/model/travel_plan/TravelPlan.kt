package com.example.az.model.travel_plan


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class TravelPlan(
    val success: Boolean?,
    val date: String? ,
    val destination: String? ,
    @Json(name = "_id")
    val id: String? ,
    val source: String?,
    var days: Int?
) : Parcelable