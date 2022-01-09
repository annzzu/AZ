package com.example.az.presentation.user.travel_plans

import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.az.databinding.FragmentTravelPlanEditBinding
import com.example.az.databinding.FragmentUserHomeBinding
import com.example.az.extensions.getDateNextLine
import com.example.az.extensions.gone
import com.example.az.model.travel_plan.TravelPlan
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

    private fun setInfo() {
        with(binding) {
            args.travelPlan?.let {
                tvSource.text = it.source
                tvDestination.text = it.destination
                tvDate.text = it.date?.getDateNextLine()
                btnBack.setOnClickListener {
                    openBack(args.travelPlan!!)
                }
                btnTravelPlanSave.setOnClickListener {
                    btnTravelPlanSave(args.travelPlan!!)
                }
            } ?: run {
                btnBack.gone()
            }
        }
    }

    private fun listeners() {

    }

    private fun observers() {

    }

    private fun openBack(travelPlan: TravelPlan){
        findNavController().navigate(
            TravelPlanEditFragmentDirections.actionNavigationTravelPlanEditToNavigationTravelPlan(
                travelPlan
            )
        )
    }
    private fun btnTravelPlanSave(travelPlan: TravelPlan){
        openBack(travelPlan)
    }
}