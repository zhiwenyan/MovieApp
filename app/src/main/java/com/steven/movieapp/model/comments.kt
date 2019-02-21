package com.steven.movieapp.model

/**
 * Description:
 * Data：2/21/2019-2:24 PM
 * @author yanzhiwen
 */
data class comments(
    val rating: Rate,
    val useful_count: Int,
    val author: Actor,
    val subject_id: String,
    val content: String,
    val id: String,
    val name: String

)