package com.steven.movieapp.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * Description:
 * Data：2019/1/28
 * Author:Steven
 */

abstract class BaseFragment : Fragment() {

    var mRootView: View? = null
    var mContext: Context? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mRootView != null) {
            val parent = mRootView.let { mRootView as ViewGroup }
            parent.removeView(mRootView)
        } else {
            mRootView = inflater.inflate(getLayoutId(), container, false)
        }
        initData()
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        onRequestData()

    }

    abstract fun getLayoutId(): Int
    abstract fun initData()
    abstract fun initView()
    abstract fun onRequestData()


    override fun onDetach() {
        super.onDetach()
        this.mContext = null
    }
}