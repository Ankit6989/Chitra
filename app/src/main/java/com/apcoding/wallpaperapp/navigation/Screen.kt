package com.apcoding.wallpaperapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Favourite : Screen("favourite", "Favourite", Icons.Default.Favorite)
    object FavouriteFullScreen : Screen("fav_full_screen/{fullUrl}/{regularUrl}", "Favourite Wallpaper", Icons.Default.Wallpaper)
    object HomeScreen : Screen("home_screen", "Home", Icons.Default.Home)
    object HomeWallpaperFullScreen : Screen("home_wallpaper_screen/{regularUrl}/{fullUrl}", "Wallpaper", Icons.Default.Wallpaper)
    object Search : Screen("search_screen", "Search", Icons.Default.Search)
    object MainScreen : Screen("main_screen", "Main", Icons.Default.Home)
    object Settings : Screen("settings_screen", "Settings", Icons.Default.Settings)
}
