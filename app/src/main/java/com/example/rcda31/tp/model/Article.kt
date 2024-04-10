package com.example.rcda31.tp.model

import java.util.Date

data class Article(
    var id: Long,
    val title: String,
    val description: String,
    val price: Double,
    val urlImage: String?,
    val realaseDate: Date?
)