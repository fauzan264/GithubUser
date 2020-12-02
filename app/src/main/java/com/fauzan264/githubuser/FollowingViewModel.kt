package com.fauzan264.githubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fauzan264.githubuser.model.Following
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FollowingViewModel : ViewModel() {
    val listFollowing = MutableLiveData<ArrayList<Following>>()

    internal fun setFollowing(username: String) {
        val listItems = ArrayList<Following>()

        val apiKey = "token cc1a12cad98f867dbe87ab463bf49697a0f87045"
        val url = "https://api.github.com/users/${username}/following"

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
                        val following = jsonArray.getJSONObject(i)
                        val followingItems = Following()
                        followingItems.username = following.getString("login")
                        followingItems.photo = following.getString("avatar_url")

                        listItems.add(followingItems)
                    }
                    listFollowing.postValue(listItems)
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

    fun getFollowing(): LiveData<ArrayList<Following>> = listFollowing
}