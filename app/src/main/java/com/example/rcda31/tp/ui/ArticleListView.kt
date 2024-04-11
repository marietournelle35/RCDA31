package com.example.rcda31.tp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rcda31.tp.model.Article
import com.example.rcda31.tp.viewmodel.ArticleListViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ArticleListView(
    viewModel: ArticleListViewModel = viewModel(factory = ArticleListViewModel.Factory),
    onAddArticle: () -> Unit,
    onGoToDetailArticle: (Long) -> Unit
) {
    val articles by remember(viewModel) {viewModel.articleList}.collectAsState()

    Column(modifier = Modifier.padding(24.dp)) {

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f, false)
        ){
            articles.forEach { article: Article ->
                ItemCardView(article = article) {
                    onGoToDetailArticle(it)
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    onAddArticle()
                }
            ) {
                Text(text = "Ajouter un article")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemCardView(
    article: Article,
    onGoToDetailArticle: (Long) -> Unit
) {

    val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE)

    var date = "Date inconnue"
    article.realaseDate?.let {
        date = formatter.format(it)
    }

    Card(
        modifier = Modifier
            .padding(vertical = 16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        onClick = { onGoToDetailArticle(article.id) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(13.dp)
        ) {
            Column (
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = article.title,
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = article.price.toString() + "€",
                        textAlign = TextAlign.End
                    )
                }
                Row (
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = date,
                        fontSize = 12.sp,
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        }
    }
}