package com.example.rcda31.tp.ui

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rcda31.tp.model.Article
import com.example.rcda31.tp.viewmodel.FormViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun FormView(viewModel: FormViewModel = viewModel(factory = FormViewModel.Factory)){

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

    var isVisible by rememberSaveable {
        mutableStateOf(false)
    }

    fun isSaveButtonEnabled() : Boolean {
        return title.isNotEmpty() && description.isNotEmpty() && price.isNotEmpty() && releaseDate.isNotEmpty()
    }

    fun submitArticle() {
        val format = SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE)
        val date = format.parse(releaseDate)

        val numericString = price.dropLast(1)
        val priceDouble = numericString.toDouble()

        viewModel.saveArticle(
            Article(
                id = 0,
                title = title,
                description = description,
                price = priceDouble,
                urlImage = null,
                realaseDate = date
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
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
                            modifier = Modifier.padding(bottom = 12.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                    },
                    value = title,
                    onValueChange = {
                        title = it
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    label = {
                        Text(
                            text = "Description",
                            modifier = Modifier.padding(bottom = 12.dp),
                            color = MaterialTheme.colorScheme.primary
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
                            modifier = Modifier.padding(bottom = 12.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                    },
                    value = price,
                    onValueChange = {
                        if (it.count { char -> char == '.' } <= 1) {
                            val numericPart = it.takeWhile { char -> char.isDigit() || char == '.' }
                            val formattedInput = if (numericPart.isEmpty()) "€" else "$numericPart€"
                            price = formattedInput
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

        Spacer(modifier = Modifier.height(32.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                enabled = isSaveButtonEnabled(),
                onClick = {
                    CoroutineScope(Dispatchers.Main).launch {
                        submitArticle()
                        isVisible = true
                    }
                          },
                modifier = Modifier.width(250.dp)
            ) {
                Text(
                    text = "Enregistrer",
                    fontSize = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        SimpleSnackbar(
            isVisible = isVisible,
            message = "Vous venez de créer l'article $title vendu pour un montant de $price",
            onHide = { isVisible = false }
        )
    }
}

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
            disabledTextColor = Color.Black
        )
    )
}

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