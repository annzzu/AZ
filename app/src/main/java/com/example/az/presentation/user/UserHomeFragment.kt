package com.example.az.presentation.user


import com.example.az.databinding.FragmentUserHomeBinding
import com.example.az.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserHomeFragment : BaseFragment<FragmentUserHomeBinding>(
    FragmentUserHomeBinding::inflate
) {
    override fun init() {
    }
}