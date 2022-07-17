package com.nazirov.android_graphql.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo3.exception.ApolloException
import com.nazirov.android_graphql.DeleteUserMutation
import com.nazirov.android_graphql.UpdateUserMutation
import com.nazirov.android_graphql.databinding.ActivityUpdateBinding
import com.nazirov.android_graphql.network.GraphQL
import kotlinx.coroutines.launch

class UpdateActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdateBinding
    private var id: String = ""
    private var name: String = ""
    private var rocket: String = ""
    private var twitter: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {


        id = intent.getStringExtra("id") ?: "Empty"
        name = intent.getStringExtra("name") ?: "Empty"
        rocket = intent.getStringExtra("rocket") ?: "Empty"
        twitter = intent.getStringExtra("twitter") ?: "Empty"



        binding.apply {
            etName.setText(name)
            etRocket.setText(rocket)
            etTwitter.setText(twitter)

            etName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {
                    name = etName.text.toString()
                }
            })
            etRocket.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {
                    rocket = etRocket.text.toString()
                }
            })
            etTwitter.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {
                    twitter = etTwitter.text.toString()
                }
            })

            btnUpdate.setOnClickListener {
                if (name != "" && rocket != "" && twitter != "") {
                    updateUser(id, name, rocket, twitter)
                    finish()
                } else {
                    Toast.makeText(
                        this@UpdateActivity,
                        "Please fill the fields first",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            btnDelete.setOnClickListener {
                deleteUser(id)
                finish()
            }
        }
    }

    private fun updateUser(id: String, name: String, rocket: String, twitter: String) {
        lifecycleScope.launch launchWhenResumed@{
            val result = try {
                GraphQL.get().mutation(
                    UpdateUserMutation(id, name, rocket, twitter)
                ).execute()
            } catch (e: ApolloException) {
                Log.d("UpdateActivity", e.toString())
                return@launchWhenResumed
            }
            Log.d("UpdateActivity", result.toString())
        }
    }

    private fun deleteUser(id: String) {
        lifecycleScope.launch launchWhenResumed@{
            val result = try {
                GraphQL.get().mutation(DeleteUserMutation(id)).execute()
            } catch (e: ApolloException) {
                Log.d("UpdateActivity", e.toString())
                return@launchWhenResumed
            }
            Log.d("UpdateActivity", result.toString())
        }
    }
}