package com.example.az.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.pow

fun String.emailValid(): Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
fun String.passwordValid(): Boolean = !(this.isEmpty() || this.length > 15 || this.length < 2)
fun String.getName(): String = this.substringBefore("@")

fun String.addInfo(info: String): String = this.plus(info)
fun String.addInfoNationality(info: Boolean? , formatted: String): String =
    if (info != null) this.plus(formatted)
    else this.plus("")

fun String.addString(info: String): String = this.plus(info)
fun String.addNL(info: String): String = this.plus("\n")
fun String.addInfoNL(info: String): String = this.plus(" : ").plus(info).plus("\n")

fun Date.dateToString(): String =
      SimpleDateFormat("yyyy-MM-dd HH:mm:ss" , Locale.getDefault()).format(this).replaceSpaceWithT()


fun Boolean.addInfoBoolean(): String =
    if (this) {
        "True"
    } else {
        "False"
    }
