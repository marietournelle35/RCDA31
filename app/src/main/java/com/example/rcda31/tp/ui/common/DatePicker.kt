package com.example.rcda31.tp.ui.common

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DatePicker(
    label: String,
    value: String,
    onValueChange: (String) -> Unit = {},
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    pattern: String = "dd-MM-yyyy",
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val formatter = DateTimeFormatter.ofPattern(pattern)
    val date = if (value.isNotBlank()) LocalDate.parse(value, formatter) else LocalDate.now()
    val dialog = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            onValueChange(LocalDate.of(year, month + 1, dayOfMonth).format(formatter))
        },
        date.year,
        date.monthValue - 1,
        date.dayOfMonth,
    )

    dialog.datePicker.minDate = System.currentTimeMillis()

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
        enabled = false,
        modifier = Modifier
            .clickable {
                dialog.show()
                keyboardController?.hide()
            }
            .fillMaxWidth(),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        colors = TextFieldDefaults.colors(
            disabledContainerColor = Color.White.copy(alpha = 0.6f),
            disabledTextColor = Color.DarkGray,
            disabledIndicatorColor = MaterialTheme.colorScheme.secondary
        )
    )
}