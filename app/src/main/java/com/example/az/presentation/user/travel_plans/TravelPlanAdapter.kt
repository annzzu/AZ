package com.example.az.presentation.user.travel_plans

import android.os.Build
import android.util.Log.d
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.az.model.travel_plan.TravelPlan
import androidx.recyclerview.widget.ListAdapter
import com.example.az.databinding.ItemTravelPlanBinding
import com.example.az.extensions.*

typealias ClickTravelPlan = (travelPlan: TravelPlan) -> Unit


class TravelPlanAdapter : ListAdapter<TravelPlan , TravelPlanAdapter.ViewHolder>(DiffCallback()) {

    var clickTravelPlan: ClickTravelPlan? = null

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int) =
        ViewHolder(
            ItemTravelPlanBinding.inflate(
                LayoutInflater.from(parent.context) ,
                parent ,
                false
            )
        )


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TravelPlanAdapter.ViewHolder , position: Int) {
        holder.onBind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemTravelPlanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun onBind(model: TravelPlan) = with(binding) {
            tvSource.text = model.source
            tvDestination.text = model.destination
            if (!model.date.isNullOrBlank()) {
                tvDate.text = model.date.getTime(true).getTimeNextLine()
                tvDateTime.text = model.date.getTime(false)
                val days = model.date.getDuration().toInt()
                if (days <= 0){
                    pbDateLeft.progress = 100
                }else{
                    pbDateLeft.progress = 100 / days
                    pbDateLeft.max = days + 1
                    tvDaysLeft.text = "$days Days Left"
                }
            }else{
                pbDateLeft.progress = 100
                pbDateLeft.gone()
                tvDaysLeft.gone()
            }
            cvTravelPlan.setOnClickListener {
                clickTravelPlan?.invoke(model)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<TravelPlan>() {
        override fun areItemsTheSame(oldItem: TravelPlan , newItem: TravelPlan): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TravelPlan , newItem: TravelPlan): Boolean =
            oldItem == newItem
    }


}
