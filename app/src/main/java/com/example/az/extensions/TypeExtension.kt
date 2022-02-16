package com.example.az.extensions

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

// String
fun String.emailValid(): Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.passwordValid(): Boolean = !(this.isEmpty() || this.length > 15 || this.length < 2)

fun String.getName(): String = this.substringBefore("@")

fun String.addInfoNationality(info: Boolean? , formatted: String): String =
    if (info != null) this.plus(formatted)
    else this.plus("")

fun String.replaceSpaceWithT() = this.replace(" " , "T")

fun String.getTimeNextLine() =
    this.replace("-" , "\n")

fun String.getDateNextLine() =
    this.replace("T" , "\n").replace("-" , "/")

fun String.getTime(boolean: Boolean) =
    if (boolean) {
        this.substringBefore('T')
    } else {
        this.substringAfter('T')
    }

fun String.getTimeArray() = this.getTime(true).split("-").map { it.toInt() }

fun String.containsAirport(airport: String) = this.split(",").contains(airport)

@RequiresApi(Build.VERSION_CODES.O)
fun String.getDuration(date: String?): Int {
    val thisDate =
        LocalDate.of(this.getTimeArray()[0] , this.getTimeArray()[1] , this.getTimeArray()[2])
    date?.let {
        val secondDate =
            LocalDate.of(date.getTimeArray()[0] , date.getTimeArray()[1] , date.getTimeArray()[2])
        return (secondDate.toEpochDay() - thisDate.toEpochDay()).toInt()
    }
    val now = LocalDate.now()
    return (now.toEpochDay() - thisDate.toEpochDay()).toInt()
}

// Int
fun Int.putFirstZero() = this.toString().padStart(2 , '0')
fun Int.plusOnePutFirstZero() = this.plus(1).toString().padStart(2 , '0')

// Boolean
fun Boolean.booleanToYN(): String = if (this) "Yes" else "No"

// Date
fun Date.dateToString(): String =
    SimpleDateFormat("yyyy-MM-dd HH:mm:ss" , Locale.getDefault()).format(this).replaceSpaceWithT()


fun AppCompatTextView.deleteText() {
    this.text = ""
}



