package com.example.az.extensions

import java.util.*
import kotlin.math.pow

fun String.emailValid(): Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
fun String.passwordValid(): Boolean = !(this.isEmpty() || this.length > 15 || this.length < 2)
fun String.getName(): String = this.substringBefore("@")
