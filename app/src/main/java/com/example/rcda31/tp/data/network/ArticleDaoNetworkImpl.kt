package com.example.rcda31.tp.data.network

import com.example.rcda31.tp.data.ArticleDAO
import com.example.rcda31.tp.data.network.api.EniShopApiService
import com.example.rcda31.tp.model.Article

class ArticleDaoNetworkImpl : ArticleDAO {
    override suspend fun selectById(id: Long): Article {
        return EniShopApiService.EniShopApi.retrofitService.getArticleById(id)
    }

    override fun addNewOne(article: Article): Long {
        TODO("Not yet implemented")
    }

    override suspend fun getAllArticle(): List<Article> {
        return EniShopApiService.EniShopApi.retrofitService.getArticles()
    }
}