package com.steven.movieapp.api

import com.steven.movieapp.model.BaseResult
import com.steven.movieapp.model.Movies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Description: https://www.jianshu.com/p/a7e51129b042
 * Data：2019/1/26
 * Author:Steven
 */

interface  ServiceApi{

    /**
     * 院线正在热映
     * 请求实例：https://api.douban.com/v2/movie/in_theaters?apikey=0b2bdeda43b5688921839c8ecb20399b
     *
     */
    fun getInTheaters(@Query("apikey") apiKey:String):Call<BaseResult<Movies>>

    /**
     * 豆瓣评分排名top250
     *
     */
    @GET("top250")
    fun  getTop250Movie(@Query("apikey") apiKey:String,
                        @Query("start") start:Int,
                        @Query("count") count:Int):Call<BaseResult<Movies>>


}


