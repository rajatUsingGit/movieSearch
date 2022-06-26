package com.example.searchmovies.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.searchmovies.Constants
import com.example.searchmovies.data.ResponseItem
import com.example.searchmovies.databinding.ListItemBinding

class MainListAdapter :
    ListAdapter<ResponseItem, MainListAdapter.MainListViewHolder>(DiffCallback) {

    private lateinit var mContext: Context

    class MainListViewHolder(val binding: ListItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    companion object DiffCallback : DiffUtil.ItemCallback<ResponseItem>() {
        override fun areItemsTheSame(oldItem: ResponseItem, newItem: ResponseItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ResponseItem, newItem: ResponseItem): Boolean {
            return oldItem.title == newItem.title && oldItem.posterPath == newItem.posterPath
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListViewHolder {
        val listItemBinding = ListItemBinding.inflate(LayoutInflater.from(parent.context))
        val lp = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        listItemBinding.root.layoutParams = lp
        mContext = parent.context
        return MainListViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(holder: MainListViewHolder, position: Int) {
        val item  = getItem(position)
        holder.binding.textBox.text = item.title
        Glide.with(mContext)
            .load(Constants.IMAGE_BASE_URL + item.posterPath)
            .fitCenter()
            .into(holder.binding.imageBox)
    }

}