package com.steven.movieapp.base

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.howshea.basemodule.component.fragment.LazyFragment
import com.steven.movieapp.R
import com.steven.movieapp.adapter.MovieAdapter
import com.steven.movieapp.model.BaseResult
import com.steven.movieapp.model.Movie
import com.steven.movieapp.ui.MovieInfoActivity
import com.steven.movieapp.ui.Top250MovieFragment
import com.steven.movieapp.viewmodel.MovieViewModel
import com.steven.movieapp.viewmodel.MovieViewModelFactory
import com.steven.movieapp.widget.LoopTextView
import com.steven.movieapp.widget.recyclerview.OnItemClickListener
import com.steven.movieapp.widget.refreshLoad.DefaultLoadViewCreator
import com.steven.movieapp.widget.refreshLoad.DefaultRefreshViewCreator
import com.steven.movieapp.widget.refreshLoad.LoadRefreshRecyclerView
import com.steven.movieapp.widget.refreshLoad.RefreshRecyclerView
import kotlinx.android.synthetic.main.fragment_base_refresh.*
import kotlinx.android.synthetic.main.load_view.*

/**
 * Description:
 * Data：2/19/2019-3:14 PM
 * @author yanzhiwen
 */
abstract class BaseResultRefreshFragment : LazyFragment(), OnItemClickListener<Movie>,
    RefreshRecyclerView.OnRefreshListener,
    LoadRefreshRecyclerView.OnLoadListener {

    private var adapter: MovieAdapter? = null

    private var movies = arrayListOf<Movie>()

    protected val movieViewModel: MovieViewModel by lazy {
        ViewModelProviders.of(this, MovieViewModelFactory()).get(MovieViewModel::class.java)
    }

    protected val mBaseResultObserver: Observer<BaseResult<List<Movie>>> by lazy {
        Observer<BaseResult<List<Movie>>> {
            if (adapter == null) {
                movies = it.subjects as ArrayList<Movie>
                adapter = MovieAdapter(context!!, R.layout.movie_list_item, movies)
                recyclerView.adapter = adapter
            } else {
                recyclerView.onStopRefresh()
                adapter?.apply {
                    if (this@BaseResultRefreshFragment is Top250MovieFragment) {
                        recyclerView.onStopLoad()
                        if (it.subjects.isNotEmpty() && recyclerView.isLoading()) {
                            movies.addAll(it.subjects)
                        }
                    }
                    notifyDataSetChanged()
                }
            }
            adapter?.apply { setOnItemClickListener(this@BaseResultRefreshFragment) }
            setUpLoopMovieName(it.subjects)
        }
    }

    override fun getLayoutId() = R.layout.fragment_base_refresh

    override fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addRefreshViewCreator(DefaultRefreshViewCreator())
        recyclerView.setOnRefreshListener(this)
        recyclerView.addLoadingView(load_view)
        recyclerView.addEmptyView(empty_view)
        if (this is Top250MovieFragment) {
            recyclerView.addLoadViewCreator(DefaultLoadViewCreator())
            recyclerView.setOnLoadListener(this)
        }
    }

    override fun onItemClick(position: Int, item: Movie) {
        val intent = Intent(context, MovieInfoActivity::class.java)
        intent.putExtra("movie_id", item.id)
        startActivity(intent)
    }


    override fun onRefresh() {
        onRequestData()
    }

    override fun onLoad() {
    }

    private fun setUpLoopMovieName(movies: List<Movie>) {
        val textList = ArrayList<String>()
        movies.forEach { textList.add(it.title + " | " + it.original_title) }
        activity?.apply {
            findViewById<LoopTextView>(R.id.loop_movie_name).setTextList(textList)
        }
    }
}