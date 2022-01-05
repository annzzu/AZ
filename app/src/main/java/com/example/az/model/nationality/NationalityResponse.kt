package com.example.az.model.nationality


import com.squareup.moshi.Json

data class NationalityResponse(

    val success: Boolean? ,
    @Json(name = "nacionalities")
    val nationalities: List<String>?

)