package com.example.rcda31.tp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.rcda31.tp.viewmodel.ArticleDetailViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ArticleDetailView(
    idArticle: Long,
    viewModel: ArticleDetailViewModel = viewModel(factory = ArticleDetailViewModel.Factory),
    ) {

    val article = viewModel.fetchArticle(idArticle = idArticle)

    val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE)

    var date = "Date inconnue"
    article.realaseDate?.let {
        date = formatter.format(date)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.padding(24.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            if(article.urlImage != null) {
                AsyncImage(
                    model = article.urlImage,
                    contentDescription = null,
                    alignment = Alignment.Center
                )
            }
        }

        Row {
            Text(text = "Titre : ", fontWeight = FontWeight.Bold)
            Text(article.title)
        }
        Row {
            Text(text = "Description : ", fontWeight = FontWeight.Bold)
            Text(article.description)
        }
        Row {
            Text(text = "Prix : ", fontWeight = FontWeight.Bold)
            Text(article.price.toString() + " €")
        }
        Row {
            Text("Date : ", fontWeight = FontWeight.Bold)
            Text(date)
        }
    }
}