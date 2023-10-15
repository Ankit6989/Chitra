package com.apcoding.wallpaperapp.ui.screens.common

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.apcoding.wallpaperapp.navigation.Screen
import com.apcoding.wallpaperapp.ui.theme.bottomAppBarBackgroundColor
import com.apcoding.wallpaperapp.ui.theme.bottomAppBarContentColor

@Composable
fun BottomBar(
    navController: NavHostController,
) {
    val items = listOf(
        Screen.HomeScreen,
//        Screen.Random,
        Screen.Favourite
    )
    CompositionLocalProvider(LocalElevationOverlay provides null) {
        BottomNavigation(
            backgroundColor = MaterialTheme.colors.bottomAppBarBackgroundColor,
            contentColor = MaterialTheme.colors.bottomAppBarContentColor
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            items.forEach { item ->
                BottomNavigationItem(
                    unselectedContentColor = Color.LightGray,
                    icon = {
                        Icon(imageVector = item.icon, item.title)
                    },
                    selected = currentRoute == item.route,
                    onClick = {
                        if (currentRoute != item.route) {
                            navController.navigate(item.route)
                        }
                    },
                    alwaysShowLabel = false
                )
            }
        }
    }
}
