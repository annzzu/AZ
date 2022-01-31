package com.example.az.presentation.intro

import android.util.Log.d
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.az.databinding.FragmentIntroSlideBinding
import com.example.az.extensions.DRAWABLES
import com.example.az.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroSlideFragment : BaseFragment<FragmentIntroSlideBinding>(
    FragmentIntroSlideBinding::inflate
) {

    override fun init() {
        initSliderAdapter()
    }

    private fun initSliderAdapter() {
        binding.introSliderViewPager.apply {
            adapter = introSliderAdapter
            registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    setCurrentIndicator(position)
                }
            })

            setPageTransformer(ZoomOutPageTransformer())
        }
        setupIndicators()
        setCurrentIndicator(0)
        initBTNs()
    }

    private fun initBTNs() = with(binding) {
        buttonNext.setOnClickListener {
            if (introSliderViewPager.currentItem + 1 < introSliderAdapter.itemCount) {
                introSliderViewPager.currentItem += 1
            } else {
                openHome()
            }
        }
        textSkipIntro.setOnClickListener {
            openHome()
        }

    }

    private fun openHome() {
        findNavController().apply {
            navigate(
                IntroSlideFragmentDirections.actionIntroSlideFragmentToNavigationHome()
            )
        }
    }

    private fun setupIndicators() {
        val indicators = arrayOfNulls<ImageView>(introSliderAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT ,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(8 , 0 , 8 , 0)

        for (i in indicators.indices) {
            indicators[i] = ImageView(requireContext())
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext() ,
                        DRAWABLES.indicator_inactive
                    )

                )
                this?.layoutParams = layoutParams
            }

            binding.indicatorContainer.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = binding.indicatorContainer.childCount
        for (i in 0 until childCount) {
            val imageView = binding.indicatorContainer[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext() ,
                        DRAWABLES.indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext() ,
                        DRAWABLES.indicator_inactive
                    )
                )
            }
        }
    }

    private val introSliderAdapter = IntroSliderAdapter(
        listOf(
            IntroSlide(
                "AZ\nFor Your Comfort" ,
                "Welcome to Covid Century. Unfortunately we have to follow the Covid Regulations to destroy pandemic." ,
                DRAWABLES.ic_covid_19
            ) ,
            IntroSlide(
                "AZ\nCovid Restrictions" ,
                "Due to the on-going COVID situation we have to obey some rules," +
                        " so AZ is the easiest way to see worldwide Covid Restrictions" ,
                DRAWABLES.ic_plane
            ) ,
            IntroSlide(
                "AZ\nUser Information" ,
                "For saving travel information and then find saved travel route with its Restrictions" ,
                DRAWABLES.ic_user
            )
        )
    )
}