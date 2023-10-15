package com.apcoding.wallpaperapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.apcoding.wallpaperapp.ui.MainScreen
import com.apcoding.wallpaperapp.ui.MainViewModel
import com.apcoding.wallpaperapp.ui.animation.CircularReveal
import com.apcoding.wallpaperapp.ui.screens.common.wallpaper.WallpaperFullScreenViewModel
import com.apcoding.wallpaperapp.ui.theme.WallPaperAppTheme
import com.apcoding.wallpaperapp.util.ThemeSetting
import com.apcoding.wallpaperapp.util.ThemeSettingPreference
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var themeSetting: ThemeSetting

    @RequiresApi(Build.VERSION_CODES.S)
    @OptIn(ExperimentalComposeUiApi::class, ExperimentalAnimationApi::class, ExperimentalPagingApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            mainViewModel.isLoading.value
        }
        setContent {
            val wallpaperFullScreenViewModel =
                ViewModelProvider(this)[WallpaperFullScreenViewModel::class.java]

            // showing Admob Interstitial Ad (You can call this function from anywhere)
//            showAdmobInterstitialAd(this)

            themeSetting = ThemeSettingPreference(context = LocalContext.current)
            val theme = themeSetting.themeStream.collectAsState()
            val useDarkColors = when (theme.value) {
//
                com.apcoding.wallpaperapp.util.WallPaperTheme.MODE_DAY -> false
                com.apcoding.wallpaperapp.util.WallPaperTheme.MODE_NIGHT -> true
            }

            val navController = rememberAnimatedNavController()
            CircularReveal(
                targetState = useDarkColors,
                animationSpec = tween(1600, easing = LinearOutSlowInEasing)
            ) { isDark ->
                WallPaperAppTheme(darkTheme = isDark) {
                    androidx.compose.material.Surface(
                        modifier = Modifier.semantics { testTagsAsResourceId = true },
                        color = androidx.compose.material.MaterialTheme.colors.background
                    ) {
                        MainScreen(
                            wallpaperFullScreenViewModel,
                            navController = navController,
                            onItemSelected = { theme -> themeSetting.theme = theme }
                        )
                    }
                }
            }
        }
    }
}
