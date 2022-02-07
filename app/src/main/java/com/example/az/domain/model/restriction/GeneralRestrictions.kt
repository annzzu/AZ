package com.example.az.domain.model.restriction


data class GeneralRestrictions(
    val allowsBusinessVisit: Boolean,
    val allowsTourists: Boolean ,
    val covidPassportRequired: Boolean ,
    val generalInformation: String ,
    val moreInfoUrl: String ,
    val pcrRequiredForNoneResidents: Boolean ,
    val pcrRequiredForResidents: Boolean
)