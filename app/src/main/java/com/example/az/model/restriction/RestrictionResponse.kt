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
        val tbs: TBS? ,
        @Json(name = "RIX")
        val rix: RIX? ,
        @Json(name = "TLL")
        val tll: TLL? ,
        @Json(name = "BER")
        val ber: BER? ,
        @Json(name = "GVA")
        val gva: GVA?
    ) {
        data class TBS(
            val type: String? ,
            val generalRestrictions: GeneralRestrictions? ,
            val restrictionsByVaccination: RestrictionsByVaccination ,
            val restrictionsByNationality: RestrictionsByNationality ,
        )

        data class RIX(
            val type: String?,
            val generalRestrictions: GeneralRestrictions? ,
            val restrictionsByVaccination: RestrictionsByVaccination ,
            val restrictionsByNationality: RestrictionsByNationality ,
        )

        data class TLL(
            val type: String?,
            val generalRestrictions: GeneralRestrictions? ,
            val restrictionsByVaccination: RestrictionsByVaccination ,
            val restrictionsByNationality: RestrictionsByNationality ,
        )

        data class BER(
            val type: String?,
            val generalRestrictions: GeneralRestrictions? ,
            val restrictionsByVaccination: RestrictionsByVaccination ,
            val restrictionsByNationality: RestrictionsByNationality ,
        )

        data class GVA(
            val type: String?,
            val generalRestrictions: GeneralRestrictions? ,
            val restrictionsByVaccination: RestrictionsByVaccination ,
            val restrictionsByNationality: RestrictionsByNationality ,
        )
    }
}