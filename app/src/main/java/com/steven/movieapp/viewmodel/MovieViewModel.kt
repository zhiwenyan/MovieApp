package com.steven.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.steven.movieapp.api.RetrofitClient
import com.steven.movieapp.model.*

/**
 * Description:
 * Data：1/29/2019-2:32 PM
 * @author yanzhiwen
 */
class MovieViewModel : ViewModel() {

    /**
     * 院线热映
     */
    fun getInTheaters(apiKey: String): LiveData<BaseResult<List<Movie>>> =
        RetrofitClient.serviceApi.getInTheaters(apiKey)


    /**
     * 即将上映
     */
    fun getComingSoon(apiKey: String): LiveData<BaseResult<List<Movie>>> =
        RetrofitClient.serviceApi.getComingSoon(apiKey)

    /**
     * 口碑榜
     */
    fun getMovieWeekly(apiKey: String): LiveData<List<Movie>> =
        RetrofitClient.serviceApi.getMovieWeekly(apiKey)

    /**
     * 北美票房榜
     */
    fun getMovieUsBox(apiKey: String): LiveData<BaseResult<List<Movie>>> =
        RetrofitClient.serviceApi.getMovieUsBox(apiKey)

    /**
     *  新片榜
     */
    fun getMovieNewMovies(apiKey: String): LiveData<BaseResult<List<Movie>>> =
        RetrofitClient.serviceApi.getMovieNewMovies(apiKey)

    /**
     *  top250
     */
    fun getTop250Movie(apiKey: String, start: Int, count: Int): LiveData<BaseResult<List<Movie>>> =
        RetrofitClient.serviceApi.getTop250Movie(apiKey, start, count)

    /**
     * 电影信息
     */
    fun getMovieInfo(movieId: String, apiKey: String): LiveData<MovieInfo> =
        RetrofitClient.serviceApi.getMovieInfo(movieId, apiKey)

    /**
     * 热评
     */
    fun getComments(movieId: String, apiKey: String): LiveData<Comments> =
        RetrofitClient.serviceApi.getComments(movieId, apiKey)



    /**
     * 影人
     */
    fun getCelebrity(celebrityId: String, apiKey: String): LiveData<ActorInfo> =
        RetrofitClient.serviceApi.getCelebrity(celebrityId, apiKey)
}
