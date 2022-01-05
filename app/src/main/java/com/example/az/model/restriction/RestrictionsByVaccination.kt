package com.example.az.model.restriction


data class RestrictionsByVaccination(
    val dozesRequired: Int?,
    val isAllowed: Boolean?,
    val maxDaysAfterVaccination: Int?,
    val minDaysAfterVaccination: Int?
)