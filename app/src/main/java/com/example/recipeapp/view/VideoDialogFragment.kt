package com.example.recipeapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.recipeapp.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import android.widget.ImageButton

class VideoDialogFragment : DialogFragment() {

    private lateinit var youTubePlayerView: YouTubePlayerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Close button setup
        view.findViewById<ImageButton>(R.id.close_button).setOnClickListener {
            dismiss()
        }

        youTubePlayerView = view.findViewById(R.id.youtube_player_view)

        val videoId = arguments?.getString("VIDEO_ID")
        if (!videoId.isNullOrEmpty()) {
            lifecycle.addObserver(youTubePlayerView)
            youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(videoId, 0f)
                }
            })
        }
    }

    override fun onStart() {
        super.onStart()
        // Adjust dialog size to be smaller and centered
        dialog?.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        youTubePlayerView.release()
    }
}
