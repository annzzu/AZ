package com.example.az.utils.anumations

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.az.extensions.transformAnimPage


class ZoomOutPageTransformer : ViewPager2.PageTransformer {

    override fun transformPage(view: View , position: Float) = view.transformAnimPage(position)
}