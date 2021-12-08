package com.yudahendriawan.secondsubmissionfundamentalandroid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yudahendriawan.secondsubmissionfundamentalandroid.R
import com.yudahendriawan.secondsubmissionfundamentalandroid.adapter.SearchUsersAdapter
import com.yudahendriawan.secondsubmissionfundamentalandroid.databinding.ActivityFavoriteUsersBinding
import com.yudahendriawan.secondsubmissionfundamentalandroid.viewmodel.FavoritesViewModel
import com.yudahendriawan.secondsubmissionfundamentalandroid.viewmodel.SavedFavoritesUserViewModel
import com.yudahendriawan.secondsubmissionfundamentalandroid.viewmodel.ViewModelFactory

class FavoriteUsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUsersBinding
    private lateinit var viewModel: FavoritesViewModel
    private lateinit var adapter: SearchUsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = "Favorites"
            setDisplayHomeAsUpEnabled(true)
        }

        binding.rvFavoritesUser.layoutManager = LinearLayoutManager(this)
        binding.rvFavoritesUser.setHasFixedSize(true)
        binding.rvFavoritesUser.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager(this).orientation
            )
        )

        viewModel = obtainViewModel(this)
        viewModel.getAllFavoriteUsers().observe(this, { favoritesUser ->
            adapter = SearchUsersAdapter(this, favoritesUser, TYPE_VIEW_FAVORITES)
            binding.rvFavoritesUser.adapter = adapter
            adapter.notifyDataSetChanged()
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoritesViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoritesViewModel::class.java]
    }

    companion object{
        const val TYPE_VIEW_FAVORITES = "type.view.favorites"
    }
}