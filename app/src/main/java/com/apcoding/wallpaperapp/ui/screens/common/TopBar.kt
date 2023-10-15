package com.hamza.wallpap.ui.screens.common

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import com.apcoding.wallpaperapp.R
import com.apcoding.wallpaperapp.data.local.dao.FavUrlsViewModel
import com.apcoding.wallpaperapp.navigation.Screen
import com.apcoding.wallpaperapp.ui.screens.common.home.HomeViewModel
import com.apcoding.wallpaperapp.ui.theme.topAppBarBackgroundColor
import com.apcoding.wallpaperapp.ui.theme.topAppBarContentColor
import com.apcoding.wallpaperapp.ui.theme.topAppBarTitle


@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalPagingApi::class, ExperimentalAnimationApi::class)
@Composable
fun TopBar(
    onNavButtonClick: () -> Unit = {},
    currentRoute: String?,
    onSearchClicked: () -> Unit,
    onUserDetailsClicked: () -> Unit,
    homeViewModel: HomeViewModel,
    favUrlsViewModel: FavUrlsViewModel,
    context: Context,
) {

    if (favUrlsViewModel.clearAllImagesDialogState.value) {
        ClearAllDialog(
            dialogState = favUrlsViewModel.clearAllImagesDialogState,
            context = context,
            stringResource(id = R.string.clear_images),
            currentRoute,
            favUrlsViewModel
        )
    }

    TopAppBar(
        title = {
            Text(
                text =
                if (currentRoute.equals(Screen.HomeScreen.route)) stringResource(id = R.string.home)
                else if (currentRoute.equals(Screen.Settings.route)) stringResource(id = R.string.settings)
//                else if (currentRoute.equals(Screen.Random.route)) stringResource(id = R.string.random)
                else if (currentRoute.equals(Screen.Favourite.route)) stringResource(id = R.string.favourite)
                else stringResource(id = R.string.home),
                color = MaterialTheme.colors.topAppBarTitle,
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {

            if (!currentRoute.equals(Screen.Settings.route)) {

//                if (currentRoute.equals(Screen.Home.route)) {
//                    IconButton(onClick = onUserDetailsClicked) {
//                        Icon(
//                            imageVector = if (homeViewModel.showUserDetails) Icons.Default.Info else Icons.Outlined.Info,
//                            contentDescription = stringResource(id = R.string.show_user_details),
//                            tint = MaterialTheme.colors.topAppBarContentColor
//                        )
//                    }
//                }
//                if (currentRoute.equals(Screen.Random.route)) {
//                    IconButton(onClick = onUserDetailsClicked) {
//                        Icon(
//                            imageVector = if (randomScreenViewModel.showUserDetails) Icons.Default.Info else Icons.Outlined.Info,
//                            contentDescription = stringResource(id = R.string.show_user_details),
//                            tint = MaterialTheme.colors.topAppBarContentColor
//                        )
//                    }
//                }

                if (currentRoute.equals(Screen.Favourite.route) &&
                    favUrlsViewModel.getAllFavUrls.observeAsState(listOf()).value.isNotEmpty()
                ) {
                    IconButton(
                        onClick = {
                            favUrlsViewModel.clearAllImagesDialogState.value = true
                        }) {
                        Icon(
                            imageVector = Icons.Default.DeleteSweep,
                            contentDescription = stringResource(id = R.string.clear_images_button),
                            tint = MaterialTheme.colors.topAppBarContentColor
                        )
                    }
                }
            }
        }
    )
}
