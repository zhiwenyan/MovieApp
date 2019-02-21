package com.steven.movieapp.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import com.steven.movieapp.R

/**
 * Description:
 * Data：2019/2/20
 * Actor:Steven
 */
class DefaultLoadViewCreator : LoadViewCreator() {
    private lateinit var mIvRefresh: View
    override fun getLoadView(context: Context, parent: ViewGroup): View {
        val refreshView = LayoutInflater.from(context).inflate(R.layout.load_footer_view, parent, false);
        mIvRefresh = refreshView.findViewById(R.id.iv_refresh)
        return refreshView
    }

    override fun onPull(currentDragHeight: Int, refreshHeight: Int, currentRefreshStatus: Int) {
        val rotate = currentDragHeight.toFloat() / refreshHeight
        mIvRefresh.rotation = rotate * 360
    }

    override fun onLoading() {
        val animation = RotateAnimation(0f, 720f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        animation.repeatCount = -1
        animation.duration = 1288
        mIvRefresh.startAnimation(animation)
    }

    override fun onStopLoad() {
        mIvRefresh.rotation = 0f
        mIvRefresh.clearAnimation()
    }
}