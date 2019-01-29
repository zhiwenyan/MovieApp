package com.steven.movieapp.ui

import android.content.Context
import com.steven.movieapp.R
import com.steven.movieapp.model.Movie
import com.steven.movieapp.recyclerview.BaseRecyclerAdapter
import com.steven.movieapp.recyclerview.BaseViewHolder

/**
 * Description:d
 * Data：1/29/2019-2:20 PM
 * @author yanzhiwen
 *
 */
class MovieAdapter(context: Context, layoutId: Int, data: List<Movie>) :
        BaseRecyclerAdapter<Movie>(context, layoutId, data) {

    override fun convert(holder: BaseViewHolder, position: Int, item: Movie) {
        holder.setText(R.id.name, item.title)
                .setImage(R.id.iv_movie, item.images.large)
    }
}