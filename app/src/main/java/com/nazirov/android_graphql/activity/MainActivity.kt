package com.nazirov.android_graphql.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo3.exception.ApolloException
import com.nazirov.android_graphql.*
import com.nazirov.android_graphql.adapter.PostAdapter
import com.nazirov.android_graphql.databinding.ActivityMainBinding
import com.nazirov.android_graphql.network.GraphQL
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    override fun onResume() {
        super.onResume()
        getUserList()
    }

    private fun initViews() {
        getUserList()
        binding.floatingButton.setOnClickListener {
            openCreateActivity()
        }
    }

    private fun getUserList() {
        lifecycleScope.launch launchWhenResumed@{
            val response = try {
                GraphQL.get().query(UsersListQuery(10)).execute()
            } catch (e: ApolloException) {
                Log.d("MainActivity", e.toString())
                return@launchWhenResumed
            }
            val users = response.data?.users
            Log.d("MainActivity", users!!.size.toString())
            refreshAdapter(users)
        }
    }

    private fun refreshAdapter(photos: List<UsersListQuery.User>) {
        val adapter = PostAdapter(this,photos)
        binding.recyclerView.adapter = adapter
    }

    private fun openCreateActivity() {
        val intent = Intent(this, CreateActivity::class.java)
        startActivity(intent)
    }

    fun openUpdateActivity(user:UsersListQuery.User) {
        val intent = Intent(this, UpdateActivity::class.java)
        intent.putExtra("id",user.id.toString())
        intent.putExtra("name",user.name)
        intent.putExtra("rocket",user.rocket)
        intent.putExtra("twitter",user.twitter)
        startActivity(intent)
    }

}