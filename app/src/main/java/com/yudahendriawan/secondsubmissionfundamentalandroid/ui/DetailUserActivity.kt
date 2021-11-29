package com.yudahendriawan.secondsubmissionfundamentalandroid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.yudahendriawan.secondsubmissionfundamentalandroid.R
import com.yudahendriawan.secondsubmissionfundamentalandroid.adapter.SectionPagerAdapter
import com.yudahendriawan.secondsubmissionfundamentalandroid.databinding.ActivityDetailUserBinding
import com.yudahendriawan.secondsubmissionfundamentalandroid.model.DetailUserResponse
import com.yudahendriawan.secondsubmissionfundamentalandroid.viewmodel.DetailViewModel

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private var username: String? = null
    val viewModel by viewModels<DetailViewModel>()

    companion object {

        const val USERNAME = "username"

        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.text_tab_1, R.string.text_tab_2)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = intent.extras?.getString(USERNAME)

        val sectionPagerAdapter = SectionPagerAdapter(this, username.toString())
        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->

            tab.text = resources.getString(TAB_TITLES[position])

        }.attach()

        supportActionBar?.hide()

        viewModel.detailUser.observe(this, { user ->
            setDetailUser(user)
        })

        username?.let { viewModel.getDetailUser(it) }
    }

    private fun setDetailUser(user: DetailUserResponse) {
        with(binding) {
            tvUsername.text = user.name
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