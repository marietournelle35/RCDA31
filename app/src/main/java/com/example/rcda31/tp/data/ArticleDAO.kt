package com.example.rcda31.tp.data

import com.example.rcda31.tp.model.Article

interface ArticleDAO {
    fun selectById(id : Long) : Article

    fun addNewOne(article: Article) : Long

    fun getAllArticle() : List<Article>

}