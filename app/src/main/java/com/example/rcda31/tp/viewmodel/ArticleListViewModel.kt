package com.example.rcda31.tp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.rcda31.tp.model.Article
import com.example.rcda31.tp.repository.ArticleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ArticleListViewModel(
    private val articleRepository: ArticleRepository
): ViewModel() {

    var articleList = MutableStateFlow<List<Article>>(emptyList())
   // var articlesList: StateFlow<List<Article>> = _articleList

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {

                return ArticleListViewModel(
                    ArticleRepository
                ) as T
            }
        }
    }

    init {
        viewModelScope.launch {
            articleList.value = articleRepository.getArticles()
        }
    }
}