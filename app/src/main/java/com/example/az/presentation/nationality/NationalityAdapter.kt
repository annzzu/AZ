package com.example.az.presentation.nationality

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.az.databinding.ItemNationalityBinding

typealias ClickNationalityCallBack = (nationality: String) -> Unit

class NationalityAdapter() :
    ListAdapter<String , NationalityAdapter.ViewHolder>(DiffCallback()) {
    var clickNationalityCallBack: ClickNationalityCallBack? = null

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int) =
        ViewHolder(
            ItemNationalityBinding.inflate(
                LayoutInflater.from(parent.context) ,
                parent ,
                false
            )
        )

    override fun onBindViewHolder(
        holder: NationalityAdapter.ViewHolder ,
        position: Int
    ) = holder.onBind(getItem(position))

    inner class ViewHolder(private val binding: ItemNationalityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(nationality: String) {
            binding.tvNationality.text = nationality.uppercase()
            binding.root.setOnClickListener {
                clickNationalityCallBack?.invoke(nationality)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String , newItem: String): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String , newItem: String): Boolean =
            oldItem == newItem
    }
}