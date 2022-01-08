package com.example.az.presentation.user.travel_plans

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.az.databinding.FragmentTravelPlanBinding
import com.example.az.presentation.base.BaseFragment
import com.example.az.presentation.user.UserViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TravelPlanFragment : BaseFragment<FragmentTravelPlanBinding>(
    FragmentTravelPlanBinding::inflate
) {
    private val args: TravelPlanFragmentArgs by navArgs()
    private val viewModel: UserViewModel by activityViewModels()

}