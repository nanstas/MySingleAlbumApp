package ru.netology.mysinglealbumapp.utils

import ru.netology.mysinglealbumapp.dto.Album
import ru.netology.mysinglealbumapp.dto.Track

object Empty {
    val emptyAlbum = Album(
        id = 0,
        title = "",
        subtitle = "",
        artist = "",
        published = "",
        genre = "",
        tracks = emptyList()
    )

    val emptyTracks = Track(
        id = 0,
        file = "",
        isPlayed = false,
    )
}