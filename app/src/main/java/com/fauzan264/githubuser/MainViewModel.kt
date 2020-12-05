package com.fauzan264.githubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fauzan264.githubuser.model.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class MainViewModel : ViewModel() {
    val listUsers = MutableLiveData<ArrayList<User>>()

    fun setUser(username: String) {
        val listItems = ArrayList<User>()

        val apiKey = "token ${BuildConfig.API_KEY}"
        val url = "https://api.github.com/search/users?q=$username"

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
                    val responseObject = JSONObject(result)
                    val items = responseObject.getString("items")
                    val lists = JSONArray(items)
                    for (i in 0 until lists.length()) {
                        val user = lists.getJSONObject(i)
                        val dataUser = User()
                        dataUser.photo = user.getString("avatar_url")
                        dataUser.username = user.getString("login")

                        listItems.add(dataUser)
                    }
                    listUsers.postValue(listItems)
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

    fun getUsers(): LiveData<ArrayList<User>> = listUsers
}