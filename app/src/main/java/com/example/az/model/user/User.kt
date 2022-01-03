package com.example.az.model.user


import com.squareup.moshi.Json

data class User(
    val data: Data? = null,
    val token: String? = null ,
    val email: String? =null,
    val password: String? =null,
    @Json(name = "_id")
    val id: String? =null,
) {
    data class Data(
        val nationality: String? ,
        val vaccine: String?
    )

}

data class UserFormState(
    val emailError: Int? = null ,
    val passwordError: Int? = null ,
    val isDataValid: Boolean = false
)