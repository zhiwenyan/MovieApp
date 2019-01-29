package com.steven.movieapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.steven.movieapp.ui.BaseActivity
import com.steven.movieapp.ui.MovieFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initData() {
        val movies = resources.getStringArray(R.array.tab_movies)
        val fragments = ArrayList<Fragment>()
        for (it in movies) {
            fragments.add(MovieFragment.newInstance())
        }
        viewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {

            override fun getItem(position: Int): Fragment = fragments[position]

            override fun getPageTitle(position: Int): CharSequence? = movies[position]


            override fun getCount(): Int = movies.size
        }
        tab.setupWithViewPager(viewPager)
    }

}
