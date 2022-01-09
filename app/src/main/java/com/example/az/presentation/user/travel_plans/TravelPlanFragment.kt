package com.example.az.presentation.user.travel_plans

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.az.databinding.FragmentTravelPlanBinding
import com.example.az.extensions.*
import com.example.az.model.restriction.RestrictionRequest
import com.example.az.presentation.base.BaseFragment
import com.example.az.presentation.restriction.RestrictionAdapter
import com.example.az.presentation.restriction.RestrictionViewModel
import com.example.az.presentation.user.UserViewModel
import com.example.az.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
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
    }

    private fun setInfo() = with(binding) {
        args.travelPlan?.let {
            initRV()
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
                )
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            restrictionViewModel.restriction.collectLatest {
                when (it) {
                    is Resource.Error -> binding.root.showSnackBar(it.message!!)
                    is Resource.Loading -> {
                        Log.d("testing AZ" , "Loading")
                        TODO()
                    }
                    is Resource.Success -> {
                        Log.d("testing AZ" , "ar vici ra xdeba \n ${it}")
//                        restrictionAdapter.submitList(it.data!!.restrictions!!)
//                        it.data?.restrictions?.let { restrictionList ->
//                            if (list.isNotEmpty()) {
//                                d("testing AZ" , "ar vici ra xdeba \n ${list.size}")
//                            }
//                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            restrictionViewModel.restrictionList.collectLatest {
                Log.d("testing AZ" , "ar vici ra xdeba arada vici \n $it")
                it.let { list ->
                    restrictionAdapter.submitList(list)
                    if (list.isNotEmpty()) {
                        Log.d("testing AZ" , "ar vici ra xdeba \n ${list.size}")
                    } else {
                        binding.tvNothingFound.visible()
                    }
                }
            }
        }
    }

    private fun initRV() = with(binding) {
        rvRestrictions.apply {
            restrictionAdapter = RestrictionAdapter()
            adapter = restrictionAdapter
            layoutManager =
                LinearLayoutManager(view?.context , LinearLayoutManager.VERTICAL , false)
        }
        restrictionAdapter.click = {
//            openTravelPlanDetails(it)
            Log.d("testing AZ" , "davawire")
        }
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
        }
    }

    private fun openUserHome() = findNavController().navigate(
        TravelPlanFragmentDirections.actionNavigationTravelPlanToNavigationUserHome()
    )

    private fun btnDelete() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.deleteTravelPlan(args.travelPlan!!.id!!)
            viewModel.delete.collectLatest { it ->
                when (it) {
                    is Resource.Error -> binding.root.showSnackBar(it.message!!)
                    is Resource.Loading -> TODO()
                    is Resource.Success -> {
                        Log.d("testing AZ" , "delete ${it.data!!}")
                        it.data.success?.let {
                            binding.root.showSnackBar("success")
                            openUserHome()
                        }
                    }
                }
            }
        }
    }

    private fun btnEdit() = findNavController().navigate(
        TravelPlanFragmentDirections.actionTravelPlanFragmentToTravelPlanEditFragment(args.travelPlan)
    )

}