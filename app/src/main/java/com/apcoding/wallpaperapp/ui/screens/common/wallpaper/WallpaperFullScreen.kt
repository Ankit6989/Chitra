package com.apcoding.wallpaperapp.ui.screens.common.wallpaper

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.compose.material.ContentAlpha
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.InvertColors
import androidx.compose.material.icons.filled.InvertColorsOff
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.Fullscreen
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SnackbarDuration
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
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.apcoding.wallpaperapp.data.local.dao.FavUrlsViewModel
import com.apcoding.wallpaperapp.model.FavouriteUrls
import com.apcoding.wallpaperapp.ui.theme.bottomAppBarBackgroundColor
import com.apcoding.wallpaperapp.ui.theme.bottomAppBarContentColor
import com.apcoding.wallpaperapp.ui.theme.iconColor
import com.apcoding.wallpaperapp.ui.theme.systemBarColor
import com.apcoding.wallpaperapp.util.getBitmapFromUrl
import com.apcoding.wallpaperapp.util.loadReducedSizeBitmapFromUrl
import com.apcoding.wallpaperapp.util.shareWallpaper
import com.google.accompanist.systemuicontroller.SystemUiController
import dev.shreyaspatil.capturable.Capturable
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun WallpaperFullScreen(
    regularUrl: String,
    fullUrl: String,
    navController: NavHostController,
    favUrlsViewModel: FavUrlsViewModel,
    wallpaperFullScreenViewModel: WallpaperFullScreenViewModel,
    scope: CoroutineScope,
    systemUiController: SystemUiController,
    context: Context,
) {
    systemUiController.setSystemBarsColor(color = androidx.compose.material.MaterialTheme.colors.systemBarColor)
    val configuration = LocalConfiguration.current
    var expanded by remember { mutableStateOf(false) }
    val activity = (context as? Activity)


    val matrix by remember { mutableStateOf(ColorMatrix()) }
    matrix.setToSaturation(wallpaperFullScreenViewModel.saturationSliderValue.value)
    val colorFilter = ColorFilter.colorMatrix(matrix)

    var smallSizeImage by remember { mutableStateOf<Bitmap?>(null) }
    var originalImage by remember { mutableStateOf<Bitmap?>(null) }
    var finalImageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val snackBarHostState = remember { androidx.compose.material3.SnackbarHostState() }


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

    LaunchedEffect(key1 = "bitmap", block = {
        originalImage = getBitmapFromUrl(fullUrl)
        smallSizeImage = loadReducedSizeBitmapFromUrl(fullUrl)
        finalImageBitmap = originalImage
    })

    val captureController = rememberCaptureController()

    BackHandler {
        navController.popBackStack()
        wallpaperFullScreenViewModel.saturationSliderPosition.value = 1f
        wallpaperFullScreenViewModel.saturationSliderValue.value = 1f
    }

    androidx.compose.material3.Scaffold(snackbarHost = {
        androidx.compose.material3.SnackbarHost(
            hostState = snackBarHostState
        )
    }) { padding ->
        Box(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .background(androidx.compose.material.MaterialTheme.colors.background),
            contentAlignment = Alignment.BottomCenter
        ) {
            Capturable(controller = captureController, onCaptured = { bitmap, _ ->
                if (bitmap != null) {
                    finalImageBitmap = bitmap.asAndroidBitmap()
                }
            }, content = {
                SubcomposeAsyncImage(
                    model = smallSizeImage,
                    contentScale = wallpaperFullScreenViewModel.scale,
                    contentDescription = null,
                    colorFilter = colorFilter
                ) {
                    val state = painter.state
                    if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            SubcomposeAsyncImage(
                                modifier = Modifier.fillMaxSize(),
                                contentScale = wallpaperFullScreenViewModel.scale,
                                model = regularUrl,
                                contentDescription = null
                            )
                            LinearProgressIndicator(
                                modifier = Modifier.align(Alignment.BottomCenter),
                                color = androidx.compose.material.MaterialTheme.colors.secondary
                            )
                        }
                    } else {
                        SubcomposeAsyncImageContent(
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            })

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(ContentAlpha.medium)
                    .align(Alignment.TopEnd)
                    .animateContentSize(),
                color = Color.Black
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .height(60.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        IconButton(
                            onClick = {
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
                                if ((wallpaperFullScreenViewModel.saturationSliderPosition.value != 1f &&
                                            wallpaperFullScreenViewModel.saturationSliderValue.value != 1f)
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Download,
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                }
                            }

                            if (wallpaperFullScreenViewModel.showFitScreenBtn) {
                                IconButton(
                                    onClick = {
                                        wallpaperFullScreenViewModel.scale = ContentScale.Fit
                                        wallpaperFullScreenViewModel.showFitScreenBtn = false
                                        wallpaperFullScreenViewModel.showCropScreenBtn = true
                                    }) {
                                    Icon(
                                        imageVector = Icons.Default.Fullscreen,
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                }
                            }

                            if (wallpaperFullScreenViewModel.showCropScreenBtn) {
                                IconButton(
                                    onClick = {
                                        wallpaperFullScreenViewModel.scale = ContentScale.Crop
                                        wallpaperFullScreenViewModel.showCropScreenBtn = false
                                        wallpaperFullScreenViewModel.showFitScreenBtn = true
                                    }) {
                                    Icon(
                                        imageVector = Icons.Rounded.Fullscreen,
                                        contentDescription = null,
                                        tint = androidx.compose.material.MaterialTheme.colors.bottomAppBarContentColor
                                    )
                                }
                            }

                            if (expanded) {
                                IconButton(
                                    onClick = {
                                        expanded =
                                            if (wallpaperFullScreenViewModel.saturationSliderValue.value == 1f && wallpaperFullScreenViewModel.saturationSliderPosition.value == 1f) {
                                                false
                                            } else {
                                                captureController.capture()
                                                false
                                            }
                                    }) {
                                    Icon(
                                        imageVector = Icons.Default.Done,
                                        contentDescription = null,
                                        tint = if ((wallpaperFullScreenViewModel.saturationSliderValue.value != 1f && wallpaperFullScreenViewModel.saturationSliderPosition.value != 1f) || expanded) androidx.compose.material.MaterialTheme.colors.bottomAppBarContentColor else androidx.compose.material.MaterialTheme.colors.iconColor
                                    )
                                }
                            } else {
                                IconButton(
                                    onClick = {
                                        expanded = true
                                    }) {
                                    Icon(
                                        imageVector = Icons.Default.InvertColors,
                                        contentDescription = null,
                                        tint = if (wallpaperFullScreenViewModel.saturationSliderPosition.value != 1f) androidx.compose.material.MaterialTheme.colors.bottomAppBarContentColor else Color.White
                                    )
                                }
                            }
                        }
                    }
                    AnimatedVisibility(visible = expanded) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp, end = 0.dp, bottom = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Slider(
                                    modifier = Modifier.weight(4.5f),
                                    value = wallpaperFullScreenViewModel.saturationSliderPosition.value,
                                    onValueChange = {
                                        wallpaperFullScreenViewModel.saturationSliderPosition.value =
                                            it
                                    },
                                    valueRange = 0f..10f,
                                    onValueChangeFinished = {
                                        wallpaperFullScreenViewModel.saturationSliderValue.value =
                                            wallpaperFullScreenViewModel.saturationSliderPosition.value
                                    },
                                    colors = SliderDefaults.colors(
                                        activeTrackColor = androidx.compose.material.MaterialTheme.colors.bottomAppBarContentColor.copy(
                                            0.5f
                                        ),
                                        thumbColor = androidx.compose.material.MaterialTheme.colors.bottomAppBarContentColor
                                    )
                                )

                                IconButton(onClick = {
                                    wallpaperFullScreenViewModel.saturationSliderPosition.value = 1f
                                    wallpaperFullScreenViewModel.saturationSliderValue.value = 1f
                                    finalImageBitmap = originalImage
                                }, modifier = Modifier.weight(1f)) {
                                    Icon(
                                        imageVector = if (wallpaperFullScreenViewModel.saturationSliderPosition.value != 1f && wallpaperFullScreenViewModel.saturationSliderValue.value != 1f) Icons.Default.InvertColorsOff else Icons.Default.InvertColors,
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }

            AnimatedVisibility(visible = !expanded,
                enter = slideInVertically { 3000 } + scaleIn(),
                exit = slideOutVertically { 500 }) {
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
                        modifier = Modifier
                            .padding(8.dp),
                        backgroundColor = androidx.compose.material.MaterialTheme.colors.bottomAppBarBackgroundColor
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = null,
                            tint = androidx.compose.material.MaterialTheme.colors.bottomAppBarContentColor
                        )
                    }

                    AnimatedVisibility(
                        visible = wallpaperFullScreenViewModel.saturationSliderPosition.value == 1f && wallpaperFullScreenViewModel.saturationSliderValue.value == 1f,
                        enter = scaleIn() + fadeIn(),
                        exit = scaleOut() + fadeOut()
                    ) {
                        FloatingActionButton(
                            onClick = {
                                scope.launch(Dispatchers.IO) {
                                    wallpaperFullScreenViewModel.id += 1
                                    val favUrl =
                                        FavouriteUrls(
                                            wallpaperFullScreenViewModel.id,
                                            fullUrl,
                                            regularUrl
                                        )
                                    favUrlsViewModel.addToFav(favUrl)
                                    wallpaperFullScreenViewModel.interstitialState.value++
                                }
                                scope.launch {
                                    snackBarHostState.showSnackbar(
                                        "Added to Favourites!",
                                        withDismissAction = true,
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            },
                            modifier = Modifier
                                .padding(8.dp),
                            backgroundColor = androidx.compose.material.MaterialTheme.colors.bottomAppBarBackgroundColor
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null,
                                tint = androidx.compose.material.MaterialTheme.colors.bottomAppBarContentColor
                            )
                        }
                    }

                    FloatingActionButton(
                        onClick = {
                            wallpaperFullScreenViewModel.setOriginalWallpaperDialog.value = true
                        },
                        modifier = Modifier
                            .padding(8.dp),
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
}