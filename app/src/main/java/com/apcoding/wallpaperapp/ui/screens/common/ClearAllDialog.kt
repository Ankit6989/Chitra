package com.hamza.wallpap.ui.screens.common

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.apcoding.wallpaperapp.R
import com.apcoding.wallpaperapp.data.local.dao.FavUrlsViewModel
import com.apcoding.wallpaperapp.navigation.Screen
import com.apcoding.wallpaperapp.ui.theme.bottomAppBarBackgroundColor
import com.apcoding.wallpaperapp.ui.theme.maven_pro_regular
import com.apcoding.wallpaperapp.ui.theme.textColor


@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun ClearAllDialog(
    dialogState: MutableState<Boolean>,
    context: Context,
    dialogText: String,
    currentRoute: String?,
    favUrlsViewModel: FavUrlsViewModel,
) {
    Dialog(
        onDismissRequest = { dialogState.value = false },
        properties = DialogProperties(usePlatformDefaultWidth = true),
    ) {
        ClearAllDialogUI(
            modifier = Modifier,
            dialogState,
            context,
            dialogText,
            currentRoute,
            favUrlsViewModel
        )
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun ClearAllDialogUI(
    modifier: Modifier = Modifier,
    dialogState: MutableState<Boolean>,
    context: Context,
    dialogText: String,
    currentRoute: String?,
    favUrlsViewModel: FavUrlsViewModel,
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = 2.dp,
    ) {
        Column(
            modifier
                .background(color = MaterialTheme.colors.background)
        ) {

            Box(
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painterResource(id = R.drawable.wattpad),
                        contentDescription = null,
                        contentScale = ContentScale.Fit, modifier = Modifier
                            .padding(top = 0.dp, bottom = 10.dp)
                            .size(90.dp)
                    )

                    Divider(
                        modifier.padding(10.dp),
                        thickness = 1.dp,
                        color = MaterialTheme.colors.textColor
                    )

                    Column(
                        modifier = modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {

                        Text(
                            buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colors.textColor,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = maven_pro_regular
                                    )
                                ) {
                                    append(dialogText)
                                }
                            })
                    }
                }
            }
            Row(
                modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colors.bottomAppBarBackgroundColor
                    ), horizontalArrangement = Arrangement.SpaceAround
            ) {
                if (currentRoute.equals(Screen.Favourite.route)) {
                    favUrlsViewModel.deleteAllFavouriteUrls()
                    Toast.makeText(context, "Removed all images!", Toast.LENGTH_SHORT)
                        .show()
                    dialogState.value = false
                }
            }
        }
    }
}
