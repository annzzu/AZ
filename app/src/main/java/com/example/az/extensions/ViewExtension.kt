package com.example.az.extensions

import android.content.Context
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.az.R
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


fun View.showSnackBar(title: String) =
    Snackbar.make(this , title , Snackbar.LENGTH_SHORT).show()

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken , 0)
}

fun View.invisible() = View.INVISIBLE.also { visibility = it }

fun View.visible() = View.VISIBLE.also { visibility = it }

fun View.gone() = View.GONE.also { visibility = it }

fun String.getTime(boolean: Boolean) =
    if (boolean) {
        this.substringBefore('T')
    } else {
        this.substringAfter('T')
    }
fun String.replaceSpaceWithT() = this.replace(" ", "T")

fun String.getTimeNextLine() =
    this.replace("-" , "\n")

fun String.getDateNextLine() =
    this.replace("T" , "\n").replace("-" , "/")

fun String.reformatDate() =
    this.replace("\n" , "T").replace("/" , "-")

fun Int.putFirstZero() = this.toString().padStart(2, '0')
fun Int.plusOnePutFirstZero() = this.plus(1).toString().padStart(2, '0')

@RequiresApi(Build.VERSION_CODES.O)
fun String.getDuration(): Long {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val modelDate = LocalDate.parse(this.getTime(true) , formatter)
    val now = LocalDate.now(ZoneId.of("+4"))
    return ChronoUnit.DAYS.between(now , modelDate)
}


fun ImageView.setImageUrl(url: String?) {
    if (!url.isNullOrEmpty())
        Glide.with(context).load(url).placeholder(R.drawable.ic_launcher_foreground).into(this)
    else
        setImageResource(R.drawable.ic_launcher_foreground)
}

typealias STRINGS = R.string
typealias DRAWABLES = R.drawable
typealias COLORS = R.color
