package com.apcoding.wallpaperapp.ui.screens.common.home

import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import com.apcoding.wallpaperapp.ui.theme.systemBarColor
import com.apcoding.wallpaperapp.ui.theme.topAppBarTitle
import androidx.compose.material.icons.filled.NetworkCheck
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.apcoding.wallpaperapp.R
import com.apcoding.wallpaperapp.ui.UnsplashImageUI
import com.apcoding.wallpaperapp.ui.screens.common.HomeListContent
import com.apcoding.wallpaperapp.ui.screens.common.search.SearchChips
import com.apcoding.wallpaperapp.ui.theme.maven_pro_regular
import com.apcoding.wallpaperapp.ui.theme.textColor
import com.apcoding.wallpaperapp.util.isOnline
import com.google.accompanist.systemuicontroller.SystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@ExperimentalCoilApi
@ExperimentalPagingApi
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    scaffoldState: ScaffoldState,
    items: LazyPagingItems<UnsplashImageUI>,
    context: Context,
    scope: CoroutineScope,
    systemUiController: SystemUiController,
) {
    systemUiController.setSystemBarsColor(color = MaterialTheme.colors.systemBarColor)
    val activity = (context as? Activity)

    BackHandler { //This code appears to set up a back button handler. It's likely used to respond to the back button press on a device, such as a phone.
        if (scaffoldState.drawerState.isOpen) {
            scope.launch {
                scaffoldState.drawerState.close()
            }
        } else {
            activity?.finish()
        }
    }

    if (!isOnline(context)) { //his is a function call. It presumably checks whether the device has an active internet connection. The context parameter is usually a reference to the current Android application context, which is used to access various resources and services.
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            Icon(
                tint = MaterialTheme.colors.topAppBarTitle,
                imageVector = Icons.Default.NetworkCheck,
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.size(10.dp))

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
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.background(MaterialTheme.colors.background)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
//This is a property or variable from homeViewModel. It represents a list or collection of items, such as wallpapers.
//This is a higher-order function called forEachIndexed, which is applied to the collection homeViewModel.wallpaperItems. Here's what it does:
//
//index is the index of the current element in the collection.
//s is the element itself at the current index.
                homeViewModel.wallpaperItems.forEachIndexed { index, s ->
                    SearchChips(text = s.title, //is likely a Composable function that displays a clickable chip with a text label. It's used to represent an item in the list.
                        selected = homeViewModel.selectedIndex.value == index,
                        onClick = {
                            homeViewModel.selectedIndex.value = index
                            homeViewModel.query.value = homeViewModel.wallpaperItems[index].query
                        })
                    Spacer(modifier = Modifier.padding(horizontal = 6.dp))
                }
            }
            HomeListContent(
                items = items,
                navController,
                homeViewModel
//                onRefresh = { items.refresh() },
//                refreshState
            )
        }
    }
}
