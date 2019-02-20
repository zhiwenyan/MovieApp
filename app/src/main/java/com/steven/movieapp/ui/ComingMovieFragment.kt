package com.steven.movieapp.ui


import androidx.fragment.app.Fragment
import com.steven.movieapp.API_KEY
import com.steven.movieapp.base.BaseRefreshFragment

/**
 * 即将上映
 */
class ComingMovieFragment : BaseRefreshFragment() {

    companion object {
        fun newInstance(): Fragment {
            return ComingMovieFragment()
        }
    }

    override fun onRequestData() {
        super.onRequestData()
        movieViewModel.getComingSoon(API_KEY).observe(this, mObserver)
    }
}
