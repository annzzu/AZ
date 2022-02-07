package com.example.az.domain.model.airport


data class AirportResponse(
    val airports: List<Airport> = listOf() ,
    val success: Boolean?
)