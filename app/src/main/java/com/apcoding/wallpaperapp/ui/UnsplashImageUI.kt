package com.apcoding.wallpaperapp.ui

import com.apcoding.wallpaperapp.model.UnsplashImage
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashImageUI(
    val image: UnsplashImage,
    val height: Int
)
