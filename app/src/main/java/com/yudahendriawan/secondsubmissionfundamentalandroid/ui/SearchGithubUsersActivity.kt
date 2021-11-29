package com.yudahendriawan.secondsubmissionfundamentalandroid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yudahendriawan.secondsubmissionfundamentalandroid.adapter.SearchUsersAdapter
import com.yudahendriawan.secondsubmissionfundamentalandroid.databinding.ActivitySearchGithubUsersBinding
import com.yudahendriawan.secondsubmissionfundamentalandroid.model.SearchItemUsers
import com.yudahendriawan.secondsubmissionfundamentalandroid.viewmodel.SearchUsersViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchGithubUsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchGithubUsersBinding
    private val viewModel by viewModels<SearchUsersViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchGithubUsersBinding.inflate(layoutInflater)

        binding.searchUser.isIconified = false
        binding.searchUser.clearFocus()

        viewModel.searchResult.observe(this, { usersResult ->
            showSearchResults(usersResult)
        })

        viewModel.isLoading.observe(this, { isLoading ->
            showLoading(isLoading)
        })

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerViewUsers.layoutManager = layoutManager
        binding.recyclerViewUsers.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager(this).orientation
            )
        )

        var job: Job? = null
        binding.searchUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                job?.cancel()
                job = MainScope().launch {
                    delay(500L)
                    query?.let {
                        viewModel.searchGithubUsers(query)
                    }
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                job?.cancel()
                job = MainScope().launch {
                    delay(500L)
                    query?.let {
                        viewModel.searchGithubUsers(query)
                    }
                }
                return true
            }

        })

        setContentView(binding.root)
    }

    private fun showSearchResults(users: List<SearchItemUsers>) {
        val adapter = SearchUsersAdapter(this, users)
        binding.recyclerViewUsers.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

    }
}