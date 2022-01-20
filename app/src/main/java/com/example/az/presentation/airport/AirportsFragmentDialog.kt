package com.example.az.presentation.airport


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.*
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.az.databinding.FragmentAirportsBinding
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
class AirportsFragmentDialog : BaseFragmentDialog() {

    private lateinit var airportAdapter: AirportAdapter

    private val viewModel by viewModels<AirportsViewModel>()

    private var _binding: FragmentAirportsBinding? = null
    val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater ,
        container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAirportsBinding.inflate(inflater , container , false)
        return binding.root
    }

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
                when (it) {
                    is Resource.Error -> {
                        binding.tvNothingFound.visible()
                        binding.tvNothingFound.text = getString(STRINGS.error)
                        binding.root.showSnackBar(it.message!!)
                    }
                    is Resource.Loading -> {
                        binding.progressBar.visible()
                    }
                    is Resource.Success -> {
                        binding.progressBar.invisible()
                        val data = it.data?.airports
                        if (data!!.isNotEmpty()){
                            binding.tvNothingFound.invisible()
                            airportAdapter.submitList(data)
                        }else{
                            binding.tvNothingFound.visible()
                            binding.tvNothingFound.text = getString(STRINGS.nothing_found)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}