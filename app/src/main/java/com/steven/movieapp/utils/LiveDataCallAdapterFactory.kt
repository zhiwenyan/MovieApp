package com.steven.movieapp.utils

import androidx.lifecycle.LiveData
import retrofit2.CallAdapter
import retrofit2.CallAdapter.Factory
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Description:
 * Data：2/19/2019-1:56 PM
 * @author yanzhiwen
 */

class LiveDataCallAdapterFactory : Factory() {


    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        if (Factory.getRawType(returnType) != LiveData::class.java) {
            return null
        }
        val observableType = Factory.getParameterUpperBound(0, returnType as ParameterizedType)

        val rawObservableType = Factory.getRawType(observableType)

        if (observableType !is ParameterizedType) {
            throw IllegalArgumentException("resource must be parameterized")
        }

        val bodyType = Factory.getParameterUpperBound(0, observableType)

        return LiveDataCallAdapter<Any>(observableType)
    }
}