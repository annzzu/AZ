package com.example.az.presentation.nationality

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.az.databinding.ItemNationalityBinding
import com.example.az.utils.callbacks.ClickCallBack
import com.example.az.utils.callbacks.StringDiffCallBack


class NationalityAdapter :
    ListAdapter<String , NationalityAdapter.ViewHolder>(StringDiffCallBack()) {
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
        holder: NationalityAdapter.ViewHolder ,
        position: Int
    ) = holder.onBind(getItem(position))

    inner class ViewHolder(private val binding: ItemNationalityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(nationality: String) = with(binding) {
            tvNationality.text = nationality.uppercase()
            root.setOnClickListener {
                clickCallBack?.invoke(nationality)
            }
        }
    }
}