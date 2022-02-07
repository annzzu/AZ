package com.example.az.domain.model.restriction

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RestrictionRequest(
    var from: String?  = null ,
    var to: String? = null ,
    var transfer: String? = "" ,
    var nationality: String? = "" ,
    var vaccine: String? = ""
) : Parcelable