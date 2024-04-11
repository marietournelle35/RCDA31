package com.example.rcda31.tp.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    article.realaseDate?.let { dateString ->
        date = formatter.format(dateString)
    }

    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com?q=eni-shop+${article.title}"))
    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = Modifier.padding(24.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                ) {
                Text(
                    text = article.title.uppercase(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            context.startActivity(intent)
                        }
                )
                if(article.urlImage != null) {
                    AsyncImage(
                        model = article.urlImage,
                        contentDescription = null,
                        alignment = Alignment.Center
                    )
                }
            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = article.description,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = article.price.toString() + " €",
                fontSize = 16.sp,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = date,
                fontSize = 16.sp,
                textAlign = TextAlign.End
            )
        }
    }
}