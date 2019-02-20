package com.steven.movieapp.ui


import androidx.fragment.app.Fragment
import com.steven.movieapp.API_KEY
import com.steven.movieapp.base.BaseRefreshFragment

/**
 * 院线正在热映
 */
class TheaterMovieFragment : BaseRefreshFragment() {

    companion object {
        fun newInstance(): Fragment {
            return TheaterMovieFragment()
        }
    }

    override fun onRequestData() {
        super.onRequestData()
        movieViewModel.getInTheaters(API_KEY).observe(this, mObserver)
    }
}