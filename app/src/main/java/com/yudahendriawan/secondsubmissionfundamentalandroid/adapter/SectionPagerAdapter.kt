package com.yudahendriawan.secondsubmissionfundamentalandroid.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yudahendriawan.secondsubmissionfundamentalandroid.ui.FollowFragment

class SectionPagerAdapter(activity: AppCompatActivity, private val username: String) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return FollowFragment.newInstance(position + 1, username)
    }

}