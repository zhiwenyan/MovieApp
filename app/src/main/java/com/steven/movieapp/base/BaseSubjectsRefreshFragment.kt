package com.steven.movieapp.base

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.steven.movieapp.R
import com.steven.movieapp.adapter.WeeklyAdapter
import com.steven.movieapp.model.BaseSubjects
import com.steven.movieapp.model.Weekly
import com.steven.movieapp.ui.MovieInfoActivity
import com.steven.movieapp.viewmodel.MovieViewModel
import com.steven.movieapp.widget.LoopTextView
import com.steven.movieapp.widget.recyclerview.OnItemClickListener
import com.steven.movieapp.widget.refreshLoad.DefaultRefreshViewCreator
import com.steven.movieapp.widget.refreshLoad.RefreshRecyclerView
import kotlinx.android.synthetic.main.fragment_base_refresh.*
import kotlinx.android.synthetic.main.load_view.*

/**
 * Description:
 * Data：2/19/2019-3:14 PM
 * @author yanzhiwen
 */
abstract class BaseSubjectsRefreshFragment : BaseFragment(), OnItemClickListener<Weekly>,
    RefreshRecyclerView.OnRefreshListener {

    private var adapter: WeeklyAdapter? = null
    protected val movieViewModel: MovieViewModel by lazy {
        ViewModelProviders.of(this).get(MovieViewModel::class.java)

    }
    protected lateinit var mBaseSubjectsObserver: Observer<BaseSubjects<Weekly>>

    override fun getLayoutId() = R.layout.fragment_base_refresh

    override fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addRefreshViewCreator(DefaultRefreshViewCreator())
        recyclerView.setOnRefreshListener(this)
        recyclerView.addLoadingView(load_view)
        recyclerView.addEmptyView(empty_view)
    }

    override fun initData() {

    }

    override fun onRequestData() {
        mBaseSubjectsObserver = Observer {
            if (adapter == null) {
                adapter = WeeklyAdapter(mContext!!, R.layout.movie_list_item, it.subjects)
                recyclerView.adapter = adapter
                adapter?.apply { setOnItemClickListener(this@BaseSubjectsRefreshFragment) }
            } else {
                recyclerView.onStopRefresh()
                adapter?.apply { notifyDataSetChanged() }
            }
            setUpLoopMovieName(it.subjects)
        }
    }


    override fun onItemClick(position: Int, item: Weekly) {
        val intent = Intent(mContext, MovieInfoActivity::class.java)
        intent.putExtra("id", item.subject.id)
        startActivity(intent)
    }


    override fun onRefresh() {
        onRequestData()
    }

    private fun setUpLoopMovieName(movies: List<Weekly>) {
        val textList = ArrayList<String>()
        movies.forEach { textList.add(it.subject.title + " | " + it.subject.original_title) }
        activity?.apply {
            findViewById<LoopTextView>(R.id.loop_movie_name).setTextList(textList)
        }
    }
}