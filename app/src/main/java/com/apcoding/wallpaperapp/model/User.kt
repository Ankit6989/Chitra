package com.apcoding.wallpaperapp.model

import androidx.room.Embedded
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("links")
    @Embedded
    val userLinks: UserLinks,
    val username: String
)

// This code defines a data class User that represents user-related data, including a userLinks property for links and a username property for the user's username. The use of the @Serializable annotation indicates that instances of this class can be converted to and from serialized formats like JSON.
