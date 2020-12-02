package com.fauzan264.githubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fauzan264.githubuser.model.Followers
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import java.lang.Exception

class FollowersViewModel : ViewModel() {
    val listFollowers = MutableLiveData<ArrayList<Followers>>()

    internal fun setFollowers(username: String) {
        val listItems = ArrayList<Followers>()

        val apiKey = "token cc1a12cad98f867dbe87ab463bf49697a0f87045"
        val url = "https://api.github.com/users/${username}/followers"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", apiKey)
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val followers = jsonArray.getJSONObject(i)
                        val followersItems = Followers()
                        followersItems.username = followers.getString("login")
                        followersItems.photo = followers.getString("avatar_url")

                        listItems.add(followersItems)
                    }
                    listFollowers.postValue(listItems)

                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }

        })
    }

    fun getFollowers(): LiveData<ArrayList<Followers>> = listFollowers

}