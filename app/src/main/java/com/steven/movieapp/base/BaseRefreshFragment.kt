package com.steven.movieapp.base

import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.steven.movieapp.R
import com.steven.movieapp.adapter.MovieAdapter
import com.steven.movieapp.model.BaseResult
import com.steven.movieapp.model.Movie
import com.steven.movieapp.recyclerview.OnItemClickListener
import com.steven.movieapp.viewmodel.MovieViewModel
import com.steven.movieapp.widget.LoopTextView
import kotlinx.android.synthetic.main.fragment_movie.*

/**
 * Description:
 * Data：2/19/2019-3:14 PM
 * @author yanzhiwen
 */
abstract class BaseRefreshFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener, OnItemClickListener<Movie> {

    protected var adapter: MovieAdapter? = null

    protected lateinit var movieViewModel: MovieViewModel

    protected lateinit var mObserver: Observer<BaseResult<List<Movie>>>

    override fun getLayoutId() = R.layout.fragment_base_refresh

    override fun initView() {
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(mContext!!, R.color.colorAccent))
        swipeRefreshLayout.setOnRefreshListener(this)
        swipeRefreshLayout.isRefreshing = true
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                adapter?.apply {
                    getItemView().animation = AnimationUtils.loadAnimation(mContext, R.anim.scale_in_item)

                }
            }
        })
    }

    override fun onRequestData() {
        mObserver = Observer {
            if (adapter == null) {
                adapter = MovieAdapter(mContext!!, R.layout.movie_list_item, it.subjects)
                recyclerView.adapter = adapter
                adapter?.apply { setOnItemClickListener(this@BaseRefreshFragment) }
            } else {
                adapter?.apply { notifyDataSetChanged() }
            }
            swipeRefreshLayout.isRefreshing = false

            setUpLoopMovieName(it.subjects)

        }

    }

    override fun initData() {
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)

    }

    override fun onItemClick(position: Int, item: Movie) {
    }

    override fun onRefresh() {
        onRequestData()
    }


    private fun setUpLoopMovieName(movies: List<Movie>) {
        val textList = ArrayList<String>()
        movies.forEach { textList.add(it.title + " | " + it.original_title) }
        activity?.apply {
            findViewById<LoopTextView>(R.id.loop_movie_name).setTextList(textList)
        }
    }
}