package com.example.az.presentation.user.travel_plans

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.az.databinding.FragmentTravelPlanBinding
import com.example.az.extensions.*
import com.example.az.model.restriction.RestrictionRequest
import com.example.az.model.travel_plan.TravelPlan
import com.example.az.presentation.base.BaseFragment
import com.example.az.presentation.restriction.adapter.RestrictionAdapter
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
    private val restrictionViewModel: RestrictionViewModel by viewModels()
    private lateinit var restrictionAdapter: RestrictionAdapter

    override fun init() {
        setInfo()
        listeners()
    }

    private fun setInfo() = with(binding) {
        args.travelPlan?.let { it ->
            initRV()
            tvSource.text = it.source
            tvDestination.text = it.destination
            if (!it.transfer.isNullOrBlank()) {
                tvTransfer.visible()
                tvTransfer.text = getString(STRINGS.transfer_x , it.transfer)
            }
            it.travelDate?.let {
                it.getDateNextLine().also { tvDateTime.text = it }
                tvDateTime.visible()
            }
            it.days?.let { days ->
                if (days <= 0) {
                    pbDateLeft.progress = 100
                } else {
                    pbDateLeft.progress = 100 / days
                    pbDateLeft.max = days + 1
                    tvDaysLeft.text = getString(STRINGS.x_days_left , days)
                }
            } ?: run {
                pbDateLeft.invisible()
                tvDaysLeft.invisible()
            }
            initRestrictions(it)
        }
    }

    private fun initRestrictions(travelPlan: TravelPlan) {
        viewLifecycleOwner.lifecycleScope.launch {
            restrictionViewModel.getRestriction(
                RestrictionRequest(
                    from = travelPlan.source ,
                    to = travelPlan.destination ,
                    transfer = travelPlan.transfer ,
                )
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            restrictionViewModel.restriction.collectLatest {
                when (it) {
                    is Resource.Error -> {
                        binding.tvNothingFound.visible()
                        binding.tvNothingFound.text = getString(STRINGS.error)
                        binding.root.showSnackBar(it.message!!)
                    }
                    is Resource.Loading -> {
                        binding.progressBar.visible()
                    }
                    is Resource.Success -> {
                        binding.progressBar.invisible()
                        it.data?.restrictionList?.let { value ->
                            restrictionAdapter.submitList(value)
                            binding.tvNothingFound.invisible()
                        } ?: run {
                            binding.tvNothingFound.visible()
                        }
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
            viewModel.delete.collectLatest {
                when (it) {
                    is Resource.Error -> {
                        binding.progressBar.visible()
                        binding.root.showSnackBar(it.message!!)
                    }
                    is Resource.Loading -> {
                        binding.progressBar.visible()
                    }
                    is Resource.Success -> {
                        binding.progressBar.invisible()
                        it.data?.success?.let {
                            binding.root.showSnackBar(getString(STRINGS.success))
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