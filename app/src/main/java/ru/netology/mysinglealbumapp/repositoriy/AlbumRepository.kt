package ru.netology.mysinglealbumapp.repositoriy

import androidx.lifecycle.LiveData
import ru.netology.mysinglealbumapp.dto.Album
import ru.netology.mysinglealbumapp.dto.Track


interface AlbumRepository {
    val data: LiveData<List<Track>>
    suspend fun getAlbum(): Album
    suspend fun insertTracks()
    suspend fun isPlayed(id: Int)
}