package com.steven.movieapp.recyclerview

/**
 * Description:
 * Data：2019/1/28
 * Author:Steven
 */
interface MultiTypeSupport<T> {
    fun getLayoutId(item: T, position: Int): Int
}