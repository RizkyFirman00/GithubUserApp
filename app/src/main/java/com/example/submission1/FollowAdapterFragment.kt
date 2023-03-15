package com.example.submission1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission1.databinding.FragmentRvFollowBinding
import com.example.submission1.viewModel.ViewModelDetail

class FollowAdapterFragment : Fragment() {

    var type = 0
    private var binding: FragmentRvFollowBinding? = null
    private val viewModel by activityViewModels<ViewModelDetail>()
    private val adapter by lazy {
        UserAdapter { }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRvFollowBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.rvPeople?.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = this@FollowAdapterFragment.adapter
        }

        showLoading(true)
        viewModel.resultDetailFollowerModel.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setData(it)
                showLoading(false)
            }
        }

        showLoading(true)
        viewModel.resultDetailFollowingModel.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setData(it)
                showLoading(false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
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
        fun newInstance(type: Int): FollowAdapterFragment = FollowAdapterFragment().apply {
            this.type = type
        }
    }

}