package com.example.marsnasa1.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.marsnasa1.entity.FavoriteImage
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteImageDao {
    @Insert
    suspend fun insert(image: FavoriteImage)

    @Delete
    suspend fun delete(image: FavoriteImage)

    @Query("SELECT * FROM favorite_images ORDER BY earth_date DESC")
    fun getAllFavorites(): Flow<List<FavoriteImage>>

    @Query("SELECT * FROM favorite_images WHERE image_url = :url")
    suspend fun getByUrl(url: String): FavoriteImage?
}