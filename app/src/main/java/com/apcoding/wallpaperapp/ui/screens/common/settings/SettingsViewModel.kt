package com.apcoding.wallpaperapp.ui.screens.common.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {
    private val themes = listOf(
        "Original",
        "Dracula"
    )
    var value = mutableStateOf(themes[0])

    var dialogState = mutableStateOf(false)
    var dialogStateAbout = mutableStateOf(false)
}