package com.example.az.presentation.restriction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.az.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AirportBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var rvAirports : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater ,
        container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_airports, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rvAirports = view.findViewById(R.id.rvAirports)
    }
}