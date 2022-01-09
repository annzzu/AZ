package com.example.az.presentation.airport


import android.util.Log.d
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.az.databinding.FragmentAirportsBinding
import com.example.az.model.airport.Airport
import com.example.az.extensions.STRINGS
import com.example.az.extensions.gone
import com.example.az.extensions.showSnackBar
import com.example.az.extensions.visible
import com.example.az.presentation.base.BaseFragment
import com.example.az.presentation.user.travel_plans.TravelPlanAdapter
import com.example.az.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AirportsFragment : BaseFragment<FragmentAirportsBinding>(FragmentAirportsBinding::inflate) {
    private lateinit var airportAdapter: AirportAdapter
    private val viewModel: AirportsViewModel by viewModels()

    override fun init() {
        initRV()
        observer()
    }

    private fun initRV() {
        binding.rvAirports.apply {
            airportAdapter = AirportAdapter()
            adapter = airportAdapter
            layoutManager =
                LinearLayoutManager(view?.context , LinearLayoutManager.HORIZONTAL , false)
        }
        airportAdapter.clickAirportCallBack = {
            d("testing AZ" , "daawira")
        }
    }

    private fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.airportList.collectLatest {
                when (it) {
                    is Resource.Error -> binding.root.showSnackBar(it.message!!)
                    is Resource.Loading -> TODO()
                    is Resource.Success -> {
                        airportAdapter.submitList(it.data?.airports)
                    }
                }
            }
        }
    }
}