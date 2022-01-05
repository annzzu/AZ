package com.example.az.presentation.home


import android.util.Log.d
import com.example.az.databinding.FragmentHomeBinding
import com.example.az.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun init() {
        d("testing AZ", "opened")
    }
}