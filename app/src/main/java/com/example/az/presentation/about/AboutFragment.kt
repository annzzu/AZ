package com.example.az.presentation.about

import androidx.navigation.fragment.findNavController
import com.example.az.databinding.FragmentAboutBinding
import com.example.az.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AboutFragment : BaseFragment<FragmentAboutBinding>(
    FragmentAboutBinding::inflate
) {
    override fun init(){
        binding.icPlane.setOnClickListener{
            findNavController().navigate(AboutFragmentDirections.actionNavigationAboutToNavigationAirports())
        }
        binding.icPlane.setOnLongClickListener {
            findNavController().navigate(AboutFragmentDirections.actionNavigationAboutToNavigationNationalities())
            true
        }
    }

}