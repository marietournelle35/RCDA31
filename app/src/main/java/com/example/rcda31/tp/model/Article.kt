package com.example.rcda31.tp.model

import java.util.Date

data class Article(
    var id: Long = -1,
    val title: String = "",
    val price: Double = 0.0,
    val description: String = "",
    val image: String? = "",
    @Transient
    val realaseDate: Date? = Date()
)