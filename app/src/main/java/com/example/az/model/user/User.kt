package com.example.az.model.user


import com.squareup.moshi.Json

data class User(
    val `data`: Data?,
    val email: String?,
    @Json(name = "_id")
    val id: String?
)