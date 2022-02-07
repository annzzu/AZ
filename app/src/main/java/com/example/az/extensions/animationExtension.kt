package com.example.az.extensions

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreenViewProvider
import kotlin.math.abs
import kotlin.math.max

const val MIN_SCALE = 0.85f
const val MIN_ALPHA = 0.5f

fun SplashScreenViewProvider.getSplashViewRotationAnimation(): ObjectAnimator {
    val splashScreen = this
    val splashScreenAnimator = ObjectAnimator.ofFloat(
        splashScreen.view ,
        View.TRANSLATION_Y ,
        0f ,
        splashScreen.view.height.toFloat()
    )
    splashScreenAnimator.apply {
        interpolator = AnticipateInterpolator()
        duration = 600L
        start()
        doOnEnd { splashScreen.remove() }
    }
    return splashScreenAnimator
}

fun View.getSplashIconRotationAnimation(): ObjectAnimator {
    val iconAnimator =
        ObjectAnimator.ofFloat(this , View.ROTATION , -360f , 0f)
    iconAnimator.apply {
        duration = 600L
        start()
    }
    return iconAnimator
}

fun View.getRotationAnimation(): ObjectAnimator {
    val animator =
        ObjectAnimator.ofFloat(this , View.ROTATION , -360f , 0f)
    animator.apply {
        start()
        animator.duration = 1600
        animator.repeatCount = ValueAnimator.INFINITE
    }
    return animator
}

fun View.getFabIconAnimation(): ObjectAnimator {
        val animator =
            ObjectAnimator.ofFloat(this , View.TRANSLATION_X , 100f , 0f)
    animator.apply {
        start()
        animator.duration = 200
        animator.repeatCount = ValueAnimator.RESTART
    }
    return animator
}

fun View.getZoomAnimation(): ObjectAnimator {
    val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 3f)
    val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 3f)
    val animator = ObjectAnimator.ofPropertyValuesHolder(
        this, scaleX, scaleY)
    animator.apply {
        duration = 1600
        repeatCount = ValueAnimator.INFINITE
        start()
    }
    return animator
}

fun View.transformAnimPage(position: Float) {
    this.apply {
        val pageWidth = width
        val pageHeight = height
        when {
            position < -1 -> {
                alpha = 0f
            }
            position <= 1 -> {
                val scaleFactor = max(MIN_SCALE , 1 - abs(position))
                val vertMargin = pageHeight * (1 - scaleFactor) / 2
                val horMargin = pageWidth * (1 - scaleFactor) / 2
                translationX = if (position < 0) {
                    horMargin - vertMargin / 2
                } else {
                    horMargin + vertMargin / 2
                }

                scaleX = scaleFactor
                scaleY = scaleFactor

                alpha = (MIN_ALPHA +
                        (((scaleFactor - MIN_SCALE) / (1 - MIN_SCALE)) * (1 - MIN_ALPHA)))
            }
            else -> {
                alpha = 0f
            }
        }
    }
}