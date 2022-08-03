package com.vanlee.sportify.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.vanlee.sportify.R
import com.vanlee.sportify.databinding.FragmentRecyclerViewBinding
import com.vanlee.sportify.network.NetworkRequests
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class EventsFragment : KeyedFragment(R.layout.fragment_recycler_view) {

    private lateinit var binding: FragmentRecyclerViewBinding

    private val client = OkHttpClient()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRecyclerViewBinding.bind(view)
        // val eventsFragmentKey = getKey<EventsFragmentKey>() // Get args if you need them

        lifecycleScope.launch(Dispatchers.IO) {
            val httpResponse = NetworkRequests.getEvents(client)
        }

    }

}