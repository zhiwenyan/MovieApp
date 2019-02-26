package com.steven.movieapp.adapter

import android.content.Context
import com.steven.movieapp.R
import com.steven.movieapp.model.Photo
import com.steven.movieapp.recyclerview.BaseRecyclerAdapter
import com.steven.movieapp.recyclerview.BaseViewHolder

/**
 * Description:
 * Data：2/21/2019-5:43 PM
 * @author yanzhiwen
 */
class ActorPhotoAdapter(context: Context, layoutId: Int, data: List<Photo>) :
    BaseRecyclerAdapter<Photo>(context, layoutId, data) {


    override fun convert(holder: BaseViewHolder, position: Int, item: Photo) {
        holder.setImage(R.id.iv_actor, item.image)
    }

}