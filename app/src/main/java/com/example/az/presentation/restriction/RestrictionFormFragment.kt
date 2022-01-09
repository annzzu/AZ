package com.example.az.presentation.restriction

import android.util.Log.d
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.az.databinding.FragmentRestrictionFormBinding
import com.example.az.extensions.STRINGS
import com.example.az.extensions.showSnackBar
import com.example.az.model.airport.AirportChooseType
import com.example.az.model.restriction.RestrictionRequest
import com.example.az.presentation.airport.AirportsFragmentDialog
import com.example.az.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RestrictionFormFragment :
    BaseFragment<FragmentRestrictionFormBinding>(FragmentRestrictionFormBinding::inflate) {

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
            if (!restriction.from.isNullOrBlank()  || !restriction.from.isNullOrBlank()  ) {
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
    private val restriction = RestrictionRequest()

    private fun openAirportDialog(type: AirportChooseType) {
        val dialog = AirportsFragmentDialog()
        dialog.show(childFragmentManager , null)
        dialog.clickAirport = {
            when (type) {
                AirportChooseType.FROM -> {
                    restriction.from = it
                    binding.tvSource.text = it
                }
                AirportChooseType.TO -> {
                    restriction.to = it
                    binding.tvDestination.text = it
                }
                AirportChooseType.TRANSITION ->{
                    restriction.transfer = it
                    binding.tvTransfer.text = it
                }
            }
        }
    }
}