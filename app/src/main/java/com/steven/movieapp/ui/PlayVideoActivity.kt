package com.steven.movieapp.ui

import android.net.Uri
import android.view.View
import android.widget.MediaController
import com.steven.movieapp.R
import com.steven.movieapp.base.BaseActivity
import com.steven.movieapp.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_play_video.*

class PlayVideoActivity : BaseActivity() {

    private val videoUrl by lazy {
        intent.getStringExtra("video_url")
    }

    private val title by lazy {
        intent.getStringExtra("title")
    }

    override fun getLayoutId(): Int = R.layout.activity_play_video

    override fun initData() {
    }

    override fun initView() {
        StatusBarUtil.statusBarTranslucent(this)
        supportActionBar?.hide()
        supportActionBar?.title = title
        video.setMediaController(MediaController(this))
        video.setVideoURI(Uri.parse(videoUrl))
        video.setOnPreparedListener { mp ->
            progressBar.visibility = View.GONE
            mp.start()
        }
        video.setOnClickListener {
            if (supportActionBar!!.isShowing) {
                supportActionBar?.hide()
                StatusBarUtil.statusBarTranslucent(this)
            } else {
                supportActionBar?.show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (video != null) {
            video.suspend()
        }
    }
}
