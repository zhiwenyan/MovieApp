package com.steven.movieapp.api

import android.util.Log
import com.steven.movieapp.BASE_URL
import com.steven.movieapp.TAG
import com.steven.movieapp.utils.LiveDataCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Description:
 * Data：2019/1/28
 * Author:Steven
 */
class RetrofitClient {
    companion object {

        val serviceApi: ServiceApi by lazy {
            val retrofitClient = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(
                    OkHttpClient
                        .Builder()
                        .addInterceptor(
                            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
                                Log.i(TAG, message)
                            }).setLevel(HttpLoggingInterceptor.Level.BODY)
                        ).build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
            retrofitClient.create(ServiceApi::class.java)
        }
    }

}
