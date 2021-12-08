package com.yudahendriawan.secondsubmissionfundamentalandroid.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yudahendriawan.secondsubmissionfundamentalandroid.R
import com.yudahendriawan.secondsubmissionfundamentalandroid.adapter.SearchUsersAdapter
import com.yudahendriawan.secondsubmissionfundamentalandroid.databinding.ActivitySearchGithubUsersBinding
import com.yudahendriawan.secondsubmissionfundamentalandroid.model.SearchItemUsers
import com.yudahendriawan.secondsubmissionfundamentalandroid.preference.SettingPreferences
import com.yudahendriawan.secondsubmissionfundamentalandroid.viewmodel.SearchUsersViewModel
import com.yudahendriawan.secondsubmissionfundamentalandroid.viewmodel.SettingViewModel
import com.yudahendriawan.secondsubmissionfundamentalandroid.viewmodel.SettingViewModelFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchGithubUsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchGithubUsersBinding
    private val viewModel by viewModels<SearchUsersViewModel>()
    private lateinit var settingViewModel: SettingViewModel
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchGithubUsersBinding.inflate(layoutInflater)

        binding.searchUser.isIconified = false
        binding.searchUser.clearFocus()

        val preferences = SettingPreferences.getInstance(dataStore)
        settingViewModel = ViewModelProvider(
            this,
            SettingViewModelFactory(preferences)
        )[SettingViewModel::class.java]

        settingViewModel.getThemeSettings().observe(this, { isDarkModeActive ->
            this.isDarkModeActive = isDarkModeActive
            invalidateOptionsMenu()
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        })

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

        binding.searchUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchGithubUsers(query)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {

                return true
            }

        })

        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private var isDarkModeActive = false

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menu.findItem(R.id.dark_mode).title =
            if (isDarkModeActive) "Aktifkan Light Mode" else "Aktifkan Dark Mode"
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_favorites -> {
                startActivity(Intent(this, FavoriteUsersActivity::class.java))
            }

            R.id.dark_mode -> {
                settingViewModel.saveThemeSetting(!isDarkModeActive)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSearchResults(users: List<SearchItemUsers>) {
        val adapter = SearchUsersAdapter(this, users, TYPE_VIEW_DETAIL)
        binding.recyclerViewUsers.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

    }

    companion object {
        const val TYPE_VIEW_DETAIL = "type.view.details"
    }
}