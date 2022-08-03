package com.vanlee.sportify.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vanlee.sportify.R
import com.vanlee.sportify.adapters.SchedulesAdapter
import com.vanlee.sportify.databinding.FragmentRecyclerViewBinding
import com.vanlee.sportify.viewmodel.SchedulesViewModel
import com.zhuinden.simplestackextensions.fragments.KeyedFragment

class ScheduleFragment : KeyedFragment(R.layout.fragment_recycler_view) {

    private var _binding: FragmentRecyclerViewBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentRecyclerViewBinding.bind(view)
        // val scheduleFragmentKey = getKey<ScheduleFragmentKey>() // Get args if you need them

        val model = ViewModelProvider(this)[SchedulesViewModel::class.java]
        model.getSchedules().observe(viewLifecycleOwner) { schedules ->
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = SchedulesAdapter(requireContext(), schedules)
            }
        }
        model.loadSchedules()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}