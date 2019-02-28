package com.steven.movieapp.ui

import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.steven.movieapp.R
import com.steven.movieapp.adapter.MovieAdapter
import com.steven.movieapp.base.BaseActivity
import com.steven.movieapp.model.Movie
import com.steven.movieapp.widget.recyclerview.OnItemClickListener
import com.steven.movieapp.widget.refreshLoad.DefaultLoadViewCreator
import com.steven.movieapp.widget.refreshLoad.DefaultRefreshViewCreator
import com.steven.movieapp.widget.refreshLoad.LoadRefreshRecyclerView
import com.steven.movieapp.widget.refreshLoad.RefreshRecyclerView
import kotlinx.android.synthetic.main.activity_search_movie.*
import kotlinx.android.synthetic.main.load_view.*

class SearchMovieActivity : BaseActivity(), RefreshRecyclerView.OnRefreshListener,
        LoadRefreshRecyclerView.OnLoadListener {


    private var adapter: MovieAdapter? = null
    private var movies = ArrayList<Movie>()
    private var start: Int = 0
    private lateinit var name: String

    override fun getLayoutId(): Int = R.layout.activity_search_movie

    override fun initData() {
    }

    override fun initView() {
        load_view.visibility = View.GONE
        rv_movies.layoutManager = LinearLayoutManager(this)
        rv_movies.itemAnimator = DefaultItemAnimator()
        rv_movies.addRefreshViewCreator(DefaultRefreshViewCreator())
        rv_movies.addLoadViewCreator(DefaultLoadViewCreator())
        rv_movies.setOnRefreshListener(this)
        rv_movies.setOnLoadListener(this)

        iv_back.setOnClickListener {
            finish()
        }
        search.setOnClickListener {
            if (TextUtils.isEmpty(search_name.text)) {
                Toast.makeText(this, "请输入电影名称", Toast.LENGTH_SHORT).show()
            } else {
                searchByName(search_name.text.toString())
            }
        }
    }

    private fun searchByName(name: String) {
        this.name = name
        load_view.visibility = View.VISIBLE

        if (this.movies.size > 0) {
            adapter?.apply { this.notifyItemRangeRemoved(0, movies.size) }
            this.movies.clear()
            adapter = null
        }
        movieViewModel.getMovieSearchByTag(name, 0, 10).observe(this, Observer {
            this.movies = it.subjects as ArrayList<Movie>
            showMovie()
        })
    }

    private fun showMovie() {
        if (load_view.visibility == View.VISIBLE) {
            load_view.visibility = View.GONE
        }
        if (adapter == null) {
            adapter = MovieAdapter(this, R.layout.movie_list_item, this.movies)
            rv_movies.adapter = adapter
        } else {
            rv_movies.onStopRefresh()
            adapter?.apply { notifyDataSetChanged() }
            rv_movies.onStopLoad()
        }
        adapter?.setOnItemClickListener(object : OnItemClickListener<Movie> {
            override fun onItemClick(position: Int, item: Movie) {
                val intent = Intent(this@SearchMovieActivity, MovieInfoActivity::class.java)
                intent.putExtra("movie_id", item.id)
                startActivity(intent)
            }
        })

    }

    override fun onRefresh() {
        showMovie()
    }

    override fun onLoad() {
        start += 10
        movieViewModel.getMovieSearchByTag(name, start, 10).observe(this, Observer {
            this.movies.addAll(it.subjects)
            showMovie()
        })
    }
}
