package com.example.rcda31.tp.ui.common

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun SimpleSnackbar(
    isVisible: Boolean,
    message: String,
    onHide: () -> Unit,
    duration: Long = 5000
) {
    if (isVisible) {
        Scaffold { contentPadding ->
            Snackbar(
                shape = RoundedCornerShape(32.dp),
                modifier = Modifier
                    .padding(contentPadding),
                content = {
                    Text(
                        text = message
                    )
                }
            )
            LaunchedEffect(key1 = true) {
                delay(duration)
                onHide()
            }
        }
    }
}