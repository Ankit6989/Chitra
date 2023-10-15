package com.apcoding.wallpaperapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.paging.ExperimentalPagingApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.apcoding.wallpaperapp.data.local.dao.FavUrlsViewModel
import com.apcoding.wallpaperapp.navigation.NavGraph
import com.apcoding.wallpaperapp.navigation.Screen
import com.apcoding.wallpaperapp.ui.screens.common.BottomBar
import com.apcoding.wallpaperapp.ui.screens.common.NavDrawer
import com.apcoding.wallpaperapp.ui.screens.common.home.HomeViewModel
import com.apcoding.wallpaperapp.ui.screens.common.settings.SettingsViewModel
import com.apcoding.wallpaperapp.ui.screens.common.wallpaper.WallpaperFullScreenViewModel
import com.apcoding.wallpaperapp.ui.theme.systemBarColor
import com.apcoding.wallpaperapp.util.WallPaperTheme
import com.hamza.wallpap.ui.screens.common.TopBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagingApi::class)
@Composable
fun MainScreen(
    wallpaperFullScreenViewModel: WallpaperFullScreenViewModel,
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    favUrlsViewModel: FavUrlsViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = viewModel(),
    onItemSelected: (WallPaperTheme) -> Unit,
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = MaterialTheme.colors.systemBarColor)
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            if (
                currentRoute.equals(Screen.HomeScreen.route) or
                currentRoute.equals(Screen.Favourite.route) or
                currentRoute.equals(Screen.Settings.route)
            ) {
                TopBar(
                    onNavButtonClick = {
                        if (!currentRoute.equals(Screen.HomeScreen.route) && !currentRoute.equals(
                                Screen.Settings.route
                            )
                        ) {
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        } else {
                            navController.popBackStack()
                        }
                    },
                    currentRoute,
                    onSearchClicked = {
                        navController.navigate(Screen.Search.route)
                    },
                    onUserDetailsClicked = {
                    },
                    homeViewModel,
                    favUrlsViewModel,
                    context
                )
            }
        },
        bottomBar = {
            if (
                currentRoute.equals(Screen.HomeScreen.route) or
//                currentRoute.equals(Screen.Random.route) or
                currentRoute.equals(Screen.Favourite.route)
            ) {
                BottomBar(navController = navController)
            }
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            NavDrawer(
                scaffoldState = scaffoldState,
                navController,
                scope,
                settingsViewModel,
                context
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding)) {
            NavGraph(
                navController = navController,
                scaffoldState,
                onItemSelected,
                currentRoute,
                context,
                scope,
                systemUiController,
                wallpaperFullScreenViewModel
            )
        }
    }
}
