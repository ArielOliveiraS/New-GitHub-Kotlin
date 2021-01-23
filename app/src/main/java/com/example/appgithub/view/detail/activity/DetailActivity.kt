package com.example.appgithub.view.detail.activity

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appgithub.R
import com.example.appgithub.model.Item
import com.example.appgithub.model.Repository
import com.example.appgithub.view.detail.adapter.DetailRepositoryAdapter
import com.example.appgithub.viewmodel.RepositoryViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*

class DetailActivity : AppCompatActivity() {

    private val list = mutableListOf<Repository>()
    private val adapter = DetailRepositoryAdapter(list)
    private lateinit var viewModel: RepositoryViewModel
    private val factory = RepositoryViewModel.Factory()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initViews()
        setupBackButton()
        setupLoading()

        val item = intent.extras?.getParcelable<Item>("result")

        item!!.owner.let {
            Picasso.get().load(it.avatar_url).error(R.drawable.ic_avatar).into(userAvatarView)
            nameView.text = it.login
        }

        viewModel.getMyRepositories(item.owner.login)
        viewModel.repositorytResult.observe(this, Observer {
            adapter.updateList(it)
        })

        recyclerMyRepositories.layoutManager = LinearLayoutManager(this)
    }

    private fun initViews() {
        viewModel = ViewModelProviders.of(this, factory).get(RepositoryViewModel::class.java)
        recyclerMyRepositories.adapter = adapter
    }

    private fun setupBackButton(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    private fun setupLoading() {
        val colorProgress = Color.parseColor("#CAC8C3")
        viewModel.loadingResult.observe(this, Observer {
            if (it) {
                detailProgressBar.visibility = View.VISIBLE
                detailProgressBar.indeterminateTintList = ColorStateList.valueOf(colorProgress)
            } else {
                detailProgressBar.visibility = View.GONE
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }
}