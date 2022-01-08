package com.example.az.model.restriction


import com.squareup.moshi.Json

data class RestrictionResponse(
    val success: Boolean? ,
    @Json(name = "restricions")
    val restrictions: Restrictions? ,
) {
    // its strange to have model like this, ask backend
    data class Restrictions(
        @Json(name = "TBS")
        var tbs: RestrictionKotlin? ,
        @Json(name = "RIX")
        var rix: RestrictionKotlin? ,
        @Json(name = "TLL")
        var tll: RestrictionKotlin? ,
        @Json(name = "BER")
        var ber: RestrictionKotlin? ,
        @Json(name = "GVA")
        var gva: RestrictionKotlin?
    )
}

data class RestrictionKotlin(
    var code: String? ,
    val type: String? ,
    val generalRestrictions: GeneralRestrictions? ,
    val restrictionsByVaccination: RestrictionsByVaccination? ,
    val restrictionsByNationality: List<RestrictionsByNationality>? ,
)