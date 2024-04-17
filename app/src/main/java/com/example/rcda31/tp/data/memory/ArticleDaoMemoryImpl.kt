package com.example.rcda31.tp.data.memory

import com.example.rcda31.tp.data.ArticleDAO
import com.example.rcda31.tp.model.Article

class ArticleDaoMemoryImpl : ArticleDAO {

    private val articlesInMemory: MutableList<Article> = mutableListOf(
        Article(
            1,
            "Souris",
            10.0,
            "Souris sans fil",
            "https://m.media-amazon.com/images/I/61-GeOlhtlL._AC_SX679_.jpg",
            null
        ),
        Article(2, "Ecran",  780.40, "Ecran 27\" FULL HD","", null),
        Article(3, "Barrete de mémoire vive",  45.90, "RAM 2 x 16Go","", null)
    )
    override suspend fun selectById(id: Long): Article {
        return articlesInMemory.first {article -> article.id == id }
    }

    override fun addNewOne(article: Article): Long {
        articlesInMemory.add(article)
        article.id = articlesInMemory.size.toLong()
        return article.id
    }

    override suspend fun getAllArticle(): List<Article> {
        return  articlesInMemory
    }


}