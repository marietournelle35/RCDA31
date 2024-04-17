package com.example.rcda31.tp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.rcda31.tp.model.Article
import com.example.rcda31.tp.repository.ArticleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ArticleDetailViewModel(
    val idArticle: Long = 1,
    private val articleRepository: ArticleRepository
): ViewModel() {

    var article = MutableStateFlow(Article())
    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {

                return ArticleDetailViewModel(
                    1,
                    ArticleRepository
                ) as T
            }
        }
    }

    init {
        viewModelScope.launch {
            article.value = articleRepository.getArticle(idArticle)
        }
    }
}