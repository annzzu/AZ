package com.example.az.presentation.airport


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.*
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.az.databinding.FragmentAirportsBinding
import com.example.az.databinding.FragmentNationalitiesBinding
import com.example.az.extensions.STRINGS
import com.example.az.extensions.invisible
import com.example.az.extensions.showSnackBar
import com.example.az.extensions.visible
import com.example.az.presentation.base.BaseFragmentDialog
import com.example.az.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AirportsFragmentDialog : BaseFragmentDialog<FragmentAirportsBinding>(
    FragmentAirportsBinding::inflate
) {

    private lateinit var airportAdapter: AirportAdapter

    private val viewModel by viewModels<AirportsViewModel>()

    override fun initRV() {
        binding.rvAirports.apply {
            airportAdapter = AirportAdapter()
            adapter = airportAdapter
            layoutManager =
                LinearLayoutManager(view?.context , LinearLayoutManager.HORIZONTAL , false)
        }
        airportAdapter.clickCallBack = {
            clickCallBack?.invoke(it)
            dismiss()
        }
    }

    override fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.airportList.collectLatest {
                with(binding) {
                    when (it) {
                        is Resource.Error -> {
                            tvNothingFound.visible()
                            progressBar.invisible()
                            tvNothingFound.text = getString(STRINGS.error)
                            root.showSnackBar(it.message!!)
                        }
                        is Resource.Loading -> {
                            progressBar.visible()
                        }
                        is Resource.Success -> {
                            progressBar.invisible()
                            val data = it.data?.airports
                            if (data!!.isNotEmpty()) {
                                tvNothingFound.invisible()
                                airportAdapter.submitList(data)
                            } else {
                                tvNothingFound.visible()
                                tvNothingFound.text = getString(STRINGS.nothing_found)
                            }
                        }
                    }
                }

            }
        }
    }
}