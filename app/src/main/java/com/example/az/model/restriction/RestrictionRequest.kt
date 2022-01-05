package com.example.az.model.restriction

data class RestrictionRequest(
    val from: String ,
    val to: String ,
    val nationality: String? = "" ,
    val vaccine: String? = "" ,
    val transfer: String? = "" ,
)