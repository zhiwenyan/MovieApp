package com.steven.movieapp.ui

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

    private var mRootView: View? = null
    public var mContext: Context? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mRootView != null) {
            val parent = mRootView!!.parent as ViewGroup
            parent.removeView(mRootView)
        } else {
            mRootView = inflater.inflate(getLayoutId(), container, false)
        }
        initData()
        initView()
        return mRootView
    }

    abstract fun getLayoutId(): Int
    abstract fun initData()
    abstract fun initView()

    override fun onDetach() {
        super.onDetach()
        this.mContext = null
    }
}