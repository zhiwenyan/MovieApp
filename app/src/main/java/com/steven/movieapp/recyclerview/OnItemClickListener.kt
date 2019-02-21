package com.steven.movieapp.recyclerview

/**
 * Description:
 * Data：2019/1/28
 * Actor:Steven
 */
 interface OnItemClickListener<T>{
      fun onItemClick(position: Int,item:T)
}