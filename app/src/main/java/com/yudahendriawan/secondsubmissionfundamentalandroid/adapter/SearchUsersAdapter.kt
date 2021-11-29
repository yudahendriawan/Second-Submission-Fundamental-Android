package com.yudahendriawan.secondsubmissionfundamentalandroid.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yudahendriawan.secondsubmissionfundamentalandroid.databinding.ItemSearchUserBinding
import com.yudahendriawan.secondsubmissionfundamentalandroid.model.SearchItemUsers
import com.yudahendriawan.secondsubmissionfundamentalandroid.ui.DetailUserActivity

class SearchUsersAdapter(private val context: Activity, private val users: List<SearchItemUsers>) :
    RecyclerView.Adapter<SearchUsersAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemSearchUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchItemUsers) {
            binding.tvUsername.text = item.login
            binding.tvLinkGithub.text = item.htmlUrl
            Glide.with(context)
                .load(item.avatarUrl)
                .centerCrop()
                .into(binding.imageUser)
            binding.root.setOnClickListener {
                val intent = Intent(context, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.USERNAME, item.login)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSearchUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }

}