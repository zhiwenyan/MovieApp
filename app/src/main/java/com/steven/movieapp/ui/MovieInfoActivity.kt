package com.steven.movieapp.ui

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.app.ShareCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.steven.movieapp.API_KEY
import com.steven.movieapp.R
import com.steven.movieapp.adapter.ActorsAdapter
import com.steven.movieapp.adapter.CommentsAdapter
import com.steven.movieapp.adapter.TrailersAdapter
import com.steven.movieapp.base.BaseActivity
import com.steven.movieapp.model.Actor
import com.steven.movieapp.model.Comment
import com.steven.movieapp.model.MovieInfo
import com.steven.movieapp.model.Trailers
import com.steven.movieapp.recyclerview.DividerItemDecoration
import com.steven.movieapp.recyclerview.OnItemClickListener
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
        fab.setOnClickListener(this)
    }

    override fun onRequestData() {
        movieViewModel.getMovieInfo(movieId, API_KEY).observe(this, Observer {
            showMovieInfo(it)
            showActors(it.casts)
            showMovieComments(it.popular_comments)
            showMovieTrailers(it.trailers)
        })

    }

    private fun showActors(actors: List<Actor>) {
        rv_actors.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_actors.adapter = ActorsAdapter(this, R.layout.actor_image_item, actors)
    }


    private fun showMovieInfo(movieInfo: MovieInfo) {
        load_view.visibility = View.GONE
        container.visibility = View.VISIBLE
        Glide.with(this).load(movieInfo.images.large).into(iv_movie)
        toolbar.title = movieInfo.title
        shareText = movieInfo.title
        tv_rating.text = String.format("评分：%s", movieInfo.rating.average)
        rating_bar.rating = ((movieInfo.rating.average / 2).toFloat())
        tv_director.text = String.format("导演：%s", StringFormat.formatName(movieInfo.directors))
        tv_actor.text = String.format("演员：%s", StringFormat.formatName(movieInfo.casts))
        tv_genres.text = String.format("类型：%s", StringFormat.formatGenres(movieInfo.genres))
        tv_date.text = String.format("上映日期：%s", movieInfo.year)
        tv_country.text = String.format("制片国家/地区：%s", StringFormat.formatCountry(movieInfo.countries))
        tv_content.text = movieInfo.summary
    }

    private fun showMovieComments(popular_comments: List<Comment>) {
        rv_comments.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_comments.adapter = CommentsAdapter(this, R.layout.comment_item, popular_comments)
        rv_comments.addItemDecoration(
            DividerItemDecoration(
                this,
                R.drawable.ic_divider_item,
                LinearLayoutManager.VERTICAL
            )
        )
        val moreCommentsView =
            LayoutInflater.from(this).inflate(
                R.layout.check_more_comments,
                findViewById(R.id.container), false
            )
        rv_comments.addFooterView(moreCommentsView)
        moreCommentsView.findViewById<TextView>(R.id.comments_more).setOnClickListener {
            val intent = Intent(this, CommentsActivity::class.java)
            intent.putExtra("movieId", movieId)
            startActivity(intent)
        }

    }

    private fun showMovieTrailers(trailers: List<Trailers>) {
        rv_trailers.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter = TrailersAdapter(this, R.layout.trailers_image_item, trailers)
        rv_trailers.adapter = adapter
        adapter.setOnItemClickListener(object : OnItemClickListener<Trailers> {
            override fun onItemClick(position: Int, item: Trailers) {
                val intent = Intent(this@MovieInfoActivity, PlayTrailersActivity::class.java)
                intent.putExtra("video_url", item.resource_url)
                startActivity(intent)
            }
        })
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