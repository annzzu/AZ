package com.example.az.utils
//
//
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log.d
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.view.WindowManager
//import androidx.fragment.app.*
//import androidx.lifecycle.lifecycleScope
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.az.databinding.FragmentAirportsBinding
//import com.example.az.extensions.showSnackBar
//import com.example.az.presentation.user.UserViewModel
//import com.example.az.utils.Resource
//import dagger.hilt.android.AndroidEntryPoint
//import kotlinx.coroutines.flow.collectLatest
//import kotlinx.coroutines.launch
//
//typealias ClickAirport = (airportCode: String) -> Unit
//
//@AndroidEntryPoint
//class AirportsFragmentDialog : DialogFragment() {
//
//    private lateinit var airportAdapter: AirportAdapter
//
//    private var _binding: FragmentAirportsBinding? = null
//    val binding
//        get() = _binding!!
//
//    private val viewModel by viewModels<AirportsViewModel>()
//
//    override fun onCreateView(
//        inflater: LayoutInflater ,
//        container: ViewGroup? ,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = FragmentAirportsBinding.inflate(inflater , container , false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
//        super.onViewCreated(view , savedInstanceState)
//        init()
//    }
//
//    override fun onStart() {
//        super.onStart()
//        dialog?.window?.setLayout(
//            WindowManager.LayoutParams.MATCH_PARENT ,
//            WindowManager.LayoutParams.WRAP_CONTENT
//        )
//    }
//
//    fun init() {
//        initRV()
//        observer()
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
//            clickAirport?.invoke(it)
//            dismiss()
//        }
//    }
//    var clickAirport: ClickAirport? = null
//
//    private fun observer() {
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewModel.airportList.collectLatest {
//                when (it) {
//                    is Resource.Error -> binding.root.showSnackBar(it.message!!)
//                    is Resource.Loading -> TODO()
//                    is Resource.Success -> {
//                        airportAdapter.submitList(it.data?.airports)
//                    }
//                }
//            }
//        }
//    }
//}