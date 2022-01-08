package com.example.az.model.restriction

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RestrictionRequest(
    val from: String ,
    val to: String ,
    val transfer: String? = "" ,
    val nationality: String? = "" ,
    val vaccine: String? = ""
) : Parcelable