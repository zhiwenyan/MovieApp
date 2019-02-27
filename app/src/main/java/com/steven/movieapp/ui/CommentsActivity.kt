package com.steven.movieapp.ui

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.steven.movieapp.API_KEY
import com.steven.movieapp.R
import com.steven.movieapp.adapter.CommentsAdapter
import com.steven.movieapp.base.BaseActivity
import com.steven.movieapp.model.Comment
import com.steven.movieapp.widget.recyclerview.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_comments.*
import kotlinx.android.synthetic.main.load_view.*

class CommentsActivity : BaseActivity() {


    private val movieId: String by lazy {
        intent.getStringExtra("movieId")
    }

    override fun getLayoutId() = R.layout.activity_comments

    override fun initData() {
    }

    override fun initView() {
        supportActionBar!!.title = ""
        fab.setOnClickListener{
            rv_more_comments.scrollToPosition(0)
        }
    }

    override fun onRequestData() {
        movieViewModel.getComments(movieId, API_KEY).observe(this, Observer {
            supportActionBar!!.title = "热评(" + it.count + ")"
            showComments(it.comments)
        })

    }

    private fun showComments(comments: List<Comment>) {
        if (load_view.visibility == View.VISIBLE) {
            load_view.visibility = View.GONE
        }
        rv_more_comments.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_more_comments.adapter = CommentsAdapter(this, R.layout.comment_item, comments)
        rv_more_comments.addItemDecoration(
            DividerItemDecoration(
                this,
                R.drawable.ic_divider_item,
                LinearLayoutManager.VERTICAL
            )
        )
    }
}
