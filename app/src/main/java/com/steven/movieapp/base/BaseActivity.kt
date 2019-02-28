package com.steven.movieapp.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.steven.movieapp.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.activity_movie_info.*

/**
 * Description:
 * Data：2019/1/28
 * Actor:Steven
 */
abstract class BaseActivity : AppCompatActivity() {
    protected val movieViewModel: MovieViewModel by lazy {
        ViewModelProviders.of(this).get(MovieViewModel::class.java)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        setUpActionBar()
        initData()
        initView()
        onRequestData()
    }

    private fun setUpActionBar() {
        toolbar?.apply {
            setSupportActionBar(this)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
    }

    abstract fun getLayoutId(): Int
    abstract fun initData()
    abstract fun initView()
    open fun onRequestData() {

    }


    override fun onSupportNavigateUp(): Boolean {
        this.finish()
        return super.onSupportNavigateUp()
    }
}