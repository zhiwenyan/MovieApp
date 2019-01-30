package com.steven.movieapp.utils

/**
 * Description:
 * Data：2019/1/30
 * Author:Steven
 */

class StringFormat {
    companion object {
        fun formatGenres(genres: List<String>): String {
            return if (!genres.isNullOrEmpty()) {
                val stringBuilder = StringBuilder()
                for (i in genres.indices)
                    if (i < genres.size - 1) {
                        stringBuilder.append(genres[i]).append("/")
                    } else {
                        stringBuilder.append(genres[i])
                    }
                stringBuilder.toString()
            } else {
                "不知名类型"
            }
        }


        fun formatPubdate(pubdates: List<String>): String {
            return if (!pubdates.isNullOrEmpty()) {
                val stringBuilder = StringBuilder()
                for (i in pubdates.indices)
                    if (i < pubdates.size - 1) {
                        stringBuilder.append(pubdates[i]).append("/")
                    } else {
                        stringBuilder.append(pubdates[i])
                    }
                stringBuilder.toString()
            } else {
                "未知"
            }
        }

        fun formatDurations(durations: List<String>): String {
            return if (!durations.isNullOrEmpty()) {
                val stringBuilder = StringBuilder()
                for (i in durations.indices)
                    if (i < durations.size - 1) {
                        stringBuilder.append(durations[i]).append("/")
                    } else {
                        stringBuilder.append(durations[i])
                    }
                stringBuilder.toString()
            } else {
                "未知"
            }
        }
    }
}