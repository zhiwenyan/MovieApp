package com.steven.movieapp.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Description:
 * Data：2019/1/28
 * Author:Steven
 */
abstract class BaseRecyclerAdapter<T>(
    context: Context,
    var layoutId: Int,
    var data: List<T>
) : RecyclerView.Adapter<BaseViewHolder>() {
    //点击事件
    private var mOnItemClickListener: OnItemClickListener? = null
    //支持多种布局
    private var mMultiTypeSupport: MultiTypeSupport<T>? = null
    //mInflater
    private var mInflater: LayoutInflater = LayoutInflater.from(context)


    constructor(context: Context, data: List<T>, multiTypeSupport: MultiTypeSupport<T>)
            : this(context, -1, data) {
        this.mMultiTypeSupport = multiTypeSupport
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

        mMultiTypeSupport?.also {
            this.layoutId = viewType
        }
        val itemView = mInflater.inflate(layoutId, parent, false)
        return BaseViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener {
                mOnItemClickListener?.apply { onItemClick(position) }
            }
        }
        convert(holder, position, data[position])
    }

    abstract fun convert(holder: BaseViewHolder, position: Int, item: T)

    /**
     * 设置itemView的点击事件
     */
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.mOnItemClickListener = onItemClickListener
    }

}

