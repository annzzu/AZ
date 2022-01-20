package com.example.az.presentation.restriction.fragment

import androidx.navigation.fragment.findNavController
import com.example.az.databinding.FragmentRestrictionFormBinding
import com.example.az.extensions.STRINGS
import com.example.az.extensions.showSnackBar
import com.example.az.model.airport.AirportChooseType
import com.example.az.model.restriction.RestrictionRequest
import com.example.az.presentation.airport.AirportsFragmentDialog
import com.example.az.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RestrictionFormFragment :
    BaseFragment<FragmentRestrictionFormBinding>(FragmentRestrictionFormBinding::inflate) {

    private var restriction = RestrictionRequest()

    override fun init() {
        listeners()
    }

    private fun listeners() = with(binding) {
        cardSource.setOnClickListener {
            openAirportDialog(AirportChooseType.FROM)
        }
        cardDestination.setOnClickListener {
            openAirportDialog(AirportChooseType.TO)
        }
        cardTransfer.setOnClickListener {
            openAirportDialog(AirportChooseType.TRANSITION)
        }
        btnAirportSearch.setOnClickListener {
            if (!restriction.from.isNullOrBlank() || !restriction.from.isNullOrBlank()) {
                searchRestriction()
            } else {
                root.showSnackBar(getString(STRINGS.indicate_first))
            }
        }
    }

    private fun searchRestriction() {
        findNavController().navigate(
            RestrictionFormFragmentDirections.actionNavigationRestrictionFormToNavigationRestriction(
                restriction
            )
        )
    }


    private fun openAirportDialog(type: AirportChooseType) {
        val dialog = AirportsFragmentDialog()
        dialog.show(childFragmentManager , null)
        dialog.clickCallBack = {
            when (type) {
                AirportChooseType.FROM -> {
                    if (restriction.to.equals(it) || restriction.transfer.equals(it)) {
                        differentRouteAlert(it)
                    } else {
                        restriction.from = it
                        binding.tvSource.text = it
                    }
                }
                AirportChooseType.TO -> {
                    if (restriction.from.equals(it) || restriction.transfer.equals(it)) {
                        differentRouteAlert(it)
                    } else {
                        restriction.to = it
                        binding.tvDestination.text = it
                    }
                }
                AirportChooseType.TRANSITION -> {
                    if (restriction.from.equals(it) || restriction.to.equals(it)) {
                        differentRouteAlert(it)
                    } else {
                        restriction.transfer = it
                        binding.tvTransfer.text = it
                    }
                }
            }
        }
    }

    private fun differentRouteAlert(it: String) =
        binding.root.showSnackBar(
            getString(STRINGS.choose_different_route).plus(
                getString(
                    STRINGS.not_string ,
                    it
                )
            )
        )
}

