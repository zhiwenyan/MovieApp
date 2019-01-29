package com.steven.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.steven.movieapp.api.RetrofitClient
import com.steven.movieapp.model.BaseResult
import com.steven.movieapp.model.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Description:
 * Data：1/29/2019-2:32 PM
 * @author yanzhiwen
 */
class MovieViewModel : ViewModel() {
    private val movieLiveData = MutableLiveData<BaseResult<List<Movie>>>()

    fun getInTheaters(apiKey: String): MutableLiveData<BaseResult<List<Movie>>> {

        RetrofitClient.serviceApi.getInTheaters(apiKey).enqueue(object : Callback<BaseResult<List<Movie>>> {
            override fun onResponse(call: Call<BaseResult<List<Movie>>>, response: Response<BaseResult<List<Movie>>>) {
                movieLiveData.value = response.body()
            }

            override fun onFailure(call: Call<BaseResult<List<Movie>>>, t: Throwable) {
            }
        })




        return movieLiveData
    }
}