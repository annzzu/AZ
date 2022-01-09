package com.example.az.presentation.user.travel_plans

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.az.databinding.FragmentTravelPlanEditBinding
import com.example.az.databinding.FragmentUserHomeBinding
import com.example.az.presentation.base.BaseFragment
import com.example.az.presentation.user.UserViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TravelPlanEditFragment : BaseFragment<FragmentTravelPlanEditBinding>(
    FragmentTravelPlanEditBinding::inflate
) {
    private val args: TravelPlanFragmentArgs by navArgs()
    private val viewModel: UserViewModel by activityViewModels()


    override fun init() {
        setInfo()
        listeners()
        observers()
    }

    private fun setInfo(){
        with(binding){
            tvSource.text = args.travelPlan.source
            tvDestination.text = args.travelPlan.destination
        }
    }

    private fun listeners(){
        args.tr
    }
    private fun observers(){

    }
}