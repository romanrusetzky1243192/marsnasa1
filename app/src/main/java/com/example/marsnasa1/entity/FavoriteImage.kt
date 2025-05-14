package com.example.marsnasa1.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_images")
data class FavoriteImage(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "earth_date") val imageEarthDate: String,
    @ColumnInfo(name = "saved_at") val savedAt: Long = System.currentTimeMillis()
)