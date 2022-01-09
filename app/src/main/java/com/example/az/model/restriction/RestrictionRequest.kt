package com.example.az.model.restriction

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RestrictionRequest(
    val from: String ,
    val to: String ,
    val transfer: String? = "" ,
    var nationality: String? = "" ,
    var vaccine: String? = ""
) : Parcelable