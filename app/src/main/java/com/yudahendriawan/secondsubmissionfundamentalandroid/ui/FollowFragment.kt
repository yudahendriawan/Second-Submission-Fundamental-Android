package com.yudahendriawan.secondsubmissionfundamentalandroid.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yudahendriawan.secondsubmissionfundamentalandroid.adapter.FollowAdapter
import com.yudahendriawan.secondsubmissionfundamentalandroid.adapter.ReposAdapter
import com.yudahendriawan.secondsubmissionfundamentalandroid.databinding.FragmentFollowBinding
import com.yudahendriawan.secondsubmissionfundamentalandroid.model.FollowUserResponseItem
import com.yudahendriawan.secondsubmissionfundamentalandroid.model.ReposResponse
import com.yudahendriawan.secondsubmissionfundamentalandroid.viewmodel.DetailViewModel

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<DetailViewModel>()

    companion object {

        private const val ARG_SECTION_NUMBER = "section_number"
        private const val USERNAME = "username"

        @JvmStatic
        fun newInstance(index: Int, username: String) = FollowFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_SECTION_NUMBER, index)
                putString(USERNAME, username)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val username = arguments?.getString(USERNAME)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.layoutManager = layoutManager
        binding.recyclerview.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager(requireContext()).orientation
            )
        )

        viewModel.userFollowers.observe(viewLifecycleOwner, {
            setDataFollow(it)
        })

        viewModel.userFollowing.observe(viewLifecycleOwner, {
            setDataFollow(it)
        })

        viewModel.userRepos.observe(viewLifecycleOwner, {
            setDataRepos(it)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        if (index == 1) {
            if (username != null) {
                viewModel.getUserFollowers(username)
            }
        }

        if (index == 3) {
            if (username != null) {
                viewModel.getUserFollowing(username)
            }
        }

        if (index == 2) {
            if (username != null) {
                viewModel.getUserRepos(username)
            }
        }

    }

    private fun setDataFollow(user: List<FollowUserResponseItem>) {
        val adapter = FollowAdapter(requireActivity(), user)
        binding.recyclerview.adapter = adapter
    }

    private fun setDataRepos(user: List<ReposResponse>) {
        val adapter = ReposAdapter(user)
        binding.recyclerview.adapter = adapter
    }


}