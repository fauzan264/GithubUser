package com.fauzan264.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fauzan264.githubuser.model.User
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val user = intent.getParcelableExtra<User>(EXTRA_USER)
        user?.let {
            tv_name.text = it.name
            tv_location.text = it.location
            tv_company.text = it.company
            tv_username.text = it.username
            tv_followers.text = it.followers
            tv_following.text = it.following
            tv_repo.text = it.repo
            img_profile.setImageResource(it.photo)
        }

    }
}