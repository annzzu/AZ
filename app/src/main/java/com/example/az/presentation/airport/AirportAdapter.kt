package com.example.az.presentation.airport

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.az.databinding.ItemAirportBinding
import com.example.az.model.airport.Airport
import com.example.az.model.travel_plan.TravelPlan
import com.example.az.presentation.user.travel_plans.TravelPlanAdapter

typealias ClickAirportCallBack = (airportCity: String) -> Unit

class AirportAdapter() :
    ListAdapter<Airport , AirportAdapter.ViewHolder>(DiffCallback()) {
    var clickAirportCallBack: ClickAirportCallBack? = null

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int) =
        ViewHolder(
            ItemAirportBinding.inflate(
                LayoutInflater.from(parent.context) ,
                parent ,
                false
            )
        )

    override fun onBindViewHolder(
        holder: AirportAdapter.ViewHolder ,
        position: Int
    ) = holder.onBind(getItem(position))

    inner class ViewHolder(private val binding: ItemAirportBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(model: Airport) {
            binding.tvAirport.text = "${model.country}\n${model.city}\n${model.code}"
            binding.root.setOnClickListener {
                clickAirportCallBack?.invoke(model.code!!)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Airport>() {
        override fun areItemsTheSame(oldItem: Airport , newItem: Airport): Boolean =
            oldItem.code == newItem.code

        override fun areContentsTheSame(oldItem: Airport , newItem: Airport): Boolean =
            oldItem == newItem
    }
}