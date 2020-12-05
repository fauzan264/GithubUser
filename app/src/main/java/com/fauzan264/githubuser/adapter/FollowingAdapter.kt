package com.fauzan264.githubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fauzan264.githubuser.R
import com.fauzan264.githubuser.model.Following
import kotlinx.android.synthetic.main.item_following.view.*

class FollowingAdapter : RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {
    private val mData = ArrayList<Following>()

    fun setData(items: ArrayList<Following>) {
        mData.clear()
        mData.addAll(items)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FollowingAdapter.FollowingViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_following, viewGroup, false)
        return FollowingViewHolder(mView)
    }

    override fun onBindViewHolder(followingViewHolder: FollowingViewHolder, position: Int) {
        followingViewHolder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    inner class FollowingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(following: Following) {
            with(itemView) {
                Glide.with(context)
                    .load(following.photo)
                    .apply(RequestOptions().override(40,40))
                    .into(list_img_following)
                list_name_following.text = following.username
            }
        }
    }
}