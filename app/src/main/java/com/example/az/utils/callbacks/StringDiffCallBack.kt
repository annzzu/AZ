package com.example.az.utils.callbacks

import androidx.recyclerview.widget.DiffUtil


class StringDiffCallBack : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String , newItem: String): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: String , newItem: String): Boolean =
        oldItem == newItem
}