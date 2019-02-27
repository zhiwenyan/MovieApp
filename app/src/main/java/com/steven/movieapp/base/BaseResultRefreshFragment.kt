package com.steven.movieapp.base

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.steven.movieapp.R
import com.steven.movieapp.adapter.MovieAdapter
import com.steven.movieapp.model.BaseResult
import com.steven.movieapp.model.Movie
import com.steven.movieapp.widget.recyclerview.OnItemClickListener
import com.steven.movieapp.ui.MovieInfoActivity
import com.steven.movieapp.ui.Top250MovieFragment
import com.steven.movieapp.viewmodel.MovieViewModel
import com.steven.movieapp.widget.LoopTextView
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
abstract class BaseResultRefreshFragment : BaseFragment(), OnItemClickListener<Movie>, RefreshRecyclerView.OnRefreshListener,
    LoadRefreshRecyclerView.OnLoadListener {

    private var adapter: MovieAdapter? = null
    private var movies = ArrayList<Movie>()
    protected val movieViewModel: MovieViewModel by lazy {
        ViewModelProviders.of(this).get(MovieViewModel::class.java)

    }
    protected lateinit var mBaseResultObserver: Observer<BaseResult<List<Movie>>>

    override fun getLayoutId() = R.layout.fragment_base_refresh

    override fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(mContext)
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

    override fun initData() {

    }

    override fun onRequestData() {
        mBaseResultObserver = Observer {
            if (adapter == null) {
                movies = it.subjects as ArrayList<Movie>
                adapter = MovieAdapter(mContext!!, R.layout.movie_list_item, movies)
                recyclerView.adapter = adapter
                adapter?.apply { setOnItemClickListener(this@BaseResultRefreshFragment) }
            } else {
                recyclerView.onStopRefresh()
                adapter?.apply {
                    if (this@BaseResultRefreshFragment is Top250MovieFragment) {
                        recyclerView.onStopLoad()
                        if (it.subjects.isNotEmpty()) {
                            movies.addAll(it.subjects)
                        }
                    }
                    notifyDataSetChanged()
                }
            }
            setUpLoopMovieName(it.subjects)
        }

    }


    override fun onItemClick(position: Int, item: Movie) {
        val intent = Intent(mContext, MovieInfoActivity::class.java)
        intent.putExtra("id", item.id)
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