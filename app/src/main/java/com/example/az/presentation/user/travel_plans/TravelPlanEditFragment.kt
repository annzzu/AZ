package com.example.az.presentation.user.travel_plans

import android.os.Bundle
import android.util.Log
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.az.databinding.FragmentTravelPlanEditBinding
import com.example.az.databinding.FragmentUserHomeBinding
import com.example.az.extensions.*
import com.example.az.model.travel_plan.TravelPlan
import com.example.az.presentation.base.BaseFragment
import com.example.az.presentation.user.UserViewModel
import com.example.az.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


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
                viewModel.source = it.source ?: ""
                viewModel.destination = it.destination ?: ""
                viewModel.date = it.date ?: ""
                tvSource.text = it.source
                tvDestination.text = it.destination
                tvDate.text = it.date?.getDateNextLine()
                btnBack.setOnClickListener {
                    openBack(args.travelPlan!!)
                }
            } ?: run {
                btnBack.gone()
            }
        }
    }

    private fun listeners() {
        binding.btnTravelPlanSave.setOnClickListener {
            checkChoose()
        }
    }

    private fun checkChoose() {
        args.travelPlan?.let {
            if (it.source != viewModel.source || it.destination != viewModel.destination || it.date != viewModel.date) {
                it.source = viewModel.source
                it.destination = viewModel.destination
                it.date = viewModel.date
                btnTravelPlanSave(it , true)
            } else {
                binding.root.showSnackBar(getString(STRINGS.change_data_first))
            }
        } ?: run {
            if (viewModel.source.isBlank() || viewModel.destination.isBlank() || viewModel.date.isBlank()) {
                binding.root.showSnackBar(getString(STRINGS.choose_data_first))
            } else {
                d("testing AZ" , "aq tu shemova")
                btnTravelPlanSave(
                    TravelPlan(
                        source = viewModel.source ,
                        destination = viewModel.destination ,
                        date = viewModel.date
                    ) , false
                )
            }
        }
    }

    //    TravelPlan(
//    source = "TBS" ,
//    destination = "BER" ,
//    date = "2022-07-25T00:00:00"
//    )
    private fun observers() {

    }

    private fun openBack(travelPlan: TravelPlan) {
        findNavController().navigate(
            TravelPlanEditFragmentDirections.actionNavigationTravelPlanEditToNavigationTravelPlan(
                travelPlan
            )
        )
    }

    private fun btnTravelPlanSave(travelPlan: TravelPlan , update: Boolean) {
        if (update) {
            update(travelPlan)
        } else {
            d("testing AZ" , "aq tu shemova 2")
            create(travelPlan)
        }

    }

    private fun create(travelPlan: TravelPlan) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.createTravelPlan(travelPlan)
            viewModel.createPlan.collectLatest {
                when (it) {
                    is Resource.Error -> {
                        d("testing AZ" , "aq tu shemova errrr")
                        binding.root.showSnackBar(it.message!!)
                    }
                    is Resource.Loading -> TODO()
                    is Resource.Success -> {
                        d("testing AZ" , "aq tu shemova 3")
                        d("testing AZ" , "aaxlaaaaa ${it.data!!}")
                        it.data.travelPlan?.let { travelPlan ->
                            openBack(travelPlan)
                        }
                    }
                }
            }
        }
    }

    private fun update(travelPlan: TravelPlan) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.updateTravelPlan(travelPlan)
            viewModel.updatePlan.collectLatest {
                when (it) {
                    is Resource.Error -> binding.root.showSnackBar(it.message!!)
                    is Resource.Loading -> TODO()
                    is Resource.Success -> {
                        d("testing AZ" , "aaxlaaaaa ${it.data!!}")
                        it.data.travelPlan?.let { travelPlan ->
                            openBack(travelPlan)
                        }
                    }
                }
            }
        }
    }
}