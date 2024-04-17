package com.example.rcda31.tp.data.network.api

import com.example.rcda31.tp.data.network.api.EniShopApiService.Companion.retrofit
import com.example.rcda31.tp.model.Article
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface EniShopApiService {

    companion object {
        private const val BASE_URL = "https://fakestoreapi.com/"

        private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

        val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()
    }

    @GET("products")
    suspend fun getArticles() : List<Article>

    @GET("products/{id}")
    suspend fun getArticleById(@Path("id") id: Long) : Article

    object EniShopApi {
        val retrofitService: EniShopApiService by lazy { retrofit.create(EniShopApiService::class.java) }
    }
}