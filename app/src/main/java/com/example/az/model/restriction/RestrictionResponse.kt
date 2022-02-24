package com.example.az.model.restriction


import com.squareup.moshi.Json

data class RestrictionResponse(
    val success: Boolean? ,
    @Json(name = "restricions")
    val restrictions: Restrictions? ,
    var restrictionList: List<RestrictionKotlin>? ,
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

     init {
        val listRestrictions: MutableList<RestrictionKotlin> = mutableListOf()
        restrictions?.apply {
            tbs?.let {
                listRestrictions += convertRestrictionIntoRestrictionKotlin("TBS" , it)
            }
            gva?.let {
                listRestrictions += convertRestrictionIntoRestrictionKotlin("GVA" , it)
            }
            ber?.let {
                listRestrictions += convertRestrictionIntoRestrictionKotlin("BER" , it)
            }
            tll?.let {
                listRestrictions += convertRestrictionIntoRestrictionKotlin("TLL" , it)
            }
            rix?.let {
                listRestrictions += convertRestrictionIntoRestrictionKotlin("RIX" , it)
            }
        }
        restrictionList = listRestrictions
    }

    private fun convertRestrictionIntoRestrictionKotlin(
        code: String ,
        data: RestrictionKotlin
    ): RestrictionKotlin {
        data.code = code
        return data
    }
}

data class RestrictionKotlin(
    var code: String? ,
    val type: String? ,
    val generalRestrictions: GeneralRestrictions? ,
    val restrictionsByVaccination: RestrictionsByVaccination? ,
    val restrictionsByNationality: List<RestrictionsByNationality>? ,
    var expand: Boolean = true
)