package com.example.themoviedatabase.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import com.example.themoviedatabase.R

@Composable
fun MyTopBar(
    modifier: Modifier = Modifier,
    title: String
) {
    val spacingS = dimensionResource(R.dimen.spacing_s)
    TopAppBar(
        modifier = modifier.testTag("MyTopBar"),
        title = {
            Text(
                text = title,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.h5
            )
        },
        elevation = spacingS,
    )
}
