package com.steven.movieapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.steven.movieapp.adapter.MovieAdapter
import com.steven.movieapp.base.BaseActivity
import com.steven.movieapp.ui.ComingMovieFragment
import com.steven.movieapp.ui.TheaterMovieFragment
import com.steven.movieapp.ui.Top250MovieFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_movie.*

class MainActivity : BaseActivity() {

    private var linearLayoutManager: LinearLayoutManager =
        LinearLayoutManager(this)


    private var gridLayoutManager: GridLayoutManager =
        GridLayoutManager(this, 2)


    override fun getLayoutId(): Int = R.layout.activity_main

    override fun init() {
        val movies = resources.getStringArray(R.array.tab_movies)
        val fragments = ArrayList<Fragment>()


        fragments.add(TheaterMovieFragment.newInstance())
        fragments.add(ComingMovieFragment.newInstance())
//            fragments.add(WeeklyMovieFragment.newInstance())
        //  fragments.add(UsMovieFragment.newInstance())
        //  fragments.add(NewMoviesFragment.newInstance())
        fragments.add(Top250MovieFragment.newInstance())

        viewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {

            override fun getItem(position: Int): Fragment = fragments[position]

            override fun getPageTitle(position: Int): CharSequence? = movies[position]


            override fun getCount(): Int = movies.size
        }

        tab.setupWithViewPager(viewPager)
        fab.setOnClickListener {
            val adapter = (recyclerView.adapter as MovieAdapter)
            if (recyclerView.layoutManager is GridLayoutManager) {
                recyclerView.layoutManager = linearLayoutManager
                adapter.setLayoutId(R.layout.movie_list_item)
                fab.setImageResource(R.mipmap.ic_grid)
            } else {
                recyclerView.layoutManager = gridLayoutManager
                adapter.setLayoutId(R.layout.movie_grid_item)
                fab.setImageResource(R.mipmap.ic_list)
            }
            recyclerView.adapter = adapter
        }
    }
}
