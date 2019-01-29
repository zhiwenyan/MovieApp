package com.steven.movieapp.ui


import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.steven.movieapp.Constants
import com.steven.movieapp.R
import com.steven.movieapp.model.BaseResult
import com.steven.movieapp.model.Movie
import com.steven.movieapp.recyclerview.OnItemClickListener
import com.steven.movieapp.viewmodel.MovieViewModel
import com.steven.movieapp.widget.LoopTextView
import kotlinx.android.synthetic.main.fragment_movie.*


class MovieFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener, OnItemClickListener {


    lateinit var movieViewModel: MovieViewModel
    var adapter: MovieAdapter? = null

    companion object {
        fun newInstance(): Fragment {
            return MovieFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_movie

    override fun initView() {
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(mContext!!, R.color.colorAccent))
        swipeRefreshLayout.setOnRefreshListener(this)
        swipeRefreshLayout.isRefreshing = true
        recyclerView.layoutManager = LinearLayoutManager(mContext!!, LinearLayoutManager.VERTICAL, false)

    }

    override fun initData() {
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
    }

    override fun onRequestData() {

        movieViewModel.getInTheaters(Constants.API_KEY).observe(this, Observer<BaseResult<List<Movie>>> { result ->
            if (adapter == null) {
                adapter = MovieAdapter(mContext!!, R.layout.movie_item, result!!.subjects)
                recyclerView.adapter = adapter
                adapter?.setOnItemClickListener(this)
                setUpMovieName(result.subjects)
            } else {
                adapter?.notifyDataSetChanged()
            }
            swipeRefreshLayout.isRefreshing = false

        })
    }

    override fun onItemClick(position: Int) {
    }

    override fun onRefresh() {
        onRequestData()
    }

    private fun setUpMovieName(movies: List<Movie>) {
        val textList = ArrayList<String>()
        movies.forEach { textList.add(it.title) }
        activity!!.findViewById<LoopTextView>(R.id.loop_movie_name)!!.setTextList(textList)

    }

}
