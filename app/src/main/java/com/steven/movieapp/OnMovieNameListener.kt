package com.steven.movieapp

import com.steven.movieapp.model.Movie

/**
 * Description:
 * Data：1/29/2019-4:28 PM
 * @author yanzhiwen
 */
interface OnMovieNameListener {
    fun onMovies(movie: List<Movie>)
}