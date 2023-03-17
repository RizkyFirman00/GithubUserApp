package com.example.submission1

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.submission1.databinding.ActivityDetailBinding
import com.example.submission1.localData.DbModule
import com.example.submission1.model.GithubUserResponse
import com.example.submission1.viewModel.ViewModelDetail
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

@Suppress("DEPRECATION", "NAME_SHADOWING")
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<ViewModelDetail> {
        ViewModelDetail.Factory(DbModule(this))
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = resources.getString(R.string.title)

        val item = intent.getParcelableExtra<GithubUserResponse.Item>("item")
        val username = item?.login ?: ""

        viewModel.setDataDetailUser(username)
        viewModel.getDataDetailUser().observe(this) {
            if (it != null) {
                binding.apply {
                    txtNameDetail.text = it.name
                    txtUsernameDetail.text = it.login
                    txtFollowersDetail.text = "Followers : ${it.followers}"
                    txtFollowingDetail.text = "Following : ${it.following}"
                    Glide.with(this@DetailActivity)
                        .load(item?.avatar_url)
                        .into(imgPhotoDetail)
                }
            }
        }

        val titleFragment = mutableListOf(
            getString(R.string.followers_tab),
            getString(R.string.following_tab)
        )

        val fragment = mutableListOf<Fragment>(
            FollowAdapterFragment.newInstance(FollowAdapterFragment.FOLLOWERS),
            FollowAdapterFragment.newInstance(FollowAdapterFragment.FOLLOWING)
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
                        showLoading(true)
                        viewModel.setDataDetailFollower(username)
                        viewModel.getDataDetailFollower()
                        showLoading(false)
                    }
                    1 -> {
                        showLoading(true)
                        viewModel.setDataDetailFollowing(username)
                        viewModel.getDataDetailFollowing()
                        showLoading(false)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        viewModel.setDataDetailFollower(username)
        viewModel.getDataDetailFollower()

        viewModel.resultSuccessFavorite.observe(this){
            binding.btnFavorite.changeIconColor(R.color.red)
        }

        viewModel.resultDeleteFavorite.observe(this){
            binding.btnFavorite.changeIconColor(R.color.white)
        }

        binding.btnFavorite.setOnClickListener{
            viewModel.setFavorite(item)
        }

        viewModel.findFavorite(item?.id ?: 0){
            binding.btnFavorite.changeIconColor(R.color.red)
        }
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

    private fun FloatingActionButton.changeIconColor(@ColorRes color: Int) {
        val color = ContextCompat.getColor(this.context, color)
        imageTintList = ColorStateList.valueOf(color)
    }
}