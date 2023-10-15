package com.apcoding.wallpaperapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(
    @SerialName("results")
    val images: List<UnsplashImage>
)

//This code defines a data class SearchResult that represents a structured data object for search results.
// It contains a property named images, which is a list of UnsplashImage objects. The use of the @Serializable annotation indicates that instances of this class can be converted to and from serialized formats like JSON.