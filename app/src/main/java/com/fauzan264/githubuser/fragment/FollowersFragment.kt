package com.fauzan264.githubuser.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fauzan264.githubuser.FollowersViewModel
import com.fauzan264.githubuser.R
import com.fauzan264.githubuser.adapter.FollowersAdapter
import kotlinx.android.synthetic.main.fragment_followers.*

class FollowersFragment : Fragment() {
    companion object {
        internal lateinit var adapter: FollowersAdapter
        private lateinit var followersViewModel: FollowersViewModel
        private const val ARG_USERNAME = "username"

        fun newInstance(username: String?): FollowersFragment {
            val fragment = FollowersFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(ARG_USERNAME)
        showLoading(true)
        rv_followers.setHasFixedSize(true)
        adapter = FollowersAdapter()
        showRecyclerList()
        if (username != null) {
            followersViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel::class.java)
            followersViewModel.setFollowers(username)
            followersViewModel.getFollowers().observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    adapter.setData(it)
                    showLoading(false)
                    rv_followers.adapter = adapter
                }
            })
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            pb_followers.visibility = View.VISIBLE
        } else {
            pb_followers.visibility = View.GONE
        }
    }

    private fun showRecyclerList() {
        rv_followers.layoutManager   = LinearLayoutManager(activity)
        rv_followers.adapter         = adapter
    }

}