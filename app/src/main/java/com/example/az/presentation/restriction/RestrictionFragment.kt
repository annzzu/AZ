package com.example.az.presentation.restriction


import androidx.navigation.fragment.navArgs
import com.example.az.databinding.FragmentRestrictionBinding
import com.example.az.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RestrictionFragment :
    BaseFragment<FragmentRestrictionBinding>(FragmentRestrictionBinding::inflate) {

    private val args: RestrictionFragmentArgs by navArgs()

    override fun init() {
        binding.tv.text = "${args.restrictionRequest}"
    }
}