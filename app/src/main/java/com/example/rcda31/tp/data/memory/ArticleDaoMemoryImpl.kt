package com.example.rcda31.tp.data.memory

import com.example.rcda31.tp.data.ArticleDAO
import com.example.rcda31.tp.model.Article

class ArticleDaoMemoryImpl : ArticleDAO {

    private val articlesInMemory: MutableList<Article> = mutableListOf(
        Article(
            1,
            "Souris",
            "Souris sans fil",
            10.0,
            "https://m.media-amazon.com/images/I/61-GeOlhtlL._AC_SX679_.jpg",
            null
        ),
        Article(2, "Ecran", "Ecran 27\" FULL HD", 780.40, "", null),
        Article(3, "Barrete de mémoire vive", "RAM 2 x 16Go", 45.90, "", null)
    )
    override fun selectById(id: Long): Article {
        return articlesInMemory.first {article -> article.id == id }
    }

    override fun addNewOne(article: Article): Long {
        articlesInMemory.add(article)
        article.id = articlesInMemory.size.toLong()
        return article.id
    }

    override fun getAllArticle(): List<Article> {
        return  articlesInMemory
    }


}