package com.steven.movieapp.recyclerview

/**
 * Description:
 * Data：2019/1/28
 * Actor:Steven
 */
interface MultiTypeSupport<T> {
    fun getLayoutId(item: T, position: Int): Int
}