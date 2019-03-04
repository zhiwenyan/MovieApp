package com.steven.movieapp.base

import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
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


    private var movies = arrayListOf<Movie>()

    private val adapter: MovieAdapter  by lazy {
        MovieAdapter(activity!!, R.layout.movie_list_item, movies)
    }

    protected val movieViewModel: MovieViewModel by lazy {
        ViewModelProviders.of(this, MovieViewModelFactory()).get(MovieViewModel::class.java)
    }

    protected val mBaseResultObserver: Observer<BaseResult<List<Movie>>> by lazy {
        Observer<BaseResult<List<Movie>>> {
            if (movies.isEmpty()) {
                this.movies = it.subjects as ArrayList<Movie>
                rv_movies.adapter = adapter
            } else {
                rv_movies.onStopRefresh()
                if (this is Top250MovieFragment) {
                    if (it.subjects.isNotEmpty() && rv_movies.isLoading()) {
                        movies.addAll(it.subjects)
                        rv_movies.onStopLoad()
                    }
                }
                adapter.notifyDataSetChanged()
            }
            adapter.setOnItemClickListener(this)
            setUpLoopMovieName(it.subjects)
        }
    }

    override fun getLayoutId() = R.layout.fragment_base_refresh

    override fun initView() {
        rv_movies.layoutManager = LinearLayoutManager(context)
        rv_movies.itemAnimator = DefaultItemAnimator()
        rv_movies.addRefreshViewCreator(DefaultRefreshViewCreator())
        rv_movies.setOnRefreshListener(this)
        rv_movies.addLoadingView(load_view)
        rv_movies.addEmptyView(empty_view)
        if (this is Top250MovieFragment) {
            rv_movies.addLoadViewCreator(DefaultLoadViewCreator())
            rv_movies.setOnLoadListener(this)
        }
    }

    override fun onItemClick(view: View, position: Int, item: Movie) {
        val intent = Intent(context, MovieInfoActivity::class.java)
        intent.putExtra("movie_id", item.id)
        val v = view.findViewById<ImageView>(R.id.iv_movie)
        val options =
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity!!,
                v, getString(R.string.transition_movie_image)
            )
        startActivity(intent, options.toBundle())
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