package com.example.az.model.restriction


import com.squareup.moshi.Json

data class RestrictionsByNationality(
    @Json(name = "`data`")
    val data: List<Data>?,
    val type: String?
){
    data class Data(
        val allowsBusinessVisit: Boolean?,
        val allowsTourists: Boolean?,
        val biometricPassportRequired: Boolean?,
        val covidPassportRequired: Boolean?,
        val fastTestRequired: Boolean?,
        val locatorFormRequired: Boolean?,
        val pcrRequired: Boolean?
    )
}