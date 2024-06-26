package com.example.rcda31.tp.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rcda31.tp.model.Article
import com.example.rcda31.tp.ui.common.DatePicker
import com.example.rcda31.tp.ui.common.ESTexTield
import com.example.rcda31.tp.viewmodel.FormViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun FormView(
    viewModel: FormViewModel = viewModel(factory = FormViewModel.Factory),
    onNavigateToHome: () -> Unit
){
    val context = LocalContext.current

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

    fun isSaveButtonEnabled() : Boolean {
        return title.isNotEmpty() && description.isNotEmpty() && price.isNotEmpty() && releaseDate.isNotEmpty()
    }

    fun submitArticle(context: Context) {
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
                image = null,
                realaseDate = date
            )
        )

        Toast.makeText(context, "Votre article a été enregistré avec succès", Toast.LENGTH_LONG).show()
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
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ){

                ESTexTield(
                    label = "Titre",
                    value = title,
                    onValueChange = { newTitle ->
                        title = newTitle
                    }
                )

                ESTexTield(
                    label = "Description",
                    value = description,
                    onValueChange = { newDescription ->
                        description = newDescription
                    }
                )

                ESTexTield(
                    label = "Prix",
                    value = price,
                    onValueChange = { newPrice ->
                        if (newPrice.count { char -> char == '.' } <= 1) {
                            val numericPart = newPrice.takeWhile { char -> char.isDigit() || char == '.' }
                            val formattedInput = if (numericPart.isEmpty()) "€" else "$numericPart€"
                            price = formattedInput
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                    submitArticle(context)
                    onNavigateToHome()
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
    }
}