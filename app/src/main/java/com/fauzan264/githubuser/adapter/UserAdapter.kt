package com.fauzan264.githubuser.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.fauzan264.githubuser.R
import com.fauzan264.githubuser.model.User
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter internal constructor(private val context: Context): BaseAdapter() {
    internal var users = arrayListOf<User>()
    override fun getCount(): Int = users.size

    override fun getItem(i: Int): Any = users[i]

    override fun getItemId(i: Int): Long = i.toLong()

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        var itemView = view
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_user, viewGroup, false)
        }

        val viewHolder = ViewHolder(itemView as View)

        val user = getItem(position) as User
        viewHolder.bind(user)
        return itemView
    }

    private inner class ViewHolder constructor(view: View) {
        private val txtName: TextView = view.findViewById(R.id.list_txt_name)
        private val txtLocation: TextView = view.findViewById(R.id.list_txt_location)
        private val imgPhoto: CircleImageView = view.findViewById(R.id.list_img_photo)

        internal fun bind(user: User) {
            txtName.text = user.name
            txtLocation.text = user.location
            imgPhoto.setImageResource(user.photo)
        }
    }
}