package com.example.submission1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission1.databinding.ActivityMainBinding
import com.example.submission1.model.GithubUserResponse
import com.example.submission1.viewModel.ViewModelMain

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val adapter by lazy {
        UserAdapter {
            Intent(this, DetailActivity::class.java).apply {
                putExtra("username", it.login)
                putExtra("repos_url", it.id)
                startActivity(this)
            }
        }
    }

    private val viewModel by viewModels<ViewModelMain>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.title)

        binding.rvPeople.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rvPeople.setHasFixedSize(true)
        binding.rvPeople.adapter = adapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewModel.searchUserData(p0.toString())
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })

        viewModel.resultMainModel.observe(this) {
            when (it) {
                is ResultMain.Success<*> -> {
                    adapter.setData(it.data as MutableList<GithubUserResponse.Item>)
                }
                is ResultMain.Error -> {
                    Toast.makeText(this, it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is ResultMain.Loading -> {
                    showLoading(it.isLoading)
                }
            }
        }

        viewModel.getDataUser()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}