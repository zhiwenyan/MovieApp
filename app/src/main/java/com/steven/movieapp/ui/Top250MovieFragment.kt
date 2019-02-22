package com.steven.movieapp.ui


import androidx.fragment.app.Fragment
import com.steven.movieapp.API_KEY
import com.steven.movieapp.base.BaseRefreshFragment

/**
 * 口碑榜
 */
class Top250MovieFragment : BaseRefreshFragment() {
    private var start: Int = 0
    private var count: Int = 10

    companion object {
        fun newInstance(): Fragment {
            return Top250MovieFragment()
        }
    }

    override fun onRequestData() {
        super.onRequestData()
        movieViewModel.getTop250Movie(API_KEY, start, count).observe(this, mObserver)
    }

    override fun onLoad() {
        super.onLoad()
        start += 10
        movieViewModel.getTop250Movie(API_KEY, start, count).observe(this, mObserver)
    }
}
