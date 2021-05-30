package ru.netology.mymusicplayer.model

import ru.netology.mysinglealbumapp.dto.Track

data class TrackModel(
    val tracks: List<Track> = emptyList(),
    val empty: Boolean = false,
    val error: Boolean = false,
)