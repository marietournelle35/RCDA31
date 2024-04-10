package com.example.rcda31.tp.data

import com.example.rcda31.tp.data.memory.ArticleDaoMemoryImpl
import com.example.rcda31.tp.data.network.ArticleDaoNetworkImpl

abstract class DaoFactory {
    companion object{
        fun createArticleDAO(type: DaoType) : ArticleDAO {

            return when(type){
                DaoType.MEMORY -> ArticleDaoMemoryImpl()
                DaoType.NETWORK -> ArticleDaoNetworkImpl()
            }

        }

    }
}