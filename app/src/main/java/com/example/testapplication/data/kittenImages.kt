package com.example.testapplication.data

data class KittenImage(
    val id: Int,
    val url: String,
    val width: Int,
    val height: Int,
    val orientation: String,
    val brightness: String,
    val tags: List<String>,
    val code: Int
)