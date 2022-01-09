package com.example.az.presentation.nationality

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.util.Log.d
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.az.databinding.FragmentAirportsBinding
import com.example.az.databinding.FragmentNationalitiesBinding
import com.example.az.model.airport.Airport
import com.example.az.extensions.STRINGS
import com.example.az.extensions.gone
import com.example.az.extensions.showSnackBar
import com.example.az.extensions.visible
import com.example.az.presentation.airport.AirportAdapter
import com.example.az.presentation.airport.AirportsViewModel
import com.example.az.presentation.base.BaseFragment
import com.example.az.presentation.user.travel_plans.TravelPlanAdapter
import com.example.az.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NationalitiesFragment : BaseFragment<FragmentNationalitiesBinding>(FragmentNationalitiesBinding::inflate) {
    private lateinit var nationalityAdapter: NationalityAdapter
    private val viewModel: NationalityViewModel by viewModels()

    override fun init() {
        initRV()
        observer()
    }

    private fun initRV() {
        binding.rvNationality.apply {
            nationalityAdapter = NationalityAdapter()
            adapter = nationalityAdapter
            layoutManager =
                LinearLayoutManager(view?.context , LinearLayoutManager.HORIZONTAL , false)
        }
        nationalityAdapter.clickNationalityCallBack = {
            d("testing AZ" , "daawira $it")
        }
    }

    private fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.nationalities.collectLatest {
                when (it) {
                    is Resource.Error -> binding.root.showSnackBar(it.message!!)
                    is Resource.Loading -> TODO()
                    is Resource.Success -> {
                        nationalityAdapter.submitList(it.data?.nationalities)
                    }
                }
            }
        }
    }
}