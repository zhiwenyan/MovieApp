package com.steven.movieapp.base

import androidx.lifecycle.Observer
import com.steven.movieapp.R
import com.steven.movieapp.adapter.MovieAdapter
import com.steven.movieapp.model.Movie
import kotlinx.android.synthetic.main.fragment_base_refresh.*

/**
 * Description:
 * Data：2/19/2019-3:14 PM
 * @author yanzhiwen
 */
abstract class BaseListRefreshFragment : BaseRefreshFragment() {


    protected lateinit var mListObserver: Observer<List<Movie>>

    override fun getLayoutId() = R.layout.fragment_base_refresh


    override fun onRequestData() {
        mListObserver = Observer {
            if (adapter == null) {
                adapter = MovieAdapter(mContext!!, R.layout.movie_list_item, it)
                recyclerView.adapter = adapter
                adapter?.apply { setOnItemClickListener(this@BaseListRefreshFragment) }
            } else {
                adapter?.apply { notifyDataSetChanged() }
            }
            swipeRefreshLayout.isRefreshing = false

        }
    }
}