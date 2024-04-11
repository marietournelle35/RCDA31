package com.example.rcda31.tp.repository

import com.example.rcda31.tp.data.ArticleDAO
import com.example.rcda31.tp.model.Article
import com.example.rcda31.tp.data.DaoType
import com.example.rcda31.tp.data.DaoFactory


object ArticleRepository {

    private val articleDAO : ArticleDAO = DaoFactory.createArticleDAO(DaoType.MEMORY)

    fun getArticle(id : Long) : Article {
        return articleDAO.selectById(id)
    }
    fun addArticle(article: Article) : Long {
        return articleDAO.addNewOne(article)
    }

    fun getArticles() : List<Article> {
        return articleDAO.getAllArticle()
    }

}