package com.example.az.model.airport

data class Airport(
    val city: String? ,
    val code: String? ,
    val country: String?
)

enum class AirportChooseType {
    FROM, TO, TRANSITION
}
