package com.example.az.presentation.restriction


import android.util.Log.d
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.az.databinding.FragmentRestrictionBinding
import com.example.az.extensions.getName
import com.example.az.extensions.showSnackBar
import com.example.az.model.airport.Airport
import com.example.az.model.restriction.GeneralRestrictions
import com.example.az.model.restriction.RestrictionResponse
import com.example.az.model.restriction.RestrictionsByNationality
import com.example.az.model.restriction.RestrictionsByVaccination
import com.example.az.presentation.auth.LoginViewModel
import com.example.az.presentation.base.BaseFragment
import com.example.az.presentation.user.travel_plans.TravelPlanAdapter
import com.example.az.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RestrictionFragment :
    BaseFragment<FragmentRestrictionBinding>(FragmentRestrictionBinding::inflate) ,
    ConvertToRestrictionKotlin {

    private val args: RestrictionFragmentArgs by navArgs()
    private val viewModel: RestrictionViewModel by activityViewModels()
    private lateinit var restrictionAdapter: RestrictionAdapter

    override fun init() {
        listeners()
        binding.tvFrom.text = args.restrictionRequest.from
        binding.tvTo.text = args.restrictionRequest.to
        observers()
    }

    private fun listeners() = with(binding) {
        btnBack.setOnClickListener {
            findNavController().navigate(RestrictionFragmentDirections.actionNavigationRestrictionToNavigationRestrictionForm())
        }
        rvRestrictions.apply {
            restrictionAdapter = RestrictionAdapter()
            adapter = restrictionAdapter
            layoutManager =
                LinearLayoutManager(view?.context , LinearLayoutManager.VERTICAL , false)
        }
        restrictionAdapter.click = {
//            openTravelPlanDetails(it)
            d("testing AZ", "davawire")
        }
    }

    private fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getRestriction(args.restrictionRequest)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.restriction.collectLatest {
                when (it) {
                    is Resource.Error -> binding.root.showSnackBar(it.message!!)
                    is Resource.Loading -> {
                        d("testing AZ" , "Loading")
                        TODO()
                    }
                    is Resource.Success -> {
                        d("testing AZ" , "ar vici ra xdeba \n ${it}")
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
            viewModel.restrictionList.collectLatest {
                d("testing AZ" , "ar vici ra xdeba arada vici \n $it")
                it.let { list ->
                    restrictionAdapter.submitList(list)
                    if (list.isNotEmpty()) {
                        d("testing AZ" , "ar vici ra xdeba \n ${list.size}")
                    }
                }
            }
        }
    }
}
