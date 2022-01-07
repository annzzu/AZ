package com.example.az.presentation.user

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.az.R
import com.example.az.databinding.ItemTravelPlanBinding
//import com.example.az.databinding.ItemTravelPlanBinding
import com.example.az.extensions.getDuration
import com.example.az.extensions.getTime
import com.example.az.extensions.getTimeNextLine
import com.example.az.extensions.gone
import com.example.az.model.travel_plan.TravelPlan
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class TravelPlanBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: ItemTravelPlanBinding? = null
    private val binding: ItemTravelPlanBinding get() = _binding!!

    //    private var binding: ItemTravelPlanBinding
//        binding = ItemTravelPlanBinding.inflate(inflater , container , false)
//        return binding.root
    override fun onCreateView(
        inflater: LayoutInflater ,
        container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ItemTravelPlanBinding.inflate(inflater , container , false)
        return binding.root
//        return
        //        inflater.inflate(R.layout.item_travel_plan , container , false)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun bottomSheetFragment(model: TravelPlan) {
        binding.tvSource.text = "Ana"
//        with(binding) {
//            tvSource.text = model.source
//            tvDestination.text = model.destination
//            if (!model.date.isNullOrBlank()) {
//                tvDate.text = model.date.getTime(true).getTimeNextLine()
//                tvDateTime.text = model.date.getTime(false)
//                val days = model.date.getDuration().toInt()
//                if (days <= 0){
//                    pbDateLeft.progress = 100
//                }else{
//                    pbDateLeft.progress = 100 / days
//                    pbDateLeft.max = days + 1
//                    tvDaysLeft.text = "$days Days Left"
//                }
//            }else{
//                pbDateLeft.progress = 100
//                pbDateLeft.gone()
//                tvDaysLeft.gone()
//            }
//            cvTravelPlan.setOnClickListener {
//                clickTravelPlan?.invoke(model)
//            }
    }


//        private lateinit var binding: ItemTravelPlanBinding
//        binding = ItemTravelPlanBinding.inflate(inflater , container , false)
//        return binding.root


}