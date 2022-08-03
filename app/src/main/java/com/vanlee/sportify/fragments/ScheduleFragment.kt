package com.vanlee.sportify.fragments

import android.os.Bundle
import android.view.View
import com.vanlee.sportify.R
import com.vanlee.sportify.databinding.FragmentRecyclerViewBinding
import com.zhuinden.simplestackextensions.fragments.KeyedFragment

class ScheduleFragment : KeyedFragment(R.layout.fragment_recycler_view) {

    private lateinit var binding: FragmentRecyclerViewBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRecyclerViewBinding.bind(view)
        // val scheduleFragmentKey = getKey<ScheduleFragmentKey>() // Get args if you need them
    }
}