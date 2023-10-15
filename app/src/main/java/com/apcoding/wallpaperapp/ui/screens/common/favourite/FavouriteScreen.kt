package com.apcoding.wallpaperapp.ui.screens.common.favourite

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NetworkCheck
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.apcoding.wallpaperapp.R
import com.apcoding.wallpaperapp.data.local.dao.FavUrlsViewModel
import com.apcoding.wallpaperapp.model.FavouriteUrls
import com.apcoding.wallpaperapp.ui.theme.maven_pro_regular
import com.apcoding.wallpaperapp.ui.theme.systemBarColor
import com.apcoding.wallpaperapp.ui.theme.textColor
import com.apcoding.wallpaperapp.ui.theme.topAppBarContentColor
import com.apcoding.wallpaperapp.util.isOnline
import com.google.accompanist.systemuicontroller.SystemUiController

@Composable
fun FavouriteScreen(
    favUrlsViewModel: FavUrlsViewModel,
    navController: NavHostController,
    context: Context,
    favouriteItemsData: State<List<FavouriteUrls>>,
    systemUiController: SystemUiController,
) {
    systemUiController.setSystemBarsColor(color = MaterialTheme.colors.systemBarColor)
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation

    if (!isOnline(context)) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            Icon(
                imageVector = Icons.Default.NetworkCheck,
                contentDescription = null,
                tint = MaterialTheme.colors.topAppBarContentColor,
                modifier = Modifier.size(100.dp)
            )
            Text(
                text = stringResource(id = R.string.check_network) + "\n" + stringResource(id = R.string.reopen_app),
                color = MaterialTheme.colors.textColor,
                fontFamily = maven_pro_regular,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            contentAlignment = if (favouriteItemsData.value.isEmpty()) Alignment.Center else Alignment.TopCenter

        ) {

        }


    }

}