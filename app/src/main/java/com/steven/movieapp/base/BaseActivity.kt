package com.steven.movieapp.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Description:
 * Data：2019/1/28
 * Author:Steven
 */
abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initData()
        initView()

    }

    abstract fun getLayoutId(): Int
    abstract fun initData()
    abstract fun initView()
}