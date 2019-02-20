package com.steven.movieapp.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Description:
 * Data：2019/2/20
 * Author:Steven
 */

open class WrapRecyclerView : RecyclerView {

    private lateinit var mWrapRecyclerAdapter: WrapRecyclerAdapter

    private lateinit var mAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    private val mDataObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            if (mWrapRecyclerAdapter != adapter) {
                mWrapRecyclerAdapter.notifyDataSetChanged()
            }
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            if (mWrapRecyclerAdapter != adapter) {
                mWrapRecyclerAdapter.notifyItemRemoved(positionStart)
            }
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            if (mWrapRecyclerAdapter != adapter) {
                mWrapRecyclerAdapter.notifyItemMoved(fromPosition, toPosition)
            }
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            if (mWrapRecyclerAdapter != adapter) {
                mWrapRecyclerAdapter.notifyItemChanged(positionStart)
            }
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            if (mWrapRecyclerAdapter != adapter) {
                mWrapRecyclerAdapter.notifyItemChanged(positionStart, payload)
            }
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            if (mWrapRecyclerAdapter != adapter) {
                mWrapRecyclerAdapter.notifyItemInserted(positionStart)

            }
        }
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)


    override fun setAdapter(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?) {

        this.mAdapter = adapter!!
        mWrapRecyclerAdapter = if (adapter is WrapRecyclerAdapter) {
            adapter
        } else {
            WrapRecyclerAdapter(adapter)
        }

        super.setAdapter(mWrapRecyclerAdapter)
        mAdapter.registerAdapterDataObserver(mDataObserver)
        //解决GridLayout添加头部和底部要占据一行
        mWrapRecyclerAdapter.adjustSpanSize(this)
    }

    fun addHeaderView(view: View) {
        mWrapRecyclerAdapter.addHeaderView(view)
    }

    fun addFooterView(view: View) {
        mWrapRecyclerAdapter.addFooterView(view)
    }

    fun removeHeaderView(view: View) {
        mWrapRecyclerAdapter.removeHeaderView(view)
    }

    fun removeFooterView(view: View) {
        mWrapRecyclerAdapter.removeFooterView(view)
    }
}