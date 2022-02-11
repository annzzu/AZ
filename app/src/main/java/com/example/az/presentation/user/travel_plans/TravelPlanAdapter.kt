package com.example.az.presentation.user.travel_plans

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.az.domain.model.travel_plan.TravelPlan
import androidx.recyclerview.widget.ListAdapter
import com.example.az.databinding.ItemTravelPlanBinding
import com.example.az.extensions.*
import com.example.az.utils.callbacks.ClickTravelPlan
import kotlin.math.abs


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

            if (!model.transfer.isNullOrBlank()) {
                tvTransfer.visible()
                tvTransfer.text =
                    this@ViewHolder.itemView.context.getString(STRINGS.transfer_x , model.transfer)
            }
            model.travelDate.takeIf { !it.isNullOrBlank() }?.let { date ->
                date.getTime(false).also { tvDateTime.text = it }
                date.getTime(true).getTimeNextLine().also { tvDate.text = it }
                tvDateTime.visible()
                tvDate.visible()

                date.getDuration(null).takeIf { (-it) > 0 }?.let {
                    tvDaysLeft.text =  this@ViewHolder.itemView.context.getString(
                            STRINGS.x_days_left ,
                            (-it)
                        )
                    if (!model.date.isNullOrBlank()) {
                        pbDateLeft.progress =
                            abs((100 * (model.date!!.getDuration(null).toDouble() /
                                    model.travelDate!!.getDuration(model.date).toDouble())).toInt())
                    }
                } ?: run {
                    pbDateLeft.progress = 100
                }
            } ?: run {
                pbDateLeft.invisible()
                tvDaysLeft.invisible()
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
