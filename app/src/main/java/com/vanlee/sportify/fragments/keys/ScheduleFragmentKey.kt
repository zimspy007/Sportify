package com.vanlee.sportify.fragments.keys

import com.vanlee.sportify.fragments.ScheduleFragment
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScheduleFragmentKey(private val placeholder: String = "") : BaseKey() {
    override fun instantiateFragment() = ScheduleFragment()
}