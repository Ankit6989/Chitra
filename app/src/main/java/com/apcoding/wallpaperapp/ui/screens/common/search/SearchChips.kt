package com.apcoding.wallpaperapp.ui.screens.common.search

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apcoding.wallpaperapp.ui.theme.bottomAppBarContentColor

@Composable
fun SearchChips(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    if (selected) {
        TextButton(
            modifier = Modifier.padding(vertical = 2.dp),
            onClick = { },
            colors = ButtonDefaults.buttonColors(
                contentColor = colors.onSurface,
                backgroundColor = MaterialTheme.colors.bottomAppBarContentColor
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = text,
                fontSize = 12.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    } else {
        TextButton(
            modifier = Modifier.padding(vertical = 2.dp),
            onClick = {
                onClick()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.secondary.copy(0.1f),
                contentColor = MaterialTheme.colors.onSurface
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = text,
                fontSize = 12.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SearchChipsPreview() {
    SearchChips(
        text = "Nature",
        selected = true,
        onClick = { }
    )
}


