package com.nazirov.android_graphql.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo3.exception.ApolloException
import com.nazirov.android_graphql.InsertUserMutation
import com.nazirov.android_graphql.R
import com.nazirov.android_graphql.databinding.ActivityCreateBinding
import com.nazirov.android_graphql.network.GraphQL
import kotlinx.coroutines.launch

class CreateActivity : AppCompatActivity() {
    lateinit var binding: ActivityCreateBinding
    private var name: String = ""
    private var rocket: String = ""
    private var twitter: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        binding.apply {
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

            btnCreate.setOnClickListener {
                if (name != "" && rocket != "" && twitter != "") {
                    insertUser(name, rocket, twitter)
                    finish()
                } else {
                    Toast.makeText(
                        this@CreateActivity,
                        "Please fill the fields first",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun insertUser(name: String, rocket: String, twitter: String) {
        lifecycleScope.launch launchWhenResumed@{
            val result = try {
                GraphQL.get().mutation(
                    InsertUserMutation(name, rocket, twitter)
                ).execute()
            } catch (e: ApolloException) {
                Log.d("CreateActivity", e.toString())
                return@launchWhenResumed
            }
            Log.d("CreateActivity", result.toString())
        }
    }
}