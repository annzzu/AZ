package com.example.az.presentation.restriction.fragment

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.az.databinding.FragmentRestrictionFormBinding
import com.example.az.extensions.STRINGS
import com.example.az.extensions.showSnackBar
import com.example.az.model.airport.AirportChooseType
import com.example.az.presentation.airport.AirportsFragmentDialog
import com.example.az.presentation.base.BaseFragment
import com.example.az.presentation.restriction.RestrictionViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RestrictionFormFragment :
    BaseFragment<FragmentRestrictionFormBinding>(FragmentRestrictionFormBinding::inflate) {

    private val restrictionViewModel: RestrictionViewModel by activityViewModels()

    override fun init() {
        listeners()
        setData()
    }

    private fun setData(){
        binding.tvSource.text = restrictionViewModel.restrictionRequestForm.from
        binding.tvDestination.text = restrictionViewModel.restrictionRequestForm.to
        binding.tvTransfer.text = restrictionViewModel.restrictionRequestForm.transfer
    }

    private fun listeners() {
        setOnClickFun()
        setOnLongClickFun()
    }

    private fun setOnClickFun() = with(binding) {
        btnFrom.setOnClickListener {
            openAirportDialog(AirportChooseType.FROM)
        }
        btnTo.setOnClickListener {
            openAirportDialog(AirportChooseType.TO)
        }
        btnTransfer.setOnClickListener {
            openAirportDialog(AirportChooseType.TRANSFER)
        }
        btnAirportSearch.setOnClickListener {
            if (!restrictionViewModel.restrictionRequestForm.to.isNullOrBlank() &&
                !restrictionViewModel.restrictionRequestForm.from.isNullOrBlank()
            ) {
                searchRestriction()
            } else {
                root.showSnackBar(getString(STRINGS.indicate_first))
            }
        }
    }

    private fun setOnLongClickFun() = with(binding) {
        btnFrom.setOnLongClickListener {
            deleteForm(AirportChooseType.FROM)
            true
        }
        btnTo.setOnLongClickListener {
            deleteForm(AirportChooseType.TO)
            true
        }
        btnTransfer.setOnLongClickListener {
            deleteForm(AirportChooseType.TRANSFER)
            true
        }
    }

    private fun openAirportDialog(type: AirportChooseType) =
        with(restrictionViewModel.restrictionRequestForm) {
            val dialog = AirportsFragmentDialog()
            dialog.show(childFragmentManager , null)
            dialog.clickCallBack = {
                when (type) {
                    AirportChooseType.FROM -> {
                        if (to.equals(it) || transfer?.split(",")?.contains(it) == true) {
                            differentRouteAlert(it)
                        } else {
                            from = it
                            binding.tvSource.text = it
                        }
                    }
                    AirportChooseType.TO -> {
                        if (from.equals(it) || transfer?.split(",")?.contains(it) == true) {
                            differentRouteAlert(it)
                        } else {
                            to = it
                            binding.tvDestination.text = it
                        }
                    }
                    AirportChooseType.TRANSFER -> {
                        if (from.equals(it) || to.equals(it) ||
                            transfer?.split(",")?.contains(it) == true
                        ) {
                            differentRouteAlert(it)
                        } else {
                            transfer = if (!transfer.isNullOrBlank()) transfer.plus(",$it") else it
                            binding.tvTransfer.text = transfer
                        }
                    }
                }
            }
        }

    private fun deleteForm(type: AirportChooseType) = with(binding) {
        with(restrictionViewModel.restrictionRequestForm) {
            when (type) {
                AirportChooseType.TO -> {
                    tvSource.text = ""
                    to = null
                }
                AirportChooseType.FROM -> {
                    tvDestination.text = ""
                    from = null
                }
                AirportChooseType.TRANSFER -> {
                    tvTransfer.text = ""
                    transfer = null
                }
            }
        }
    }

    private fun differentRouteAlert(it: String) =
        binding.root.showSnackBar(getString(STRINGS.choose_different_route , it))

    private fun searchRestriction() {
            findNavController().navigate(
                RestrictionFormFragmentDirections.actionNavigationRestrictionFormToNavigationRestriction()
            )
        }
}

