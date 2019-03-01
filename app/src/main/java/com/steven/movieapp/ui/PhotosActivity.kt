package com.steven.movieapp.ui

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.steven.movieapp.R
import com.steven.movieapp.adapter.ActorPhotosAdapter
import com.steven.movieapp.base.BaseActivity
import com.steven.movieapp.model.Photo
import com.steven.movieapp.widget.recyclerview.OnItemClickListener
import com.steven.movieapp.widget.refreshLoad.DefaultLoadViewCreator
import com.steven.movieapp.widget.refreshLoad.DefaultRefreshViewCreator
import com.steven.movieapp.widget.refreshLoad.LoadRefreshRecyclerView
import com.steven.movieapp.widget.refreshLoad.RefreshRecyclerView
import kotlinx.android.synthetic.main.activity_photos.*
import kotlinx.android.synthetic.main.load_view.*

class PhotosActivity : BaseActivity(), RefreshRecyclerView.OnRefreshListener, LoadRefreshRecyclerView.OnLoadListener,
    OnItemClickListener<Photo> {
    private val celebrityId: String by lazy {
        intent.getStringExtra("celebrity_id")
    }
    private val name: String by lazy {
        intent.getStringExtra("name")

    }
    private var start: Int = 0
    private var count: Int = 20
    private var adapter: ActorPhotosAdapter? = null

    private var photos = arrayListOf<Photo>()

    override fun getLayoutId(): Int = R.layout.activity_photos

    override fun initView() {
        supportActionBar?.apply { title = name }
        rv_photos.layoutManager = GridLayoutManager(this, 3)
        rv_photos.addLoadViewCreator(DefaultLoadViewCreator())
        rv_photos.addRefreshViewCreator(DefaultRefreshViewCreator())
        rv_photos.addLoadingView(load_view)
        rv_photos.setOnRefreshListener(this)
        rv_photos.setOnLoadListener(this)

    }

    override fun onRequestData() {
        movieViewModel.getCelebrityPhotos(celebrityId, start, count).observe(this, Observer {
            showPhotos(it.photos)
        })
    }


    private fun showPhotos(photos: List<Photo>) {
        if (load_view.visibility == View.VISIBLE) {
            load_view.visibility = View.GONE
        }
        if (adapter == null) {
            this.photos = photos as ArrayList<Photo>
            adapter = ActorPhotosAdapter(this, R.layout.photo_item, this.photos)
            rv_photos.adapter = adapter
        } else {
            rv_photos.onStopRefresh()
            rv_photos.onStopLoad()
            if (photos.isNotEmpty() && rv_photos.isLoading()) {
                this.photos.addAll(photos)
            }
            adapter?.apply { notifyDataSetChanged() }
        }
        adapter?.setOnItemClickListener(this)

    }


    override fun onLoad() {
        start += 20
        movieViewModel.getCelebrityPhotos(celebrityId, start, count).observe(this, Observer {
            showPhotos(it.photos)
        })
    }

    override fun onRefresh() {
        onRequestData()
    }

    override fun onItemClick(position: Int, item: Photo) {
    }

}
