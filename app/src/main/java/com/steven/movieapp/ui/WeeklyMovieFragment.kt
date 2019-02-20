package com.steven.movieapp.ui


import androidx.fragment.app.Fragment
import com.steven.movieapp.API_KEY
import com.steven.movieapp.base.BaseListRefreshFragment

/**
 * 口碑榜
 */
class WeeklyMovieFragment : BaseListRefreshFragment() {

    companion object {
        fun newInstance(): Fragment {
            return WeeklyMovieFragment()
        }
    }

    override fun onRequestData() {
        super.onRequestData()
        movieViewModel.getMovieWeekly(API_KEY).observe(this, mListObserver)
    }
}
