package com.fauzan264.githubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fauzan264.githubuser.R
import com.fauzan264.githubuser.model.User
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter(private val listUser: ArrayList<User>) : RecyclerView.Adapter<UserAdapter.ListViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOneItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) = holder.bind(listUser[position])

    override fun getItemCount(): Int = listUser.size


    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User){
            with(itemView) {
                Glide.with(itemView.context)
                    .load(user.photo)
                    .apply(RequestOptions().override(55,55))
                    .into(list_img_photo)

                list_txt_name.text = user.name
                list_txt_location.text = user.location

                itemView.setOnClickListener{ onItemClickCallback?.onItemClicked(user)}
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}