package com.example.az.presentation.home


import android.util.Log.d
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.az.R
import com.example.az.databinding.FragmentHomeBinding
import com.example.az.presentation.auth.fragment.LoginFragmentDirections
import com.example.az.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun init() {
        listeners()
        setUser()
        d("testing AZ" , "opened")
    }

    private fun listeners() {
        with(binding) {
            cardAbout.setOnClickListener{
                findNavController().navigate(
                    HomeFragmentDirections.actionNavigationHomeToNavigationAbout()
                )
            }
            cardMyTravelPlan.setOnClickListener{
                findNavController().navigate(
                    HomeFragmentDirections.actionNavigationHomeToNavigationRestrictions()
                )
            }
            cardRestrictions.setOnClickListener{
                findNavController().navigate(
                    HomeFragmentDirections.actionNavigationHomeToNavigationRestrictionForm()
                )
            }
        }
    }

    private fun setUser(){
        viewLifecycleOwner.lifecycleScope.launch {
            authPrefsManager.preferencesFlow.collectLatest {
                d("testing AZ home", "$it")
            }
        }
    }

}