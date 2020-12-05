package com.fauzan264.githubuser

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fauzan264.githubuser.adapter.SectionPagerAdapter
import com.fauzan264.githubuser.model.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_detail.*
import org.json.JSONObject

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val user = intent.getParcelableExtra<User>(EXTRA_USER)
        val username = user?.username
        user?.let {
            tv_username.text = it.username
            Glide.with(this@DetailActivity)
                .load(it.photo)
                .apply(RequestOptions().override(55, 55))
                .into(img_profile)
        }
        getDetailUser(username)

        val sectionsPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.username = username
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

        supportActionBar?.elevation = 0f
    }

    private fun getDetailUser(username: String?) {
        val apiKey = "token ${BuildConfig.API_KEY}"
        val url = "https://api.github.com/users/$username"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", apiKey)
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {
                val result = String(responseBody)
                val responseObject = JSONObject(result)

                tv_name.text = responseObject.getString("name")
                tv_location.text = responseObject.getString("location")
                tv_company.text = responseObject.getString("company")
                tv_followers.text = responseObject.getString("followers")
                tv_following.text = responseObject.getString("following")
                tv_repo.text = responseObject.getString("public_repos")
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }

        })
    }
}