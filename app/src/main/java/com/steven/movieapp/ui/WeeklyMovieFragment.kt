package com.steven.movieapp.ui


import androidx.fragment.app.Fragment
import com.steven.movieapp.base.BaseRefreshFragment

/**
 * 口碑榜
 */
class WeeklyMovieFragment : BaseRefreshFragment() {

    companion object {
        fun newInstance(): Fragment {
            return WeeklyMovieFragment()
        }
    }

    override fun onRequestData() {
        super.onRequestData()
    }
}
