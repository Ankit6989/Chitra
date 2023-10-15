package com.apcoding.wallpaperapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Urls(
    val regular: String,
    val full: String,
)

//this code defines a data class Urls that represents URLs, possibly related to images or resources.
// It includes two properties: regular for a regular-sized URL and full for a full-sized URL. The use of the @Serializable annotation indicates that instances of this class can be converted to and from serialized formats like JSON.