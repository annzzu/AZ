package com.example.az.presentation.user.travel_plans

import android.os.Build
import android.util.Log.d
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.az.databinding.FragmentTravelPlanBinding
import com.example.az.extensions.*
import com.example.az.domain.model.restriction.RestrictionRequest
import com.example.az.domain.model.travel_plan.TravelPlan
import com.example.az.presentation.base.BaseFragment
import com.example.az.presentation.restriction.adapter.RestrictionAdapter
import com.example.az.presentation.restriction.RestrictionViewModel
import com.example.az.presentation.user.UserViewModel
import com.example.az.utils.Resource
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.abs


@AndroidEntryPoint
class TravelPlanFragment : BaseFragment<FragmentTravelPlanBinding>(
    FragmentTravelPlanBinding::inflate
) {
    private val args: TravelPlanFragmentArgs by navArgs()
    private val viewModel: UserViewModel by activityViewModels()
    private val restrictionViewModel: RestrictionViewModel by viewModels()
    private lateinit var restrictionAdapter: RestrictionAdapter

    override fun initOnCreate() {
        sharedElementEnterTransition = MaterialContainerTransform()
    }

    override fun motions() {
        if (args.travelPlan == null) {
            super.motions()
        } else {
            sharedElementEnterTransition = MaterialContainerTransform()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun init() {
        setInfo()
        listeners()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setInfo() = with(binding) {
        args.travelPlan?.let { travelPlan ->
            initRV()
            tvSource.text = travelPlan.source
            tvDestination.text = travelPlan.destination
            if (!travelPlan.transfer.isNullOrBlank()) {
                tvTransfer.visible()
                tvTransfer.text = getString(STRINGS.transfer_x , travelPlan.transfer)
            }

            travelPlan.travelDate.takeIf { !it.isNullOrBlank() }?.let { date ->
                date.getDateNextLine().also { tvDateTime.text = it }
                tvDateTime.visible()

                date.getDuration(null).takeIf { (-it) > 0 }?.let {
                    tvDaysLeft.text = getString(STRINGS.x_days_left , -it)
                    if (!travelPlan.date.isNullOrBlank()) {
                        val progress = (100 * (travelPlan.date!!.getDuration(null)
                            .toDouble() / travelPlan.travelDate!!.getDuration(travelPlan.date)
                            .toDouble())).toInt()

                        d("testing az" , "@$progress")
                        pbDateLeft.progress = abs(progress)
                    }
                } ?: run {
                    pbDateLeft.progress = 100
                }
            }

            initRestrictions(travelPlan)
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
                            binding.rvRestrictions.startLayoutAnimation()
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