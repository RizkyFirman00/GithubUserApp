package com.example.submission1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission1.model.GithubUserResponse
import com.example.submission1.databinding.FragmentRvUserBinding

class UserAdapter(
    private val data: MutableList<GithubUserResponse.Item> = mutableListOf(),
    private val clickListener: (GithubUserResponse.Item) -> Unit
) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    fun setData(data: MutableList<GithubUserResponse.Item>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class UserViewHolder(private val binding: FragmentRvUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GithubUserResponse.Item) {
            binding.apply {
                Glide.with(itemView)
                    .load(item.avatar_url)
                    .into(binding.imgPhoto)
                txtUsername.text = item.id.toString()
                txtName.text = item.login
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(
            FragmentRvUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            clickListener(item)
        }
    }
}


