package com.ev.greenh.ui.plants

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import com.ev.greenh.R
import com.ev.greenh.databinding.FragmentPlantVideoBinding
import com.ev.greenh.models.uimodels.PlantVideo
import com.ev.greenh.ui.MainActivity
import com.ev.greenh.util.Resource
import com.ev.greenh.viewmodels.PlantViewModel

class PlantVideoFragment:Fragment() {

    private var _binding: FragmentPlantVideoBinding?=null
    private val binding get() = _binding!!
    private lateinit var exoPlayer: ExoPlayer
    private lateinit var viewModel:PlantViewModel
    private var playerInit = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlantVideoBinding.inflate(inflater,container,false)
        viewModel = (activity as MainActivity).viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val plantId = arguments?.getString("plantId").toString()
        viewModel.getPlantVideoUrl(getString(R.string.plant_video_url), plantId)

        viewModel.videoUrl.observe(viewLifecycleOwner, Observer {
            when(it.getContentIfNotHandled()){
                is Resource.Success->{
                    val url = it.peekContent().data
                    if(url!=null && url!="null" && url!="") {
                        setupPlayer(it.peekContent().data!!)
                    }else{
                        binding.pbVideoLoad.visibility = View.INVISIBLE
                        Toast.makeText(context, "Video not available", Toast.LENGTH_SHORT).show()
                        (activity as MainActivity).supportFragmentManager.popBackStack()
                    }
                }
                is Resource.Error->{
                    binding.pbVideoLoad.visibility = View.INVISIBLE
                    Toast.makeText(context, "Video not available", Toast.LENGTH_SHORT).show()
                    (activity as MainActivity).supportFragmentManager.popBackStack()
                }
                is Resource.Loading ->{}
                else -> {}
            }
        })

        binding.backBtnVideo.setOnClickListener {
            if(playerInit){
                exoPlayer.release()
            }
            (activity as MainActivity).supportFragmentManager.popBackStack()
        }

    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    private fun setupPlayer(url:String){
        playerInit = true
        val plantVideo = PlantVideo(
            video = url
        )
        exoPlayer = ExoPlayer.Builder(requireContext())
            .build()
            .apply {
                val defaultDataSourceFactory = DefaultDataSource.Factory(requireContext())
                val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(
                    requireContext(),
                    defaultDataSourceFactory
                )
                val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(plantVideo.getVideoUri()))

                setMediaSource(source)
                prepare()
            }

        exoPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if(state==Player.STATE_READY){
                    binding.pbVideoLoad.visibility = View.INVISIBLE
                }
            }
        })
        exoPlayer.playWhenReady = true
        exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
        exoPlayer.repeatMode = Player.REPEAT_MODE_ONE

        binding.videoView.apply {
            hideController()
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            player = exoPlayer
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if(playerInit){
            exoPlayer.release()
        }
        _binding = null
    }
}
