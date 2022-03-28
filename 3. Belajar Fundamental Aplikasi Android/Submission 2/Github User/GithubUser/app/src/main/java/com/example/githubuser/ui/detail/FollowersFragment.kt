package com.example.githubuser.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.R
import com.example.githubuser.network.FollowResponseItem
import com.example.githubuser.network.follow.FollowApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersFragment : Fragment() {
    private lateinit var root: View
    private lateinit var rvFollowers: RecyclerView
    private var listFollowers = ArrayList<FollowResponseItem>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        showRecyclerList()
    }

    private fun showRecyclerList() {
        rvFollowers.layoutManager = LinearLayoutManager(activity)
        val listFollowersAdapter = FollowAdapter(listFollowers)
        rvFollowers.adapter = listFollowersAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_followers, container, false)
        rvFollowers = root.findViewById(R.id.rv_followers)

        if (arguments != null) {
            val username = arguments?.getString(EXTRA_FOLLOWERS)
            getFollowers(username!!)
        }

        return root
    }

    private fun getFollowers(username: String) {
        val tvError = root.findViewById<TextView>(R.id.followers_error)
        showLoading(true)

        val client = FollowApiConfig.getFollowApiService().getFollowers(username)
        client.enqueue(object : Callback<List<FollowResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowResponseItem>>,
                response: Response<List<FollowResponseItem>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        if (responseBody.isEmpty()) {
                            tvError.visibility = View.VISIBLE
                        } else {
                            tvError.visibility = View.GONE
                            listFollowers.addAll(responseBody)
                            Log.d("Jumlah: ", listFollowers.size.toString())
                        }
                    } else {
                        Log.d("Jumlah: ", "null")
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowResponseItem>>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        val pgBar: ProgressBar = root.findViewById(R.id.progressBar3)
        val tvError = root.findViewById<TextView>(R.id.followers_error)

        if (isLoading) {
            pgBar.visibility = View.VISIBLE
            tvError.visibility = View.GONE

        } else {
            pgBar.visibility = View.GONE
        }
    }

    companion object {
        const val EXTRA_FOLLOWERS = "extra_followers"
        private const val TAG = "FollowersFragment"
    }
}