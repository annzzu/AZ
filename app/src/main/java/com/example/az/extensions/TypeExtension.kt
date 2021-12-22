package com.example.az.extensions

import java.util.*
import kotlin.math.pow

fun Int.pow(): Int = this.toDouble().pow(2.0).toInt()
fun String.emailValid(): Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
fun String.titleValid(): Boolean = this.isEmpty() || this.length < 5 || this.length > 30
fun String.descriptionValid(): Boolean = this.isEmpty() || this.length < 32 || this.length > 300

fun Long.getTime(): String? =
    if (this == null) null else Date(this).hours.toString().plus(":").plus(Date(this).minutes)
