package com.steven.movieapp.ui

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.steven.movieapp.API_KEY
import com.steven.movieapp.R
import com.steven.movieapp.adapter.ActorPhotoAdapter
import com.steven.movieapp.base.BaseActivity
import com.steven.movieapp.model.ActorInfo
import com.steven.movieapp.model.Photo
import com.steven.movieapp.model.Works
import kotlinx.android.synthetic.main.activity_actor_info.*
import kotlinx.android.synthetic.main.load_view.*

class ActorInfoActivity : BaseActivity() {

    private val actorId: String by lazy {
        intent.getStringExtra("actor_id")
    }

    override fun getLayoutId() = R.layout.activity_actor_info

    override fun initData() {
    }

    override fun initView() {

    }

    override fun onRequestData() {
        movieViewModel.getCelebrity(actorId, API_KEY).observe(this, Observer {
            showActorInfo(it)
            showPhotos(it.photos)
            showActorWorks(it.works);
        })
    }


    private fun showActorInfo(actor: ActorInfo) {
        load_view.visibility = View.GONE
        supportActionBar!!.title = actor.name
        gender.text = String.format("性别：%s", actor.gender)
        constellation.text = String.format("星座：%s", actor.constellation)
        birthday.text = String.format("出生日期：%s", actor.birthday)
        born_place.text = String.format("出生地：%s", actor.born_place)
        actor_summary.text = actor.summary
    }

    private fun showPhotos(photos: List<Photo>) {
        rv_photos.layoutManager = LinearLayoutManager(this, R.layout.photo_item, false)
        rv_photos.adapter = ActorPhotoAdapter(this, R.layout.photo_item, photos)
    }

    /**
     * 最近的5部作品
     */
    private fun showActorWorks(works: Works) {

    }
}
