package com.example.az.presentation.home

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.az.databinding.FragmentHomeBinding
import com.example.az.extensions.STRINGS
import com.example.az.extensions.getName
import com.example.az.extensions.invisible
import com.example.az.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.example.az.extensions.getRotationAnimation


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun init() {
        listeners()
        setUser()
    }

    private fun listeners() {
        with(binding) {
            cardAbout.setOnClickListener {
                findNavController().navigate(
                    HomeFragmentDirections.actionNavigationHomeToNavigationIntroSlide()
                )
            }
            cardMyTravelPlan.setOnClickListener {
                findNavController().navigate(
                    HomeFragmentDirections.actionNavigationHomeToNavigationTravelPlanEditFragment()
                )
            }
            cardRestrictions.setOnClickListener {
                findNavController().navigate(
                    HomeFragmentDirections.actionNavigationHomeToNavigationRestrictionForm()
                )
            }
            icon.getRotationAnimation()
        }
    }

    private fun setUser() {
        viewLifecycleOwner.lifecycleScope.launch {
            authPrefsManager.preferencesFlow.collectLatest { user ->
                with(binding) {
                    if (user.token.isNullOrBlank()) {
                        cardMyTravelPlan.invisible()
                    }
                    if (user.email.isNullOrBlank()) {
                        tvHello.textSize = 30.0F
                        tvHello.text = getString(STRINGS.hello)
                    } else {
                        tvHello.textSize = 18.0F
                        tvHello.text = getString(STRINGS.hello_next_line).plus(
                            user.email.getName().uppercase()
                        )
                    }
                }
            }
        }
    }

}