package com.yudahendriawan.secondsubmissionfundamentalandroid.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yudahendriawan.secondsubmissionfundamentalandroid.databinding.ItemReposBinding
import com.yudahendriawan.secondsubmissionfundamentalandroid.databinding.ItemSearchUserBinding
import com.yudahendriawan.secondsubmissionfundamentalandroid.model.FollowUserResponseItem
import com.yudahendriawan.secondsubmissionfundamentalandroid.model.ReposResponse

class ReposAdapter(
    private val followers: List<ReposResponse>
) :
    RecyclerView.Adapter<ReposAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemReposBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReposResponse) {
            binding.tvUsername.text = item.name
            binding.tvLinkGithub.text = item.htmlUrl
            binding.root.isClickable = false
            binding.root.isFocusable = false
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemReposBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(followers[position])
    }

    override fun getItemCount(): Int {
        return followers.size
    }
}