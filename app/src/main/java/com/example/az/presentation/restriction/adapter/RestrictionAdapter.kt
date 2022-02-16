package com.example.az.presentation.restriction.adapter


import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.example.az.databinding.ItemRestrictionBinding
import com.example.az.databinding.ItemRestrictionExpandedBinding
import com.example.az.extensions.*
import com.example.az.domain.model.restriction.RestrictionKotlin


class RestrictionAdapter : ListAdapter<RestrictionKotlin , RecyclerView.ViewHolder>(
    DiffCallback()
) {
    companion object {
        const val PARENT = 1
        const val CHILD = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int) =
        if (viewType == PARENT) {
            ParentViewHolder(
                ItemRestrictionBinding.inflate(
                    LayoutInflater.from(parent.context) ,
                    parent ,
                    false
                )
            )
        } else {
            ChildViewHolder(
                ItemRestrictionExpandedBinding.inflate(
                    LayoutInflater.from(parent.context) ,
                    parent ,
                    false
                )
            )
        }

    inner class ParentViewHolder(private val binding: ItemRestrictionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(model: RestrictionKotlin) = with(binding) {
            tvCode.text = model.code
            root.setOnClickListener {
                model.expand = true
                notifyItemChanged(adapterPosition)
            }
        }
    }

    inner class ChildViewHolder(private val binding: ItemRestrictionExpandedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(model: RestrictionKotlin) = with(binding) {
            tvCode.text = model.code
            tvType.text = model.type
            val context = this@ChildViewHolder.itemView.context

            model.generalRestrictions?.let {
                lGeneralRestriction.visible()
                tvGeneralInformation.text =
                    context.getString(STRINGS.generalInformation , it.generalInformation)
                tvGeneralFull.text = context.getString(
                    STRINGS.generalInformationFull ,
                    it.allowsBusinessVisit.booleanToYN() ,
                    it.allowsTourists.booleanToYN() ,
                    it.covidPassportRequired.booleanToYN() ,
                    it.pcrRequiredForNoneResidents.booleanToYN() ,
                    it.pcrRequiredForResidents.booleanToYN() ,
                )
                btnInfoUrl.setOnClickListener { _ ->
                    val intent = Intent(Intent.ACTION_VIEW , Uri.parse(it.moreInfoUrl))
                    this@ChildViewHolder.itemView.context?.startActivity(intent)
                }
            } ?: lGeneralRestriction.gone()

            model.restrictionsByVaccination?.let {
                lRestrictionsByVaccination.visible()
                tvRestrictionsByVaccinationFull.text = context.getString(
                    STRINGS.restrictionsByVaccinationFull ,
                    it.dozesRequired ,
                    it.isAllowed?.booleanToYN() ,
                    it.maxDaysAfterVaccination ,
                    it.minDaysAfterVaccination
                )
            } ?: lRestrictionsByVaccination.gone()

            model.restrictionsByNationality?.let {
                lRestrictionsByNationality.visible()
                lateinit var restrictionNationalityAdapter: RestrictionNationalityAdapter
                rvNationalityRestriction.apply {
                    restrictionNationalityAdapter = RestrictionNationalityAdapter()
                    adapter = restrictionNationalityAdapter
                    layoutManager =
                        LinearLayoutManager(context , LinearLayoutManager.VERTICAL , false)
                    restrictionNationalityAdapter.submitList(model.restrictionsByNationality)
                    rvNationalityRestriction.startLayoutAnimation()
                }
            } ?: lRestrictionsByNationality.gone()

            btnClose.setOnClickListener {
                model.expand = false
                notifyItemChanged(adapterPosition)
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

    override fun getItemViewType(position: Int) =
        if (getItem(position).expand) CHILD else PARENT

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder , position: Int) {
        if (holder is ParentViewHolder) {
            holder.onBind(getItem(position))
        } else if (holder is ChildViewHolder) {
            holder.onBind(getItem(position))
        }
    }
}
