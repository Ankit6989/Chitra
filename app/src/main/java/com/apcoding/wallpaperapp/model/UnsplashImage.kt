package com.apcoding.wallpaperapp.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.apcoding.wallpaperapp.util.Constants.UNSPLASH_IMAGE_TABLE
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = UNSPLASH_IMAGE_TABLE)
data class UnsplashImage(
    //This annotation indicates that the following property (id) is the primary key for the data.
    //The parameter autoGenerate = false suggests that the primary key values are not auto-generated, meaning they need to be provided explicitly.
    @PrimaryKey(autoGenerate = false)
    val id: String,

    //This annotation indicates that the following property (urls, user) contains embedded data.
    //The urls and user properties seem to hold complex data structures. We can use the @Embedded annotation to indicate that the data is embedded.
    @Embedded
    val urls: Urls,
    val likes: Int,
    @Embedded
    val user: User,
    val height: Int
)