package com.steven.movieapp.ui

import android.content.Intent
import android.os.Build
import android.view.View
import androidx.core.app.ShareCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.steven.movieapp.API_KEY
import com.steven.movieapp.R
import com.steven.movieapp.base.BaseActivity
import com.steven.movieapp.model.MovieInfo
import com.steven.movieapp.utils.StringFormat
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.load_view.*

/**
 * Description:
 * Data：2/21/2019-1:53 PM
 * @author yanzhiwen
 */
class MovieInfoActivity : BaseActivity(), View.OnClickListener {

    private lateinit var shareText: String

    private val movieId: String by lazy {
        intent.getStringExtra("id")
    }

    override fun getLayoutId(): Int = R.layout.activity_movie_detail

    override fun initData() {

    }

    override fun initView() {
        load_view.visibility = View.VISIBLE
        fab.setOnClickListener(this)
    }

    override fun onRequestData() {
        movieViewModel.getMovieInfo(movieId, API_KEY).observe(this, Observer {
            showMovieInfo(it)
        })

    }

    private fun showMovieInfo(movieInfo: MovieInfo) {
        load_view.visibility = View.GONE
        container.visibility = View.VISIBLE
        Glide.with(this).load(movieInfo.images.large).into(iv_movie)
        toolbar.title = movieInfo.title
        shareText = movieInfo.title
        tv_rating.text= String.format("评分：%s",movieInfo.rating.average)
        rating_bar.rating = ((movieInfo.rating.average / 2).toFloat())
        tv_director.text = String.format("导演：%s", StringFormat.formatName(movieInfo.directors))
        tv_actor.text = String.format("演员：%s", StringFormat.formatName(movieInfo.casts))
        tv_genres.text = String.format("类型：%s", StringFormat.formatGenres(movieInfo.genres))
        tv_date.text = String.format("上映日期：%s", movieInfo.year)
        tv_country.text = String.format("制片国家/地区：%s", StringFormat.formatCountry(movieInfo.countries))
        tv_content.text = movieInfo.summary
    }

    override fun onClick(v: View) {
        val shareIntent = ShareCompat.IntentBuilder.from(this)
            .setText(shareText)
            .setType("text/plain")
            .createChooserIntent()
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
                } else {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
                }
            }
        startActivity(shareIntent)
    }
}