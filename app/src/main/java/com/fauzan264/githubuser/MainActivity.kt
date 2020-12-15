package com.fauzan264.githubuser

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.fauzan264.githubuser.adapter.UserAdapter
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.fauzan264.githubuser.model.User

class MainActivity : AppCompatActivity() {
        private lateinit var adapter: UserAdapter
        private lateinit var mainViewModel: MainViewModel
        private lateinit var sweetAlertDialog: SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_list.setHasFixedSize(true)
        adapter = UserAdapter()
        rv_list.layoutManager   = LinearLayoutManager(this)
        rv_list.adapter         = adapter
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        mainViewModel.getUsers().observe(this, { userItems ->
            if (userItems.isNotEmpty()) {
                showMainImage(false)
                adapter.setData(userItems)
                showLoading(false)
                showRecyclerList()
            } else {
                showMainImage(false)
                adapter.setData(userItems)
                showRecyclerList()
                showLoading(false)
                showNotFound()

            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(username: String): Boolean {
                showMainImage(false)
                showLoading(true)
                mainViewModel.setUser(username)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_change_settings -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showLoading(state: Boolean) {
        if (state) {
            sweetAlertDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
            sweetAlertDialog.progressHelper.barColor = Color.parseColor("#800000")
            sweetAlertDialog.titleText = getString(R.string.loading)
            sweetAlertDialog.setCancelable(true)
            sweetAlertDialog.show()
        } else {
            sweetAlertDialog.dismiss()
        }
    }

    private fun showNotFound() {
        SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
            .setTitleText(R.string.not_found)
            .setContentText(getString(R.string.username_not_found))
            .show()
    }

    fun showMainImage(state: Boolean) {
        if (state) {
            img_search.visibility = View.VISIBLE
            txt_search.visibility = View.VISIBLE
        } else {
            img_search.visibility = View.GONE
            txt_search.visibility = View.GONE
        }
    }

    private fun showRecyclerList() {
        rv_list.adapter = adapter
        val listUserAdapter= adapter

        listUserAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
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