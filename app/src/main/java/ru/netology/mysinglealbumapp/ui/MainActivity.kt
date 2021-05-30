package ru.netology.mysinglealbumapp.ui

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import ru.netology.mysinglealbumapp.BuildConfig.BASE_URL
import ru.netology.mysinglealbumapp.adapter.TrackAdapter
import ru.netology.mysinglealbumapp.adapter.OnInteractionListener
import ru.netology.mysinglealbumapp.databinding.ActivityMainBinding
import ru.netology.mysinglealbumapp.dto.Track
import ru.netology.mysinglealbumapp.mediaObserver.MediaLifecycleObserver
import ru.netology.mysinglealbumapp.viewModel.AlbumViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: AlbumViewModel by viewModels()
    private val mediaObserver = MediaLifecycleObserver()
    private var playList: List<Track> = emptyList()
    private var currentIndex = 0
    private var currentTrack = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycle.addObserver(mediaObserver)

        val adapter = TrackAdapter(object : OnInteractionListener {
            override fun onPlayPause(track: Track) {

                viewModel.isPlayed(track.id)
                currentTrack = track.id
                playerController(BASE_URL + track.file)

            }
        })

        binding.trackListRV.adapter = adapter

        binding.trackListRV.addItemDecoration(
            DividerItemDecoration(binding.trackListRV.context, DividerItemDecoration.VERTICAL),
        )

        viewModel.album.observe(this) { state ->
            binding.apply {
                albumNameView.text = state.album.title
                artistNameView.text = state.album.artist
                publishedView.text = state.album.published
                genreNameView.text = state.album.genre
            }
        }

        viewModel.data.observe(this) { state ->

            playList = state.tracks
            adapter.submitList(playList)

            playList.forEachIndexed { index, track ->
                if (track.id == currentTrack) currentIndex = index
            }
        }
    }

    fun playerController(url: String) {
        val nextListener =
            MediaPlayer.OnCompletionListener {
                mediaObserver.apply {
                    onStop()
                    if (currentIndex <= playList.size) {
                        currentIndex++
                        currentTrack++
                        player?.setDataSource(BASE_URL + playList[currentIndex].file).let {
                            viewModel.isPlayed(currentTrack - 1)
                            viewModel.isPlayed(currentTrack)
                        }
                        onPlay()
                    } else {
                        player?.setDataSource(BASE_URL + playList[0].file)
                        onPlay()
                    }
                }
            }

        mediaObserver.apply {
            if (player != null && player?.isPlaying == true) {
                if (currentTrack != currentIndex + 1) {
                    onStop()
                    viewModel.isPlayed(currentIndex + 1)
                    player?.setDataSource(url)
                    onPlay()
                } else {
                    onStop()
                }
            } else if (player != null) {
                player?.setOnCompletionListener(nextListener)
                player?.setDataSource(url)
                onPlay()
            }
        }
    }
}