package com.apcoding.wallpaperapp.model

import kotlinx.serialization.Serializable

@Serializable
data class UserLinks(
    val html: String
)

//This code defines a data class UserLinks that represents user-related links, including an html property for an HTML link. The use of the @Serializable annotation indicates that instances of this class can be converted to and from serialized formats like JSON.
