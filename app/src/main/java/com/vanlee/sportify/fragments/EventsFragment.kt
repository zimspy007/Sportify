package com.vanlee.sportify.fragments

import android.os.Bundle
import android.view.View
import com.vanlee.sportify.R
import com.vanlee.sportify.databinding.FragmentRecyclerViewBinding
import com.zhuinden.simplestackextensions.fragments.KeyedFragment

class EventsFragment : KeyedFragment(R.layout.fragment_recycler_view) {

    private lateinit var binding: FragmentRecyclerViewBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRecyclerViewBinding.bind(view)
        // val eventsFragmentKey = getKey<EventsFragmentKey>() // Get args if you need them


    }

}