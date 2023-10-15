package com.apcoding.wallpaperapp.ui.screens.common.favourite

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Bitmap
import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ContentAlpha
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.apcoding.wallpaperapp.data.local.dao.FavUrlsViewModel
import com.apcoding.wallpaperapp.model.FavouriteUrls
import com.apcoding.wallpaperapp.ui.screens.common.wallpaper.WallpaperFullScreenViewModel
import com.apcoding.wallpaperapp.ui.theme.bottomAppBarBackgroundColor
import com.apcoding.wallpaperapp.ui.theme.bottomAppBarContentColor
import com.apcoding.wallpaperapp.util.getBitmapFromUrl
import com.apcoding.wallpaperapp.util.loadReducedSizeBitmapFromUrl
import com.apcoding.wallpaperapp.util.shareWallpaper
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun FavouriteWallpaperFullScreen(
    fullUrl: String,
    regularUrl: String,
    navController: NavHostController,
    favUrlsViewModel: FavUrlsViewModel,
    wallpaperFullScreenViewModel: WallpaperFullScreenViewModel,
    context: Context,
    scope: CoroutineScope,
) {
    val activity = (context as? Activity)
    val configuration = LocalConfiguration.current
    var smallSizeImage by remember { mutableStateOf<Bitmap?>(null) }
    var orginalImage by remember { mutableStateOf<Bitmap?>(null) }
    var finalImageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val snackBarHostState = remember { androidx.compose.material3.SnackbarHostState() }
    var expanded by remember { mutableStateOf(false) }
    val captureController = rememberCaptureController()
    val matrix by remember { mutableStateOf(ColorMatrix()) }
    matrix.setToSaturation(wallpaperFullScreenViewModel.saturationSliderValue.value)
    val colorFilter = ColorFilter.colorMatrix(matrix)

    LaunchedEffect(key1 = "image", block = {
        smallSizeImage = loadReducedSizeBitmapFromUrl(fullUrl)
        orginalImage = getBitmapFromUrl(fullUrl)
        finalImageBitmap = orginalImage
    })

    DisposableEffect(Unit) {
        val activity = context as? ComponentActivity
        activity?.requestedOrientation = when (configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
            Configuration.ORIENTATION_PORTRAIT -> ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
            else -> activity?.requestedOrientation ?: ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }

        onDispose {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }
    Scaffold(snackbarHost = {
        SnackbarHost(
            hostState = snackBarHostState
        )
    }) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .alpha(ContentAlpha.medium)
                .animateContentSize(),
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        IconButton(onClick = {
                            navController.popBackStack()
                            wallpaperFullScreenViewModel.saturationSliderPosition.value = 1f
                            wallpaperFullScreenViewModel.saturationSliderValue.value = 1f
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }

                        Row {
                            AnimatedVisibility(
                                visible = !expanded,
                                enter = scaleIn() + fadeIn(),
                                exit = scaleOut() + fadeOut()
                            ) {
                                if ((wallpaperFullScreenViewModel.saturationSliderValue.value == 1f && wallpaperFullScreenViewModel.saturationSliderPosition.value == 1f)) {
                                    Icon(
                                        imageVector = Icons.Default.MoreVert,
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.MoreVert,
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                }

                            }
                        }

                    }
                }
            }

        }
        AnimatedVisibility(
            visible = !expanded,
            enter = slideInVertically { 3000 } + scaleIn(),
            exit = slideOutVertically { 500 })
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                FloatingActionButton(
                    onClick = {
                        shareWallpaper(
                            context,
                            finalImageBitmap,
                            shareWithWhatsAppOnly = false,
                            saveToDrive = false
                        )
                    },
                    modifier = Modifier.padding(8.dp),
                    backgroundColor = androidx.compose.material.MaterialTheme.colors.bottomAppBarBackgroundColor
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = null,
                        tint = androidx.compose.material.MaterialTheme.colors.bottomAppBarContentColor
                    )
                }

                FloatingActionButton(
                    onClick = {
                        scope.launch(Dispatchers.IO) {
                            favUrlsViewModel.deleteFavouriteUrl(
                                FavouriteUrls(
                                    wallpaperFullScreenViewModel.id, fullUrl, regularUrl
                                )
                            )
                        }
                        scope.launch {
                            snackBarHostState.showSnackbar(
                                "Removed from favourites",
                                withDismissAction = true,
                                duration = SnackbarDuration.Short
                            )
                        }
                    },
                    modifier = Modifier.padding(8.dp),
                    backgroundColor = androidx.compose.material.MaterialTheme.colors.bottomAppBarBackgroundColor
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = androidx.compose.material.MaterialTheme.colors.bottomAppBarContentColor
                    )
                }

                FloatingActionButton(
                    onClick = {
                        wallpaperFullScreenViewModel.setOriginalWallpaperDialog.value = true
                    },
                    modifier = Modifier.padding(8.dp),
                    backgroundColor = androidx.compose.material.MaterialTheme.colors.bottomAppBarBackgroundColor
                ) {
                    Icon(
                        imageVector = Icons.Default.Wallpaper,
                        contentDescription = null,
                        tint = androidx.compose.material.MaterialTheme.colors.bottomAppBarContentColor
                    )

                }
            }
        }
    }
}



