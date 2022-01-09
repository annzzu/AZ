package com.example.az.presentation.airport


import android.os.Bundle
import android.util.Log.d
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.az.databinding.ActivityMainBinding
import com.example.az.databinding.FragmentAirportsBinding
import com.example.az.model.airport.Airport
import com.example.az.extensions.STRINGS
import com.example.az.extensions.gone
import com.example.az.extensions.visible
import com.example.az.presentation.base.BaseFragment
import com.example.az.presentation.user.travel_plans.TravelPlanAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
//
//@AndroidEntryPoint
//class AirportsFragmentDialog : DialogFragment() {
//
//    private lateinit var binding: A
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        installSplashScreen()
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        initFab()
//
//    }
//    private lateinit var airportAdapter: AirportAdapter
//    private val viewModel: AirportsViewModel by viewModels()
//
//    override fun init() {
//        getResponse()
//        initRV()
//        observer()
//    }
//
//    private fun getResponse() {
//        viewModel.getAirportsResponse()
//    }
//
//    private fun initRV() {
//        binding.rvAirports.apply {
//            airportAdapter = AirportAdapter()
//            adapter = airportAdapter
//            layoutManager =
//                LinearLayoutManager(view?.context , LinearLayoutManager.HORIZONTAL , false)
//        }
//        airportAdapter.clickAirportCallBack = {
//            d("testing AZ", "daawira")
//        }
//    }
//
//    private fun observer() {
//        viewLifecycleOwner.lifecycleScope.launch {
//
//        }
//    }
//
//    private fun getStatus(data: List<Airport>? , state: Boolean) {
////        with(binding) {
////            if (state) {
////                tvStatus.gone()
////                airportAdapter.setData(data!!)
////            } else {
////                tvStatus.visible()
////                tvStatus.text = getString(STRINGS.notifications_is_empty)
////            }
////        }
//
//    }
//
//}