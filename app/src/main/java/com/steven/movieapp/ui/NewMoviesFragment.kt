package com.steven.movieapp.ui


import androidx.fragment.app.Fragment
import com.steven.movieapp.API_KEY
import com.steven.movieapp.base.BaseRefreshFragment

/**
 *   新片榜
 */
class NewMoviesFragment : BaseRefreshFragment() {

    companion object {
        fun newInstance(): Fragment {
            return NewMoviesFragment()
        }
    }

    override fun onRequestData() {
        super.onRequestData()
        movieViewModel.getMovieNewMovies(API_KEY).observe(this, mObserver)
    }
}
