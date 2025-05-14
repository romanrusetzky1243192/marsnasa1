package com.example.marsnasa1.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsnasa1.database.AppDatabase
import com.example.marsnasa1.entity.FavoriteImage
import com.example.marsnasa1.repository.ImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ImageViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ImageRepository

    init {
        val dao = AppDatabase.getDatabase(application).favoriteImageDao()
        repository = ImageRepository(dao)
    }

    fun addToFavorites(imageUrl: String, earthDate: String) = viewModelScope.launch {
        repository.addToFavorites(imageUrl, earthDate)
    }

    fun removeFromFavorites(imageUrl: String) = viewModelScope.launch {
        repository.removeFromFavorites(imageUrl)
    }

    fun isFavorite(imageUrl: String): Boolean {
        return runBlocking {
            repository.isFavorite(imageUrl)
        }
    }

    val allFavorites: Flow<List<FavoriteImage>> = repository.allFavorites
}