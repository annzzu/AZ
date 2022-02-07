package com.example.az.domain.model.restriction


data class RestrictionsByNationality(
    val data: Data ,
    val type: String
) {
    data class Data(
        val allowsTourists: Boolean?,
        val allowsBusinessVisit: Boolean?,
        val pcrRequired: Boolean?,
        val fastTestRequired: Boolean?,
        val biometricPassportRequired: Boolean?,
        val locatorFormRequired: Boolean?,
        val covidPassportRequired: Boolean?,
    )
}