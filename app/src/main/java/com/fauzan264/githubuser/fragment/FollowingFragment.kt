package com.fauzan264.githubuser.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fauzan264.githubuser.FollowingViewModel
import com.fauzan264.githubuser.R
import com.fauzan264.githubuser.adapter.FollowingAdapter
import kotlinx.android.synthetic.main.fragment_following.*

class FollowingFragment : Fragment() {
    companion object {
        internal lateinit var adapter: FollowingAdapter
        private lateinit var followingViewModel: FollowingViewModel
        private const val ARG_USERNAME = "username"

        fun newInstance(username: String?): FollowingFragment {
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.getString(ARG_USERNAME)

        showLoading(true)
        rv_following.setHasFixedSize(true)
        adapter = FollowingAdapter()
        showRecyclerList()
        if (username != null) {
            followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)
            followingViewModel.setFollowing(username)
            followingViewModel.getFollowing().observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    adapter.setData(it)
                    showLoading(false)
                    rv_following.adapter = adapter
                }
            })
        }

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            pb_following.visibility = View.VISIBLE
        } else {
            pb_following.visibility = View.GONE
        }
    }

    private fun showRecyclerList() {
        rv_following.layoutManager   = LinearLayoutManager(activity)
        rv_following.adapter         = adapter
    }
}