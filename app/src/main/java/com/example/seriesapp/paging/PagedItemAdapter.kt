package com.example.seriesapp.paging

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.seriesapp.BR

import com.example.seriesapp.R
import com.example.seriesapp.data.characters.Result
import com.example.seriesapp.databinding.ItemLayoutBinding


class PagedItemAdapter() : PagedListAdapter<Result, PagedItemAdapter.ItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = DataBindingUtil.inflate<ItemLayoutBinding>(LayoutInflater.from(parent.context), R.layout.item_layout, parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return currentList?.size ?: 0
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemLayoutBinding.setVariable(BR.character, getItem(position))
    }

    class ItemViewHolder(var itemLayoutBinding: ItemLayoutBinding) : RecyclerView.ViewHolder(itemLayoutBinding.root)


}