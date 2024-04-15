package com.example.rcda31.tp.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ESTexTield(
    label: String,
    value: String,
    singleLine: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (String) -> Unit
) {

    TextField(
        label = {
            Text(
                text = label,
                modifier = Modifier.padding(bottom = 12.dp),
                color = MaterialTheme.colorScheme.primary
            )
        },
        value = value,
        onValueChange = onValueChange,
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        modifier = Modifier
            .fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White.copy(alpha = 0.6f),
            unfocusedContainerColor = Color.White.copy(alpha = 0.6f)
        )
    )
}