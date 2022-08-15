package com.vanlee.sportify.fragments.keys

import com.vanlee.sportify.fragments.VideoPlayerFragment
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoPlayerFragmentKey(val eventId: Long) : BaseKey() {
    override fun instantiateFragment() = VideoPlayerFragment()
}