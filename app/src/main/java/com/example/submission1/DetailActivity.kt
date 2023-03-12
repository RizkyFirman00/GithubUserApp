package com.example.submission1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.submission1.databinding.ActivityDetailBinding
import com.example.submission1.model.GithubDetailResponse
import com.example.submission1.viewModel.ViewModelDetail
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<ViewModelDetail>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = resources.getString(R.string.title)

        val username = intent.getStringExtra("username") ?: ""

        viewModel.resultDetailModel.observe(this) {
            when (it) {
                is ResultMain.Success<*> -> {
                    val user = it.data as GithubDetailResponse
                    with(binding) {
                        txtNameDetail.text = user.name
                        txtUsernameDetail.text = user.login
                        txtFollowersDetail.text = user.followers.toString()
                        txtFollowingDetail.text = user.following.toString()
                        Glide.with(this@DetailActivity)
                            .load(user.avatar_url)
                            .into(imgPhotoDetail)
                    }
                }
                is ResultMain.Error -> {
                    Toast.makeText(this, it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is ResultMain.Loading -> {
                    showLoading(it.isLoading)
                }
            }
        }
        viewModel.getDataDetailUser(username)


        val titleFragment = mutableListOf(
            getString(R.string.followers_tab),
            getString(R.string.following_tab)
        )

        val fragment = mutableListOf<Fragment>(
            RvFollowFragment.newInstance(RvFollowFragment.FOLLOWERS),
            RvFollowFragment.newInstance(RvFollowFragment.FOLLOWING)
        )

        val adapter = DetailUserAdapter(this, fragment)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = titleFragment[position]
        }.attach()

        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        viewModel.getDataDetailFollower(username)
                    }
                    1 -> {
                        viewModel.getDataDetailFollowing(username)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        viewModel.getDataDetailFollower(username)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}