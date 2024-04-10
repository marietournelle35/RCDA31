package com.example.rcda31.tp.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.rcda31.tp.model.Article
import com.example.rcda31.tp.repository.ArticleRepository

class FormViewModel(
    private val articleRepository: ArticleRepository
): ViewModel() {

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {

                return FormViewModel(
                    ArticleRepository
                ) as T
            }
        }
    }

    fun saveArticle(article: Article) {
        articleRepository.addArticle(article)
    }

    fun showArticleSaved(id: Long): Article {
        return articleRepository.getArticle(id)
    }
}