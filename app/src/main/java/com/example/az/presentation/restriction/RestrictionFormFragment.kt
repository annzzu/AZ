package com.example.az.presentation.restriction

import android.util.Log.d
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.az.databinding.FragmentRestrictionFormBinding
import com.example.az.extensions.STRINGS
import com.example.az.extensions.showSnackBar
import com.example.az.model.restriction.RestrictionRequest
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
        btnAirportSearch.setOnClickListener {
            var ready = true
            if (ready) {
                searchRestriction("GVA" , "BER" , "TBS")
            } else {
                root.showSnackBar(getString(STRINGS.indicate_first))
            }
        }
    }

    private fun searchRestriction(from: String , to: String , transfer: String?) {
        val restriction = RestrictionRequest(
            from ,
            to ,
            transfer ,
        )
        findNavController().navigate(
            RestrictionFormFragmentDirections.actionNavigationRestrictionFormToNavigationRestriction(
                restriction
            )
        )
    }
}