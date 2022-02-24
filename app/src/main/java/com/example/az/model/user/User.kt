package com.example.az.model.user


import com.squareup.moshi.Json

data class User(
    val success: Boolean? = null ,
    val data: Data? = null ,
    val token: String? = null ,
    val email: String? = null ,
    val password: String? = null ,
    @Json(name = "_id")
    val id: String? = null ,
) {
    data class Data(
        val nationality: String? ,
        val vaccine: String?
    )

}