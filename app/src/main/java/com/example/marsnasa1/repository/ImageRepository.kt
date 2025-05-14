package com.example.marsnasa1.repository

import com.example.marsnasa1.dao.FavoriteImageDao
import com.example.marsnasa1.entity.FavoriteImage
import kotlinx.coroutines.flow.Flow

class ImageRepository(private val favoriteImageDao: FavoriteImageDao) {
    val allFavorites: Flow<List<FavoriteImage>> = favoriteImageDao.getAllFavorites()

    suspend fun addToFavorites(imageUrl: String, earthDate: String) {
        val image = FavoriteImage(imageUrl = imageUrl, imageEarthDate = earthDate)
        favoriteImageDao.insert(image)
    }

    suspend fun removeFromFavorites(imageUrl: String) {
        favoriteImageDao.getByUrl(imageUrl)?.let { favoriteImageDao.delete(it) }
    }

    suspend fun isFavorite(imageUrl: String): Boolean {
        return favoriteImageDao.getByUrl(imageUrl) != null
    }
}