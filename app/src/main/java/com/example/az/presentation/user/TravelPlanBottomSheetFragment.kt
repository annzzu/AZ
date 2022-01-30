package com.example.az.presentation.user

import android.os.Build
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.az.R
import com.example.az.databinding.BottomSheetTravelPlanBinding
import com.example.az.databinding.ItemTravelPlanBinding
//import com.example.az.databinding.ItemTravelPlanBinding
import com.example.az.extensions.getDuration
import com.example.az.extensions.getTime
import com.example.az.extensions.getTimeNextLine
import com.example.az.model.travel_plan.TravelPlan
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import dagger.hilt.android.AndroidEntryPoint


typealias ClickSeeMore = (model: TravelPlan) -> Unit

@AndroidEntryPoint
class TravelPlanBottomSheetFragment : BottomSheetDialogFragment() {

    var clickSeeMore: ClickSeeMore? = null

    private var _binding: BottomSheetTravelPlanBinding? = null
    val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater ,
        container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetTravelPlanBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun bottomSheetFragment(model: TravelPlan) = with (binding){
        tvFromBottomSheet.text = model.source
        tvToBottomSheet.text = model.destination
        btnSeeMoreBottomSheet.setOnClickListener{
            clickSeeMore?.invoke(model)
        }
    }

}