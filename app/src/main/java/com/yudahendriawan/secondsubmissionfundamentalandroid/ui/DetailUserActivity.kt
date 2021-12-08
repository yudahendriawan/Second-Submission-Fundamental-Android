package com.yudahendriawan.secondsubmissionfundamentalandroid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.yudahendriawan.secondsubmissionfundamentalandroid.R
import com.yudahendriawan.secondsubmissionfundamentalandroid.adapter.SectionPagerAdapter
import com.yudahendriawan.secondsubmissionfundamentalandroid.databinding.ActivityDetailUserBinding
import com.yudahendriawan.secondsubmissionfundamentalandroid.model.DetailUserResponse
import com.yudahendriawan.secondsubmissionfundamentalandroid.model.SearchItemUsers
import com.yudahendriawan.secondsubmissionfundamentalandroid.viewmodel.DetailViewModel
import com.yudahendriawan.secondsubmissionfundamentalandroid.viewmodel.SavedFavoritesUserViewModel
import com.yudahendriawan.secondsubmissionfundamentalandroid.viewmodel.ViewModelFactory

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private var user: SearchItemUsers? = null
    val viewModel by viewModels<DetailViewModel>()
    private lateinit var savedFavoritesUserViewModel: SavedFavoritesUserViewModel
    private lateinit var type: String

    companion object {

        const val TYPE_VIEW = "type.view"
        const val USERNAME = "username"

        @StringRes
        private val TAB_TITLES =
            intArrayOf(R.string.text_tab_1, R.string.tab_repo, R.string.text_tab_2)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.extras?.getParcelable<SearchItemUsers>(USERNAME)
        type = intent.extras?.getString(TYPE_VIEW, "")!!

        val sectionPagerAdapter = SectionPagerAdapter(this, user?.login.toString())
        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->

            tab.text = resources.getString(TAB_TITLES[position])

        }.attach()

        supportActionBar?.hide()

        if (type == SearchGithubUsersActivity.TYPE_VIEW_DETAIL) {
            fabBehaviour(false)
        } else if (type == FavoriteUsersActivity.TYPE_VIEW_FAVORITES) {
            fabBehaviour(true)
        }

        viewModel.detailUser.observe(this, { user ->
            setDetailUser(user)
        })

        user?.login.toString()?.let {
            viewModel.getUserFollowers(it)
            viewModel.getUserFollowing(it)
            viewModel.getUserRepos(it)
        }

        viewModel.countFollowers.observe(this, {
            binding.tvCountFollowers.text = it.toString()
        })

        viewModel.countFollowing.observe(this, {
            binding.tvCountFollowing.text = it.toString()
        })

        viewModel.countRepos.observe(this, {
            binding.tvRepos.text = it.toString()
        })

        user?.login.toString()?.let { viewModel.getDetailUser(it) }

        viewModel.isLoading.observe(this, {
            showLoading(it)
        })

        savedFavoritesUserViewModel = obtainViewModel(this@DetailUserActivity)

        binding.fabFavorites.setOnClickListener {
            user?.let { user ->
                savedFavoritesUserViewModel.insert(user)
                showMessage()
            }
        }

        binding.fabRemoveFavorites.setOnClickListener {
            user?.let { user ->
                savedFavoritesUserViewModel.delete(user)
                showDeleteMessage()
                onBackPressed()
            }
        }
    }

    private fun fabBehaviour(isFavorites: Boolean) {
        with(binding) {
            fabFavorites.visibility = if (isFavorites) GONE else VISIBLE
            fabRemoveFavorites.visibility = if (isFavorites) VISIBLE else GONE
        }
    }

    private fun showMessage() {
        Toast.makeText(this, "Successfully added to favorite user!", Toast.LENGTH_SHORT).show()
    }

    private fun showDeleteMessage() {
        Toast.makeText(this, "Successfully remove from favorite list!", Toast.LENGTH_SHORT).show()
    }

    private fun obtainViewModel(activity: AppCompatActivity): SavedFavoritesUserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[SavedFavoritesUserViewModel::class.java]
    }

    private fun showLoading(isLoading: Boolean) {
        binding.detailProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setDetailUser(user: DetailUserResponse) {
        with(binding) {
            tvFullName.text = user.name
            tvUsername.text = user.login
            tvLinkGithub.text = user.htmlUrl
            Glide.with(this@DetailUserActivity)
                .load(user.avatarUrl)
                .centerCrop()
                .into(imageUser)
            tvCompany.text = user.company
            tvLocation.text = user.location
        }
    }

}