package com.example.az.presentation.restriction.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.example.az.databinding.ItemNationalityRestrictionBinding
import com.example.az.extensions.STRINGS
import com.example.az.extensions.addInfoNationality
import com.example.az.model.restriction.RestrictionsByNationality


class RestrictionNationalityAdapter :
    ListAdapter<RestrictionsByNationality , RestrictionNationalityAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int) =
        ViewHolder(
            ItemNationalityRestrictionBinding.inflate(
                LayoutInflater.from(parent.context) ,
                parent ,
                false
            )
        )

    override fun onBindViewHolder(
        holder: RestrictionNationalityAdapter.ViewHolder ,
        position: Int
    ) {
        holder.onBind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemNationalityRestrictionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(model: RestrictionsByNationality) = with(binding) {
            val context = this@ViewHolder.itemView.context
            tvNationalityDataFull.text = model.type
                .addInfoNationality(
                    model.data.allowsTourists ,
                    context.getString(
                        STRINGS.allowsTouristsNat ,
                        model.data.allowsTourists
                    )
                ).addInfoNationality(
                    model.data.allowsBusinessVisit ,
                    context.getString(
                        STRINGS.allowsBusinessVisitNat ,
                        model.data.allowsBusinessVisit
                    )
                ).addInfoNationality(
                    model.data.pcrRequired ,
                    context.getString(
                        STRINGS.pcrRequiredNat ,
                        model.data.pcrRequired
                    )
                ).addInfoNationality(
                    model.data.fastTestRequired ,
                    context.getString(
                        STRINGS.fastTestRequiredNat ,
                        model.data.fastTestRequired
                    )
                ).addInfoNationality(
                    model.data.biometricPassportRequired ,
                    context.getString(
                        STRINGS.biometricPassportRequiredNat ,
                        model.data.biometricPassportRequired
                    )
                ).addInfoNationality(
                    model.data.locatorFormRequired ,
                    context.getString(
                        STRINGS.locatorFormRequiredNat ,
                        model.data.locatorFormRequired
                    )
                ).addInfoNationality(
                    model.data.covidPassportRequired ,
                    context.getString(
                        STRINGS.covidPassportRequiredNat ,
                        model.data.covidPassportRequired
                    )
                )
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<RestrictionsByNationality>() {
        override fun areItemsTheSame(
            oldItem: RestrictionsByNationality ,
            newItem: RestrictionsByNationality
        ): Boolean =
            oldItem.type == newItem.type

        override fun areContentsTheSame(
            oldItem: RestrictionsByNationality ,
            newItem: RestrictionsByNationality
        ): Boolean =
            oldItem == newItem
    }

}