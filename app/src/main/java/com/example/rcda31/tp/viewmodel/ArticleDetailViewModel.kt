package com.example.rcda31.tp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.rcda31.tp.model.Article
import com.example.rcda31.tp.repository.ArticleRepository

class ArticleDetailViewModel(
    private val articleRepository: ArticleRepository
): ViewModel() {
    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {

                return ArticleDetailViewModel(
                    ArticleRepository
                ) as T
            }
        }
    }

    fun fetchArticle(idArticle: Long): Article {
        return articleRepository.getArticle(idArticle)
    }
}