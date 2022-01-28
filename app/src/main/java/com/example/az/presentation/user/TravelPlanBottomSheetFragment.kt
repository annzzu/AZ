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
import com.example.az.databinding.ItemTravelPlanBinding
//import com.example.az.databinding.ItemTravelPlanBinding
import com.example.az.extensions.getDuration
import com.example.az.extensions.getTime
import com.example.az.extensions.getTimeNextLine
import com.example.az.model.travel_plan.TravelPlan
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton


typealias ClickSeeMore = (model: TravelPlan) -> Unit

class TravelPlanBottomSheetFragment : BottomSheetDialogFragment() {

    var clickSeeMore: ClickSeeMore? = null

    override fun onCreateView(
        inflater: LayoutInflater ,
        container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_travel_plan, container, false)
    }

    fun bottomSheetFragment(model: TravelPlan){
        view?.findViewById<AppCompatTextView>(R.id.tvFromBottomSheet)?.text = model.source
        view?.findViewById<AppCompatTextView>(R.id.tvToBottomSheet)?.text = model.destination
        view?.findViewById<AppCompatTextView>(R.id.btnSeeMoreBottomSheet)?.setOnClickListener{
            clickSeeMore?.invoke(model)
        }
    }

}