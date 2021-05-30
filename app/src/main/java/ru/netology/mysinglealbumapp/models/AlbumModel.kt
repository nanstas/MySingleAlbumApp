package ru.netology.mymusicplayer.model

import ru.netology.mysinglealbumapp.dto.Album
import ru.netology.mysinglealbumapp.utils.Empty

data class AlbumModel(
    val album: Album = Empty.emptyAlbum,
    val loading: Boolean = false,
    val error: Boolean = false,
)