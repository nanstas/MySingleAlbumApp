package ru.netology.mysinglealbumapp.viewModel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.netology.mymusicplayer.model.AlbumModel
import ru.netology.mymusicplayer.model.TrackModel
import ru.netology.mysinglealbumapp.db.AppDb
import ru.netology.mysinglealbumapp.repositoriy.AlbumRepository
import ru.netology.mysinglealbumapp.repositoriy.AlbumRepositoryImpl

class AlbumViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AlbumRepository = AlbumRepositoryImpl(AppDb.getInstance(context = application).trackDao())

    val data: LiveData<TrackModel> = repository.data.map(::TrackModel)

    private val _album = MutableLiveData(AlbumModel())
    val album: LiveData<AlbumModel>
        get() = _album

    init {
        loadAlbum()
    }

    private fun loadAlbum() = viewModelScope.launch {
        try {
            _album.value = AlbumModel(loading = true)
            _album.value = AlbumModel(repository.getAlbum())
            repository.insertTracks()
        } catch (e: Exception) {
            _album.value = AlbumModel(error = true)
        }
    }

    fun isPlayed(id: Int) = viewModelScope.launch {
        try {
            repository.isPlayed(id)
        } catch (e: Exception) {
            _album.value = AlbumModel(error = true)
        }
    }
}