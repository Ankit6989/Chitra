package com.apcoding.wallpaperapp.ui.screens.common.wallpaper

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.ViewModel

class WallpaperFullScreenViewModel : ViewModel() {
    var scale by mutableStateOf(ContentScale.Crop)
    var showFitScreenBtn by mutableStateOf(true)
    var showCropScreenBtn by mutableStateOf(false)
    var id by mutableIntStateOf(0)
    var setOriginalWallpaperDialog = mutableStateOf(false)
    var setWallpaperAs by mutableIntStateOf(1)
    var interstitialState = mutableIntStateOf(0)
    var saturationSliderValue = mutableFloatStateOf(1f)
    var saturationSliderPosition = mutableFloatStateOf(1f)
    var scaleFitState by mutableStateOf(true)
    var scaleCropState by mutableStateOf(false)
    var scaleStretchState by mutableStateOf(false)
    var finalScaleState by mutableStateOf(scaleFitState)
}