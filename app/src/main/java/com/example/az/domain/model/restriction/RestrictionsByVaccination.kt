package com.example.az.domain.model.restriction


data class RestrictionsByVaccination(
    val dozesRequired: Int? ,
    val isAllowed: Boolean? ,
    val maxDaysAfterVaccination: Int? ,
    val minDaysAfterVaccination: Int?
)