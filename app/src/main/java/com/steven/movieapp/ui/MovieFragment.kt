package com.steven.movieapp.ui


import androidx.fragment.app.Fragment
import com.steven.movieapp.R
import kotlinx.android.synthetic.main.fragment_movie.*


class MovieFragment : BaseFragment() {

    companion object {
        fun newInstance(): Fragment {
            return MovieFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_movie

    override fun initView() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent)
        swipeRefreshLayout.setOnRefreshListener {

        }
    }

    override fun initData() {
    }


}
