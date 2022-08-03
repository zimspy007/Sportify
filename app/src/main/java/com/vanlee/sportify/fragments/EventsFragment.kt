package com.vanlee.sportify.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vanlee.sportify.R
import com.vanlee.sportify.adapters.EventsAdapter
import com.vanlee.sportify.databinding.FragmentRecyclerViewBinding
import com.vanlee.sportify.viewmodel.EventsViewModel
import com.zhuinden.simplestackextensions.fragments.KeyedFragment

class EventsFragment : KeyedFragment(R.layout.fragment_recycler_view) {

    private var _binding: FragmentRecyclerViewBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentRecyclerViewBinding.bind(view)
        // val eventsFragmentKey = getKey<EventsFragmentKey>() // Get args if you need them

        val model = ViewModelProvider(this)[EventsViewModel::class.java]
        model.getEvents().observe(viewLifecycleOwner) { events ->
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = EventsAdapter(requireContext(), events)
            }
        }
        model.loadEvents()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}