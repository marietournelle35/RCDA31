package com.example.rcda31.tp.data

import com.example.rcda31.tp.model.Article

interface ArticleDAO {
    suspend fun selectById(id : Long) : Article

    fun addNewOne(article: Article) : Long

    suspend fun getAllArticle() : List<Article>

}