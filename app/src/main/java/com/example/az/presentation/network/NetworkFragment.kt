package com.example.az.presentation.network

import com.example.az.databinding.ItemIntroSliderBinding
import com.example.az.extensions.DRAWABLES
import com.example.az.extensions.STRINGS
import com.example.az.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NetworkFragment : BaseFragment<ItemIntroSliderBinding>(ItemIntroSliderBinding::inflate){
    override fun init() {
        with (binding){
            tvTitle.text = getString(STRINGS.network_oops)
            tvDescription.text =  getString(STRINGS.network_connection_false)
            icon.setImageResource(DRAWABLES.ic_wifi)
        }
    }
}