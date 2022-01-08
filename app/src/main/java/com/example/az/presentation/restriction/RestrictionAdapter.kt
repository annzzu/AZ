package com.example.az.presentation.restriction


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.example.az.databinding.ItemRestrictionBinding
import com.example.az.model.restriction.RestrictionKotlin

typealias ClickRestriction = (click: Boolean) -> Unit


class RestrictionAdapter : ListAdapter<RestrictionKotlin , RestrictionAdapter.ViewHolder>(
    DiffCallback()
) {

    var click: ClickRestriction? = null

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int) =
        ViewHolder(
            ItemRestrictionBinding.inflate(
                LayoutInflater.from(parent.context) ,
                parent ,
                false
            )
        )

    override fun onBindViewHolder(holder: RestrictionAdapter.ViewHolder , position: Int) {
        holder.onBind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemRestrictionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(model: RestrictionKotlin) = with(binding) {
            tvCode.text = model.code
            root.setOnClickListener {
                click?.invoke(true)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<RestrictionKotlin>() {
        override fun areItemsTheSame(
            oldItem: RestrictionKotlin ,
            newItem: RestrictionKotlin
        ): Boolean =
            oldItem.code == newItem.code

        override fun areContentsTheSame(
            oldItem: RestrictionKotlin ,
            newItem: RestrictionKotlin
        ): Boolean =
            oldItem == newItem
    }

}
