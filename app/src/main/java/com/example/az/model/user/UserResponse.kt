package com.example.az.model.user


import com.squareup.moshi.Json

data class UserResponse(
    val success: Boolean?,
    val user: User?
)