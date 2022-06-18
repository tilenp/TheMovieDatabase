package com.example.themoviedatabase.ui.common

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import com.example.themoviedatabase.utils.UISnackbar

@Composable
fun <T : Any> MySnackbar(
    scaffoldState: ScaffoldState,
    content: UISnackbar<T>,
    onSnackbarActionPerformed: (T) -> Unit
) {
    val message = stringResource(content.message)
    val actionLabel = stringResource(content.actionLabel)
    LaunchedEffect(key1 = content.message) {
        val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
            message = message,
            actionLabel = actionLabel,
            duration = SnackbarDuration.Indefinite
        )
        if (snackbarResult == SnackbarResult.ActionPerformed) {
            onSnackbarActionPerformed(content.action)
        }
    }
}