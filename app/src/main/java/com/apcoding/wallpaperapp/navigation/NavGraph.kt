package com.apcoding.wallpaperapp.navigation

import android.content.Context
import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.systemuicontroller.SystemUiController
import com.apcoding.wallpaperapp.data.local.dao.FavUrlsViewModel
import com.apcoding.wallpaperapp.ui.screens.common.favourite.FavouriteScreen
import com.apcoding.wallpaperapp.ui.screens.common.favourite.FavouriteWallpaperFullScreen
import com.apcoding.wallpaperapp.ui.screens.common.home.HomeScreen
import com.apcoding.wallpaperapp.ui.screens.common.home.HomeViewModel
import com.apcoding.wallpaperapp.ui.screens.common.search.SearchScreen
import com.apcoding.wallpaperapp.ui.screens.common.search.SearchViewModel
import com.apcoding.wallpaperapp.ui.screens.common.settings.SettingsScreen
import com.apcoding.wallpaperapp.ui.screens.common.settings.SettingsViewModel
import com.apcoding.wallpaperapp.ui.screens.common.wallpaper.WallpaperFullScreen
import com.apcoding.wallpaperapp.ui.screens.common.wallpaper.WallpaperFullScreenViewModel
import com.apcoding.wallpaperapp.util.WallPaperTheme
import kotlinx.coroutines.CoroutineScope

@OptIn(
    ExperimentalPagingApi::class,
    ExperimentalCoilApi::class,
    ExperimentalFoundationApi::class,
    ExperimentalAnimationApi::class
)

@Composable
fun NavGraph(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    onItemSelected: (WallPaperTheme) -> Unit,
    currentRoute: String?,
    context: Context,
    scope: CoroutineScope,
    systemUiController: SystemUiController,
    wallpaperFullScreenViewModel: WallpaperFullScreenViewModel,
) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val wallpaperFullScreenViewModel: WallpaperFullScreenViewModel = viewModel()
    val settingsViewModel: SettingsViewModel = hiltViewModel()
    val searchViewModel: SearchViewModel = hiltViewModel()
   // val randomScreenViewModel: RandomScreenViewModel = viewModel()
    val favUrlsViewModel: FavUrlsViewModel = viewModel()
    val homeItems = homeViewModel.itemsFlow.collectAsLazyPagingItems()
    val favouriteItemsData = favUrlsViewModel.getAllFavUrls.observeAsState(listOf())

    AnimatedNavHost(
        navController, startDestination = Screen.HomeScreen.route,
    ) {

        composable(Screen.HomeScreen.route, enterTransition = {
            when (currentRoute) {
                Screen.HomeScreen.route -> fadeIn(animationSpec = tween(600))
                else -> null
            }
        }) {
            HomeScreen(
                navController,
                homeViewModel,
                scaffoldState,
                homeItems,
//                homeRefreshState,
                context,
                scope,
                systemUiController
            )
        }

        composable(Screen.Search.route, enterTransition = {
            when (currentRoute) {
                Screen.Search.route -> fadeIn(animationSpec = tween(600)) + slideInVertically { 1800 }
                else -> null
            }
        }, exitTransition = {
            when (currentRoute) {
                Screen.Search.route -> fadeOut(animationSpec = tween(600)) + slideOutVertically { 1800 }
                else -> null
            }
        }) {
            SearchScreen(
                navController = navController,
                searchViewModel,
                homeViewModel,
                //                homeRefreshState,
                context
            )
        }

        composable(Screen.Settings.route, enterTransition = {
            when (currentRoute) {
                Screen.Settings.route -> fadeIn(animationSpec = tween(600)) + slideInHorizontally { 1800 }
                else -> null
            }
        }) {
            SettingsScreen(
                settingsViewModel,
                navController,
                scaffoldState,
                onItemSelected,
                systemUiController,
                context,
                scope,
                homeViewModel
            )
        }

        composable(Screen.Favourite.route) {
            FavouriteScreen(
                favUrlsViewModel, navController, context, favouriteItemsData, systemUiController
            )
        }

//        composable(Screen.Random.route) {
//            RandomScreen(
//                navController,
//                scaffoldState,
//                randomScreenViewModel,
//                randomItems,
//                systemUiController,
//                context,
//                scope,
////                randomRefreshState,
//                homeViewModel
//            )
//        }

        composable(Screen.HomeWallpaperFullScreen.route, enterTransition = {
            when (currentRoute) {
                Screen.HomeWallpaperFullScreen.route -> fadeIn(animationSpec = tween(600)) + scaleIn()
                else -> null
            }
        }, exitTransition = {
            when (currentRoute) {
                Screen.HomeWallpaperFullScreen.route -> fadeOut(animationSpec = tween(600)) + scaleOut()
                else -> null
            }
        }, arguments = listOf(navArgument("regularUrl") {
            nullable = true
            type = NavType.StringType
        }, navArgument("fullUrl") {
            nullable = true
            type = NavType.StringType
        })) {
            val regularUrl = it.arguments?.getString("regularUrl")
            val fullUrl = it.arguments?.getString("fullUrl")
            if (regularUrl != null && fullUrl != null) {
                WallpaperFullScreen(
                    regularUrl,
                    fullUrl,
                    navController,
                    favUrlsViewModel,
                    wallpaperFullScreenViewModel,
                    scope,
                    systemUiController,
                    context
                )
            }
        }

        composable(Screen.FavouriteFullScreen.route, enterTransition = {
            when (currentRoute) {
                Screen.FavouriteFullScreen.route -> fadeIn(animationSpec = tween(600)) + scaleIn()
                else -> null
            }
        }, exitTransition = {
            when (currentRoute) {
                Screen.FavouriteFullScreen.route -> fadeOut(animationSpec = tween(600)) + scaleOut()
                else -> null
            }
        }, arguments = listOf(navArgument("fullUrl") {
            nullable = true
            type = NavType.StringType
        }, navArgument("regularUrl") {
            nullable = true
            type = NavType.StringType
        })) {
            val fullUrl = it.arguments?.getString("fullUrl")
            val regularUrl = it.arguments?.getString("regularUrl")
            if (fullUrl != null && regularUrl != null) {
                FavouriteWallpaperFullScreen(
                    fullUrl,
                    regularUrl,
                    navController,
                    favUrlsViewModel,
                    wallpaperFullScreenViewModel,
                    context,
                    scope
                )
            }
        }
    }
}