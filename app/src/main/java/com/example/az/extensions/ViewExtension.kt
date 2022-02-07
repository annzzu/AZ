package com.example.az.extensions

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.az.R
import com.google.android.material.snackbar.Snackbar


fun View.showSnackBar(title: String) =
    Snackbar
        .make(this , title , Snackbar.LENGTH_SHORT)
        .setBackgroundTint(resources.getColor(COLORS.green_l))
        .setTextColor(resources.getColor(COLORS.white))
        .show()

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken , 0)
}

fun View.invisible() = View.INVISIBLE.also { visibility = it }

fun View.visible() = View.VISIBLE.also { visibility = it }

fun View.gone() = View.GONE.also { visibility = it }

fun ImageView.setImageUrl(url: String?) {
    if (!url.isNullOrEmpty())
        Glide.with(context).load(url).placeholder(R.drawable.ic_launcher_foreground).into(this)
    else
        setImageResource(R.drawable.ic_launcher_foreground)
}

// typealias
typealias STRINGS = R.string
typealias DRAWABLES = R.drawable
typealias COLORS = R.color
typealias IDS = R.id
