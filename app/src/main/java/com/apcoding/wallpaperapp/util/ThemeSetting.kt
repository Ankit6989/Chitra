package com.apcoding.wallpaperapp.util

import kotlinx.coroutines.flow.StateFlow

enum class WallPaperTheme {
    //  MODE_AUTO,
    MODE_DAY,
    MODE_NIGHT;

    companion object {
        fun fromOrdinal(ordinal: Int) = values()[ordinal]
    }
}

interface ThemeSetting {
    val themeStream: StateFlow<WallPaperTheme>
    var theme: WallPaperTheme
}