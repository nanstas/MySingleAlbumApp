package ru.netology.mysinglealbumapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.mysinglealbumapp.R
import ru.netology.mysinglealbumapp.databinding.ItemTrackBinding
import ru.netology.mysinglealbumapp.dto.Track

interface OnInteractionListener {
    fun onPlayPause(track: Track) {}
}

class TrackAdapter(
    private val onInteractionListener: OnInteractionListener
) : ListAdapter<Track, TrackViewHolder>(TrackDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val binding = ItemTrackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrackViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = getItem(position)
        holder.bind(track)
    }
}

class TrackViewHolder(
    private val binding: ItemTrackBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(track: Track) {
        binding.apply {
            trackNameView.text = track.file
            if (track.isPlayed) {
                playPauseButtonView.setIconResource(R.drawable.ic_play_24)
            } else {
                playPauseButtonView.setIconResource(R.drawable.ic_stop_24)
            }
            playPauseButtonView.setOnClickListener {
                onInteractionListener.onPlayPause(track)
            }
        }
    }
}

class TrackDiffCallback : DiffUtil.ItemCallback<Track>() {
    override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem == newItem
    }
}