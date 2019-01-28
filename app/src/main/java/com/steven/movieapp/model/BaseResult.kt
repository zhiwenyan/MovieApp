package com.steven.movieapp.model

/**
 * Description:
 * Data：2019/1/28
 * Author:Steven
 */
data class BaseResult<T>(
    val count:Int,
    val start:Int,
    val total:Int,
    val subjects:List<T>
    )