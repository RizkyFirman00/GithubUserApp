package com.example.submission1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission1.databinding.FragmentRvFollowBinding
import com.example.submission1.model.GithubUserResponse
import com.example.submission1.viewModel.ViewModelDetail

class RvFollowFragment : Fragment() {

    var type = 0
    private var binding: FragmentRvFollowBinding? = null
    private val viewModel by activityViewModels<ViewModelDetail>()
    private val adapter by lazy {
        UserAdapter { }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRvFollowBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.rvPeople?.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = this@RvFollowFragment.adapter
        }

        when (type) {
            FOLLOWERS -> {
                viewModel.resultDetailFollowerModel.observe(viewLifecycleOwner, this::resultFollow)
            }
            FOLLOWING -> {
                viewModel.resultDetailFollowingModel.observe(viewLifecycleOwner, this::resultFollow)
            }
        }
    }

    private fun resultFollow(state: ResultMain) {
        when (state) {
            is ResultMain.Success<*> -> {
                adapter.setData(state.data as MutableList<GithubUserResponse.Item>)
            }
            is ResultMain.Error -> {
                Toast.makeText(
                    requireActivity(),
                    state.exception.message.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
            is ResultMain.Loading -> {
                showLoading(state.isLoading)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.progressBar?.visibility = View.GONE
        }
    }

    companion object {
        const val FOLLOWERS = 10
        const val FOLLOWING = 11
        fun newInstance(type: Int): RvFollowFragment = RvFollowFragment()
            .apply {
                this.type = type
            }
    }

}