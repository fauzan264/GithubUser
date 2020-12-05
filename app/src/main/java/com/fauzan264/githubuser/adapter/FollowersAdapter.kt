package com.fauzan264.githubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fauzan264.githubuser.R
import com.fauzan264.githubuser.model.Followers
import kotlinx.android.synthetic.main.item_followers.view.*

class FollowersAdapter : RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder>() {
    private val mData = ArrayList<Followers>()

    fun setData(items: ArrayList<Followers>) {
        mData.clear()
        mData.addAll(items)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FollowersViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_followers, viewGroup, false)
        return FollowersViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    inner class FollowersViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        fun bind(followers: Followers) {
            with(itemView) {
                Glide.with(context)
                    .load(followers.photo)
                    .apply(RequestOptions().override(40,40))
                    .into(list_img_followers)
                list_name_followers.text = followers.username
            }
        }
    }
}