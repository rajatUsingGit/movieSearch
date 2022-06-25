package com.example.searchmovies.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.searchmovies.data.ResponseItem


@BindingAdapter("listData")
fun listData(recyclerView: RecyclerView, movieItems: List<ResponseItem>?) {
    (recyclerView.adapter as MainListAdapter?)?.submitList(movieItems)
}