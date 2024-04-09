package com.example.rcda31.tp

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rcda31.ui.theme.RCDA31Theme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun FormView(){

    var title by rememberSaveable {
        mutableStateOf("")
    }
    var description by rememberSaveable {
        mutableStateOf("")
    }
    var price by rememberSaveable {
        mutableStateOf("€")
    }
    var releaseDate by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column (
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ){

                TextField(
                    label = {
                        Text(
                            text = "Titre",
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                    },
                    value = title,
                    onValueChange = {
                        title = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        MaterialTheme.colorScheme.primary
                    )
                )
                TextField(
                    label = {
                        Text(
                            text = "Description",
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                    },
                    value = description,
                    onValueChange = {
                        description = it
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    label = {
                        Text(
                            text = "Prix",
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                    },
                    value = price,
                    onValueChange = {
                        price = if (it.endsWith("€")) {
                            it
                        } else {
                            val numericValue = it
                            if (numericValue.isNotEmpty()) "$numericValue€" else "€"
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                DatePicker(
                    label = "Sortie initiale",
                    value = releaseDate,
                    onValueChange = {
                        releaseDate = it
                    }
                )
            }
        }
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.width(250.dp)
            ) {
                Text(
                    text = "Enregistrer",
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun DatePicker(
    label: String,
    value: String,
    onValueChange: (String) -> Unit = {},
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    pattern: String = "dd-MM-yyyy",
) {
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
            .clickable { dialog.show() }
            .fillMaxWidth(),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            disabledTextColor = Color.LightGray
        )
    )
}

@Preview(showBackground = true)
@Composable
fun FormViewPreview() {
    RCDA31Theme {
        FormView()
    }
}