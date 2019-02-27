package com.steven.movieapp

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.steven.movieapp.base.BaseActivity
import com.steven.movieapp.ui.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {


    override fun getLayoutId(): Int = R.layout.activity_main
    override fun initData() {

    }

    override fun initView() {
        val movies = resources.getStringArray(R.array.tab_movies)
        val fragments = ArrayList<Fragment>()

        fragments.add(TheaterMovieFragment.newInstance())
        fragments.add(ComingMovieFragment.newInstance())
        fragments.add(WeeklyMovieFragment.newInstance())
        fragments.add(UsMovieFragment.newInstance())
        fragments.add(NewMoviesFragment.newInstance())
        fragments.add(Top250MovieFragment.newInstance())

        viewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {

            override fun getItem(position: Int): Fragment = fragments[position]

            override fun getPageTitle(position: Int): CharSequence? = movies[position]


            override fun getCount(): Int = movies.size
        }
        viewPager.offscreenPageLimit = fragments.size
        tab.setupWithViewPager(viewPager)
        loop_movie_name.setOnClickListener {
            val intent = Intent(this, SearchMovieActivity::class.java)
            startActivity(intent)
        }
    }

}
