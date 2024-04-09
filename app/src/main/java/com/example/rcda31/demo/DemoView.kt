package com.example.rcda31.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rcda31.ui.theme.RCDA31Theme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DemoView() {

    var numberValues by rememberSaveable {
        mutableStateOf("")
    }

    val valuesList = remember { mutableStateListOf<String>() }

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            label = { Text(text = "Entrez le nombre de valeurs", modifier = Modifier.padding(bottom = 12.dp)) },
            value = numberValues,
            onValueChange = { newValue ->
                numberValues = newValue
                val newCount = newValue.toIntOrNull() ?: 0
                while (valuesList.size < newCount) {
                    valuesList.add("")
                }
                while (valuesList.size > newCount) {
                    valuesList.removeLast()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        valuesList.forEachIndexed { index, value ->
            TextField(
                label = { Text(text = "Valeur ${index + 1}", modifier = Modifier.padding(bottom = 12.dp)) },
                value = value,
                onValueChange = { newValue ->
                    valuesList[index] = newValue
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )
        }

        var average by remember { mutableFloatStateOf(0f) }
        var isAverageShow by remember { mutableStateOf(false) }

        Row {
            Button(
                onClick = {
                    isAverageShow = true
                    average = averageCalculation(valuesList)
                    keyboardController?.hide()
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Moyenne")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    numberValues = ""
                    valuesList.clear()
                    average = 0f
                    isAverageShow = false
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Recommencer")
            }
        }

        if(isAverageShow) {
            Text(
                text = "Votre moyenne est de ${String.format("%.2f", average)}",
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                )
        }
    }
}

private fun averageCalculation(numbers: List<String>): Float {
    val validNumbers = numbers.mapNotNull { it.toFloatOrNull() }
    return if (validNumbers.isNotEmpty()) validNumbers.sum() / validNumbers.size else 0f
}

@Preview
@Composable
fun DemoViewPreview() {
    RCDA31Theme {
        DemoView()
    }
}
