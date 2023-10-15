package com.apcoding.wallpaperapp.ui.screens.common.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.ExperimentalPagingApi
import com.apcoding.wallpaperapp.BuildConfig
import com.apcoding.wallpaperapp.R
import com.apcoding.wallpaperapp.ui.screens.common.home.HomeViewModel
import com.apcoding.wallpaperapp.ui.theme.bottomAppBarContentColor
import com.apcoding.wallpaperapp.ui.theme.defaultFontFamily
import com.apcoding.wallpaperapp.ui.theme.iconColor
import com.apcoding.wallpaperapp.ui.theme.maven_pro_regular
import com.apcoding.wallpaperapp.ui.theme.textColor
import com.apcoding.wallpaperapp.ui.theme.topAppBarTitle
import com.apcoding.wallpaperapp.util.WallPaperTheme
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagingApi::class)
@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel,
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    onItemSelected: (WallPaperTheme) -> Unit,
    systemUiController: SystemUiController,
    context: Context,
    scope: CoroutineScope,
    homeViewModel: HomeViewModel,
) {

    val dataStore = DataStorePreferenceRepository(context)
    val themeValue = dataStore.getThemeValue.collectAsState(initial = 0)
    BackHandler {
        if (scaffoldState.drawerState.isOpen) {
            scope.launch {
                scaffoldState.drawerState.close()
            }
        } else {
            navController.navigate("home_screen")
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(), scaffoldState
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colors.background)
                .padding(10.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.theme),
                    color = MaterialTheme.colors.textColor,
                    fontSize = 16.sp,
                    style = TextStyle(
                        fontStyle = MaterialTheme.typography.subtitle1.fontStyle,
                        fontFamily = maven_pro_regular
                    ),
                    modifier = Modifier.weight(1f)
                )

                if (themeValue.value == 0) {
                    IconButton(onClick = {
                        onItemSelected(WallPaperTheme.fromOrdinal(WallPaperTheme.MODE_NIGHT.ordinal))
                        scope.launch {
                            dataStore.saveThemeValue(1)
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.DarkMode,
                            tint = MaterialTheme.colors.iconColor,
                            contentDescription = null
                        )
                    }
                }

                if (themeValue.value == 1) {
                    IconButton(onClick = {
                        onItemSelected(WallPaperTheme.fromOrdinal(WallPaperTheme.MODE_DAY.ordinal))
                        scope.launch {
                            dataStore.saveThemeValue(0)
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.DarkMode,
                            tint = Color.Yellow,
                            contentDescription = null
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.padding(2.dp))
            Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
            Spacer(modifier = Modifier.padding(2.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.wallpaper_info),
                    color = MaterialTheme.colors.textColor,
                    fontSize = 16.sp,
                    style = TextStyle(
                        fontStyle = MaterialTheme.typography.subtitle1.fontStyle,
                        fontFamily = maven_pro_regular
                    )
                )

                Switch(
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colors.bottomAppBarContentColor,
                        uncheckedThumbColor = MaterialTheme.colors.topAppBarTitle,
                        checkedTrackAlpha = 0.2f
                    ),
                    checked = homeViewModel.showUserDetails,
                    onCheckedChange = { homeViewModel.showUserDetails = it })
            }

            Spacer(modifier = Modifier.padding(2.dp))
            Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
            Spacer(modifier = Modifier.padding(2.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.tired_of_ads),
                    color = MaterialTheme.colors.textColor,
                    fontSize = 16.sp,
                    style = TextStyle(
                        fontStyle = MaterialTheme.typography.subtitle1.fontStyle,
                        fontFamily = maven_pro_regular
                    ),
                    modifier = Modifier
                )
                TextButton(onClick = { settingsViewModel.dialogState.value = true }) {
                    Text(
                        text = stringResource(id = R.string.remove_ads),
                        fontSize = 16.sp,
                        style = TextStyle(
                            fontStyle = MaterialTheme.typography.body1.fontStyle,
                            fontFamily = maven_pro_regular
                        ),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.bottomAppBarContentColor
                    )
                }
            }

            Spacer(modifier = Modifier.padding(2.dp))
            Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
            Spacer(modifier = Modifier.padding(2.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.about_us),
                    color = MaterialTheme.colors.textColor,
                    fontSize = 16.sp,
                    style = TextStyle(
                        fontStyle = MaterialTheme.typography.subtitle1.fontStyle,
                        fontFamily = maven_pro_regular
                    ),
                    modifier = Modifier
                )
                TextButton(onClick = {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data =
                        Uri.parse("https://hamzaazizofficial.github.io/wallpap_privacy_policy/")
                    ContextCompat.startActivity(context, intent, null)
                }) {
                    Text(
                        text = stringResource(id = R.string.policy),
                        fontSize = 16.sp,
                        style = TextStyle(
                            fontStyle = MaterialTheme.typography.body1.fontStyle,
                            fontFamily = maven_pro_regular
                        ),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.bottomAppBarContentColor
                    )
                }
            }

            Spacer(modifier = Modifier.padding(2.dp))
            Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
            Spacer(modifier = Modifier.padding(2.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.app_version),
                    color = MaterialTheme.colors.textColor,
                    fontSize = 16.sp,
                    style = TextStyle(
                        fontStyle = MaterialTheme.typography.subtitle1.fontStyle,
                        fontFamily = defaultFontFamily
                    ),
                    modifier = Modifier
                )
                Text(
                    text = "${BuildConfig.VERSION_CODE}",
                    color = MaterialTheme.colors.textColor,
                    fontSize = 16.sp,
                    style = TextStyle(
                        fontStyle = MaterialTheme.typography.body1.fontStyle,
                    )
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))
        }
    }
}

@OptIn(ExperimentalPagingApi::class)
@Composable
@Preview(showBackground = true)
fun SettingsScreenPreview() {
    val settingsViewModel = SettingsViewModel()
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()
    val coroutineScope = rememberCoroutineScope()
    val homeViewModel: HomeViewModel = viewModel()


    SettingsScreen(
        settingsViewModel = settingsViewModel,
        navController = rememberNavController(),
        scaffoldState = scaffoldState,
        onItemSelected = {},
        systemUiController = systemUiController,
        context = context,
        scope = coroutineScope,
        homeViewModel = homeViewModel
    )
}
