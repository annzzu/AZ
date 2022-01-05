package com.example.az.model.airport


data class AirportResponse(
    val airports: List<Airport> = listOf() ,
    val success: Boolean?
)