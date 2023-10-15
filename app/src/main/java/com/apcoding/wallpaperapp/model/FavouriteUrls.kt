package com.apcoding.wallpaperapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.apcoding.wallpaperapp.util.Constants
import kotlinx.serialization.Serializable

@Entity(tableName = Constants.UNSPLASH_FAV_URLS_TABLE)
@Serializable
data class FavouriteUrls(
    val id: Int,
    @PrimaryKey
    val full: String,
    val regular: String
)

//This code defines a data class FavouriteUrls that represents data related to favorite URLs, with properties for an ID, a full URL (marked as the primary key), and a regular-sized URL.
// It's likely used in conjunction with a database (e.g., Android Room) and can be serialized/deserialized for storage and retrieval.