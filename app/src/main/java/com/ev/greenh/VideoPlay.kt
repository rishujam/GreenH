package com.ev.greenh

import android.media.MediaPlayer
import android.os.Bundle
import android.view.*
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.ev.greenh.databinding.VideoPlayBinding

class VideoPlay:Fragment(){

    //private val videoService: VideoDownloadAndPlayService? = null
    private var _binding: VideoPlayBinding?=null
    private val binding get() = _binding!!

    companion object {
        const val URL =
            "https://firebasestorage.googleapis.com/v0/b/fir-yt-a8191.appspot.com/o/samplevideo%2Fsnowhite.mp4?alt=media&token=414b6357-4d9d-44c6-b327-47e2215f25d5"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = VideoPlayBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}