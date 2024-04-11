package com.example.rcda31.tp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

    Column {
        if(article.urlImage != null) {
            AsyncImage(
                model = article.urlImage,
                contentDescription = null
            )
        }

        Row {
            Text("Titre :")
            Text(article.title)
        }
        Row {
            Text("Description :")
            Text(article.description)
        }
        Row {
            Text("Prix :")
            Text(article.price.toString() + " €")
        }
        Row {
            Text("Date :")
            Text(date)
        }
    }
}