package com.example.az.presentation.airport


import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.az.databinding.FragmentAirportsBinding
import com.example.az.model.airport.Airport
import com.example.az.extensions.STRINGS
import com.example.az.extensions.gone
import com.example.az.extensions.visible
import com.example.az.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AirportsFragment : BaseFragment<FragmentAirportsBinding>(FragmentAirportsBinding::inflate) {
    private lateinit var airportAdapter: AirportAdapter
    private val viewModel: AirportsViewModel by viewModels()

    override fun init() {
        getResponse()
        initRV()
        observer()
    }

    private fun getResponse() {
        viewModel.getAirportsResponse()
    }

    private fun initRV() {
        binding.rvAirports.apply {
            airportAdapter = AirportAdapter()
            adapter = airportAdapter
            layoutManager =
                LinearLayoutManager(view?.context , LinearLayoutManager.HORIZONTAL , false)
        }
    }

    private fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                getStatus(state.data , true)
                state.error?.let {
                    getStatus(null , false)
                }
            }
        }
    }

    private fun getStatus(data: List<Airport>? , state: Boolean) {
//        with(binding) {
//            if (state) {
//                tvStatus.gone()
//                airportAdapter.setData(data!!)
//            } else {
//                tvStatus.visible()
//                tvStatus.text = getString(STRINGS.notifications_is_empty)
//            }
//        }

    }

}