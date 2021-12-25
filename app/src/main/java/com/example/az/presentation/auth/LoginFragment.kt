package com.example.az.presentation.auth

import com.example.az.databinding.FragmentLoginBinding
import com.example.az.extensions.STRINGS
import com.example.az.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private lateinit var pagerAdapters: LoginAdapter
    override fun init() {
        initTabFragment()
    }

    private fun initTabFragment() {
        pagerAdapters = LoginAdapter(parentFragmentManager)
        pagerAdapters.apply {
            addFragment(LoginTabFragment() , getString(STRINGS.login))
            addFragment(SignupTabFragment() , getString(STRINGS.signup))
        }
        binding.viewPager.adapter = pagerAdapters
        binding.tabLayout.apply {
            setupWithViewPager(binding.viewPager)

        }
    }

}