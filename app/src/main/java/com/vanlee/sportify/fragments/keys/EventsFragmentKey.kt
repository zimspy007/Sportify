package com.vanlee.sportify.fragments.keys

import com.vanlee.sportify.fragments.EventsFragment
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventsFragmentKey(private val placeholder: String = "") : BaseKey() {
    override fun instantiateFragment() = EventsFragment()
}