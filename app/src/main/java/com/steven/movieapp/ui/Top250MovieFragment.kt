package com.steven.movieapp.ui


import androidx.fragment.app.Fragment
import com.steven.movieapp.API_KEY
import com.steven.movieapp.base.BaseRefreshFragment

/**
 * 口碑榜
 */
class Top250MovieFragment : BaseRefreshFragment() {

    companion object {
        fun newInstance(): Fragment {
            return Top250MovieFragment()
        }
    }

    override fun onRequestData() {
        super.onRequestData()
        movieViewModel.getTop250Movie(API_KEY,0,10).observe(this, mObserver)
    }
}
