package com.example.submission1.localData

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.submission1.model.GithubUserResponse

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: GithubUserResponse.Item)

    @Query("SELECT * FROM User")
    fun loadAll(): LiveData<MutableList<GithubUserResponse.Item>>

    @Query("SELECT * FROM User WHERE id LIKE :id LIMIT 1")
    fun findById(id: Int): GithubUserResponse.Item

    @Delete
    fun delete(user: GithubUserResponse.Item)
}