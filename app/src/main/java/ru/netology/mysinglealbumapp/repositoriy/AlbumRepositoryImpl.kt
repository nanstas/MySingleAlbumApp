package ru.netology.mysinglealbumapp.repositoriy

import androidx.lifecycle.map
import kotlinx.coroutines.*
import ru.netology.mysinglealbumapp.api.AlbumApi
import ru.netology.mysinglealbumapp.dao.TrackDao
import ru.netology.mysinglealbumapp.dto.Album
import ru.netology.mysinglealbumapp.entity.TrackEntity
import ru.netology.mysinglealbumapp.entity.toDto
import ru.netology.mysinglealbumapp.entity.toEntity
import ru.netology.mysinglealbumapp.exceptions.ApiException
import ru.netology.mysinglealbumapp.exceptions.NetworkException
import ru.netology.mysinglealbumapp.exceptions.UnknownException
import java.io.IOException

class AlbumRepositoryImpl(private val dao: TrackDao) : AlbumRepository {
    override val data = dao.getTracks().map(List<TrackEntity>::toDto)

    override suspend fun getAlbum(): Album {
        try {
            val response = AlbumApi.apiService.getAlbum()
            if (!response.isSuccessful) {
                throw ApiException(response.code(), response.message())
            }
            return response.body() ?: throw  ApiException(response.code(), response.message())
        } catch (e: IOException) {
            throw NetworkException
        } catch (e: Exception) {
            throw UnknownException
        }
    }

    override suspend fun insertTracks() {
        try {
            dao.insertTracks(getAlbum().tracks.toEntity())
        } catch (e: IOException) {
            throw NetworkException
        } catch (e: Exception) {
            throw UnknownException
        }
    }

    override suspend fun isPlayed(id: Int) {
        try {
            dao.isPlayed(id)
        } catch (e: IOException) {
            throw NetworkException
        } catch (e: Exception) {
            throw UnknownException
        }
    }
}