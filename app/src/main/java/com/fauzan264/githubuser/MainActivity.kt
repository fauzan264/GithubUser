package com.fauzan264.githubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.fauzan264.githubuser.adapter.UserAdapter
import com.fauzan264.githubuser.model.User
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val list = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_list.setHasFixedSize(true)

        list.addAll(getListUser())
        showRecyclerList()
    }

    private fun getListUser(): ArrayList<User> {
        val dataPhoto = resources.obtainTypedArray(R.array.avatar)
        val dataName = resources.getStringArray(R.array.name)
        val dataLocation = resources.getStringArray(R.array.location)
        val dataCompany = resources.getStringArray(R.array.company)
        val dataUsername = resources.getStringArray(R.array.username)
        val dataFollowers = resources.getStringArray(R.array.followers)
        val dataFollowing = resources.getStringArray(R.array.following)
        val dataRepo = resources.getStringArray(R.array.repository)

        val listUser = ArrayList<User>()
        for (pos in dataName.indices) {
            val user = User(
                dataPhoto.getResourceId(pos, -1),
                dataName[pos],
                dataLocation[pos],
                dataCompany[pos],
                dataUsername[pos],
                dataFollowers[pos],
                dataFollowing[pos],
                dataRepo[pos]
            )
            listUser.add(user)
        }
        return listUser
    }

    private fun showRecyclerList() {
        rv_list.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = UserAdapter(list)
        rv_list.adapter = listUserAdapter

        listUserAdapter.setOneItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(user: User) {
        val moveDetail = Intent(this@MainActivity, DetailActivity::class.java)
        moveDetail.putExtra(DetailActivity.EXTRA_USER, user)
        startActivity(moveDetail)
    }
}