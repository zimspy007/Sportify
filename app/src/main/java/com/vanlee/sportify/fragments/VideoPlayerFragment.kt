package com.vanlee.sportify.fragments

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import com.vanlee.sportify.R
import com.vanlee.sportify.databinding.FragmentVideoPlayerBinding
import com.vanlee.sportify.fragments.keys.VideoPlayerFragmentKey
import com.vanlee.sportify.viewmodel.EventViewModel
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import com.zhuinden.simplestackextensions.fragmentsktx.backstack


class VideoPlayerFragment :
    KeyedFragment(R.layout.fragment_video_player) {

    private var _binding: FragmentVideoPlayerBinding? = null
    private val binding get() = _binding!!

    private var mediaController: MediaController? = null

    private var eventId: Long = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentVideoPlayerBinding.bind(view)

        val key = getKey<VideoPlayerFragmentKey>()
        eventId = key.eventId

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            backstack.goBack()
        }

        val model = ViewModelProvider(this)[EventViewModel::class.java]
        model.getEvent(eventId).observe(viewLifecycleOwner) { event ->

            if (event == null) {
                DynamicToast.makeError(requireContext(), getString(R.string.error_loading_event))
                backstack.goBack()
            }

            binding.title.text = event.title

            val uri: Uri = Uri.parse(event.videoUrl)

            binding.videoView.setVideoURI(uri)

            mediaController = MediaController(requireContext())

            mediaController!!.setAnchorView(binding.videoView)
            mediaController!!.setMediaPlayer(binding.videoView)

            binding.videoView.setMediaController(mediaController)

            binding.videoView.start()

        }
        model.loadEvent(eventId)
    }

    override fun onStop() {
        if (binding.videoView.isPlaying)
            binding.videoView.stopPlayback()

        if (mediaController != null) {
            mediaController = null
        }

        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}