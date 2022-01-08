package com.example.az.model.restriction


import com.squareup.moshi.Json

data class RestrictionsByNationality(
    @Json(name = "`data`")
    val data: List<Data>? ,
    val type: String?
) {
    data class Data(
        val allowsTourists: Boolean? ,
        val allowsBusinessVisit: Boolean? ,
    )
}