package com.vanlee.sportify.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import com.vanlee.sportify.R
import com.vanlee.sportify.adapters.SchedulesAdapter
import com.vanlee.sportify.databinding.FragmentRecyclerViewBinding
import com.vanlee.sportify.viewmodel.SchedulesViewModel
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import com.zhuinden.simplestackextensions.fragmentsktx.backstack

class ScheduleFragment : KeyedFragment(R.layout.fragment_recycler_view) {

    private var _binding: FragmentRecyclerViewBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentRecyclerViewBinding.bind(view)
        // val scheduleFragmentKey = getKey<ScheduleFragmentKey>() // Get args if you need them

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbarTitle.text = getString(R.string.schedules)

        val model = ViewModelProvider(this)[SchedulesViewModel::class.java]
        model.getSchedules().observe(viewLifecycleOwner) { schedules ->

            if (schedules == null) {
                DynamicToast.makeError(
                    requireContext(),
                    getString(R.string.error_loading_schedules)
                ).show()
                backstack.goBack()
            }

            val layoutManager = LinearLayoutManager(requireContext())
            val adapter = SchedulesAdapter(requireContext(), schedules)

            adapter.setHasStableIds(true)

            binding.recyclerView.layoutManager = layoutManager
            binding.recyclerView.adapter = adapter

        }
        model.loadSchedules()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}