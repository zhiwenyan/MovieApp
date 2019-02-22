package com.steven.movieapp.ui

import android.net.Uri
import android.view.View
import android.widget.MediaController
import com.steven.movieapp.R
import com.steven.movieapp.base.BaseActivity
import com.steven.movieapp.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_play_trailers.*

class PlayTrailersActivity : BaseActivity() {

    private val mVideoUrl by lazy {
        intent.getStringExtra("video_url")
    }

    override fun getLayoutId(): Int = R.layout.activity_play_trailers

    override fun initData() {


    }

    override fun initView() {
        StatusBarUtil.statusBarTranslucent(this)
        video.setMediaController(MediaController(this))
        video.setVideoURI(Uri.parse(mVideoUrl))
        video.setOnPreparedListener { mp ->
            progressBar.visibility= View.GONE
            mp.start()
        }

    }

    override fun onRequestData() {
    }

    override fun onDestroy() {
        super.onDestroy()
        if (video != null) {
            video.suspend()
        }
    }
}
