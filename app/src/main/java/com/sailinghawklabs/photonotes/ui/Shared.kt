package com.sailinghawklabs.photonotes.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenericAppBar(
    title: String,
    onIconClick: (() -> Unit)?,
    icon: @Composable (() -> Unit)?,
    iconState: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    SmallTopAppBar(
        title = { Text(text = title) },
        actions = {
            IconButton(onClick = { onIconClick?.invoke() }) {
                if (iconState.value) {
                    icon?.invoke()
                }
            }
        }
    )
}