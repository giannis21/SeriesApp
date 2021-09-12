package com.example.seriesapp.paging

import androidx.recyclerview.widget.DiffUtil
import com.example.seriesapp.data.characters.Result


object DiffCallback : DiffUtil.ItemCallback<Result>() {

    override fun areItemsTheSame(
        oldItem: Result,
        newItem: Result
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Result,
        newItem: Result
    ): Boolean {
        return oldItem == newItem
    }
}