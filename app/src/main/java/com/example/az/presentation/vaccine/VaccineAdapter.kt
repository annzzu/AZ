package com.example.az.presentation.vaccine

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.az.databinding.ItemNationalityBinding
import com.example.az.extensions.DRAWABLES
import com.example.az.presentation.base.ClickCallBack
import com.example.az.utils.callbacks.StringDiffCallBack

class VaccineAdapter() :
    ListAdapter<String , VaccineAdapter.ViewHolder>(StringDiffCallBack()) {
    var clickCallBack: ClickCallBack? = null

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int) =
        ViewHolder(
            ItemNationalityBinding.inflate(
                LayoutInflater.from(parent.context) ,
                parent ,
                false
            )
        )

    override fun onBindViewHolder(
        holder: VaccineAdapter.ViewHolder ,
        position: Int
    ) = holder.onBind(getItem(position))

    inner class ViewHolder(private val binding: ItemNationalityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(vaccine: String) =with(binding){
            btnNationality.setImageResource(DRAWABLES.ic_vaccine)
            tvNationality.text = vaccine.uppercase()
            root.setOnClickListener {
                clickCallBack?.invoke(vaccine)
            }
        }
    }
}