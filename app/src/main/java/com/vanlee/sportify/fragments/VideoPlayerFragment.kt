package com.vanlee.sportify.fragments

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.vanlee.sportify.R
import com.vanlee.sportify.databinding.FragmentVideoPlayerBinding
import com.vanlee.sportify.viewmodel.EventViewModel
import com.zhuinden.simplestack.navigator.Navigator
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import com.zhuinden.simplestackextensions.fragmentsktx.backstack


class VideoPlayerFragment(private val eventId: Long) :
    KeyedFragment(R.layout.fragment_video_player) {

    private var _binding: FragmentVideoPlayerBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentVideoPlayerBinding.bind(view)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            backstack.goBack()
        }

        val model = ViewModelProvider(this)[EventViewModel::class.java]
        model.getEvent(eventId).observe(viewLifecycleOwner) { event ->

            val uri: Uri = Uri.parse(event.videoUrl)

            binding.videoView.setVideoURI(uri)

            val mediaController = MediaController(requireContext())

            mediaController.setAnchorView(binding.videoView)

            mediaController.setMediaPlayer(binding.videoView)

            binding.videoView.setMediaController(mediaController)

            binding.videoView.start()

        }
        model.loadEvent(eventId)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}