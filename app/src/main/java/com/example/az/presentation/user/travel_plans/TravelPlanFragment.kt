package com.example.az.presentation.user.travel_plans

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.az.databinding.FragmentTravelPlanBinding
import com.example.az.extensions.getDateNextLine
import com.example.az.extensions.getTimeNextLine
import com.example.az.extensions.gone
import com.example.az.presentation.base.BaseFragment
import com.example.az.presentation.user.UserViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TravelPlanFragment : BaseFragment<FragmentTravelPlanBinding>(
    FragmentTravelPlanBinding::inflate
) {
    private val args: TravelPlanFragmentArgs by navArgs()
    private val viewModel: UserViewModel by activityViewModels()


    override fun init() {
        setInfo()
        listeners()
        observers()
    }

    private fun setInfo() = with(binding) {
        tvSource.text = args.travelPlan.source
        tvDestination.text = args.travelPlan.destination
        args.travelPlan.date?.getDateNextLine().also { tvDateTime.text = it }

        args.travelPlan.days?.let {
            if (it <= 0) {
                pbDateLeft.progress = 100
            } else {
                pbDateLeft.progress = 100 / it
                pbDateLeft.max = it + 1
                tvDaysLeft.text = "$it Days Left"
            }
        } ?: run {
            pbDateLeft.gone()
            tvDaysLeft.gone()
        }
    }

    private fun listeners() = with(binding) {
        btnBack.setOnClickListener {
            findNavController().navigate(
                TravelPlanFragmentDirections.actionNavigationTravelPlanToNavigationUserHome()
            )
        }
        btnEdit.setOnClickListener {
            findNavController().navigate(
                TravelPlanFragmentDirections.actionTravelPlanFragmentToTravelPlanEditFragment(args.travelPlan)
            )
        }
        btnDelete.setOnClickListener {
            findNavController().navigate(
                TravelPlanFragmentDirections.actionNavigationTravelPlanToNavigationUserHome()
            )
        }
    }

    private fun observers() = with(binding) {

    }

}