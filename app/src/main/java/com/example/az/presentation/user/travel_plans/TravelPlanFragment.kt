package com.example.az.presentation.user.travel_plans

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.az.databinding.FragmentTravelPlanBinding
import com.example.az.extensions.getDateNextLine
import com.example.az.extensions.getTimeNextLine
import com.example.az.extensions.gone
import com.example.az.model.restriction.RestrictionRequest
import com.example.az.presentation.base.BaseFragment
import com.example.az.presentation.restriction.RestrictionAdapter
import com.example.az.presentation.restriction.RestrictionViewModel
import com.example.az.presentation.user.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TravelPlanFragment : BaseFragment<FragmentTravelPlanBinding>(
    FragmentTravelPlanBinding::inflate
) {
    private val args: TravelPlanFragmentArgs by navArgs()
    private val viewModel: UserViewModel by activityViewModels()
    private val restrictionViewModel: RestrictionViewModel by activityViewModels()
    private lateinit var restrictionAdapter: RestrictionAdapter

    override fun init() {
        setInfo()
        listeners()
        observers()
    }

    private fun setInfo() = with(binding) {
        args.travelPlan?.let {
            tvSource.text = it.source
            tvDestination.text = it.destination
            it.date?.getDateNextLine().also { tvDateTime.text = it }

            it.days?.let { days ->
                if (days <= 0) {
                    pbDateLeft.progress = 100
                } else {
                    pbDateLeft.progress = 100 / days
                    pbDateLeft.max = days + 1
                    tvDaysLeft.text = "$days Days Left"
                }
            } ?: run {
                pbDateLeft.gone()
                tvDaysLeft.gone()
            }

            initRestrictions(it.source!! , it.destination!!)
        }
    }

    private fun initRestrictions(from: String , to: String) {

        viewLifecycleOwner.lifecycleScope.launch {
            restrictionViewModel.getRestriction(
                RestrictionRequest(
                    from = from ,
                    to = to ,
                    nationality = "" ,
                    vaccine = ""
                )
            )
        }
    }

    private fun initRV() {

    }

    private fun listeners() = with(binding) {
        btnBack.setOnClickListener {
            openUserHome()
        }
        btnEdit.setOnClickListener {
            btnEdit()
        }
        btnDelete.setOnClickListener {
            btnDelete()
            TODO()
        }
    }

    private fun observers() = with(binding) {
        rvRestrictions
    }

    private fun openUserHome() = findNavController().navigate(
        TravelPlanFragmentDirections.actionNavigationTravelPlanToNavigationUserHome()
    )

    private fun btnDelete() {
        openUserHome()
    }

    private fun btnEdit() = findNavController().navigate(
        TravelPlanFragmentDirections.actionTravelPlanFragmentToTravelPlanEditFragment(args.travelPlan)
    )

}