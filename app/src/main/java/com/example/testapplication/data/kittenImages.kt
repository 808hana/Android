package com.example.testapplication.data

data class PixabayResponse(
    val total: Int,
    val totalHits: Int,
    val hits: List<ImageHit>
)

data class ImageHit(
    val id: Int,
    //val pageURL: String,
    //val type: String,
    val webformatURL: String,
    val tags: String,
    val favorite: Int,
    //val previewURL: String,
    //val previewWidth: Int,
    //val previewHeight: Int,
    //val webformatWidth: Int,
    //val webformatHeight: Int,
    //val largeImageURL: String,
    //val fullHDURL: String,
    //val imageURL: String,
    //val imageWidth: Int,
    //val imageHeight: Int,
    //val imageSize: Int,
    //val views: Int,
    //val downloads: Int,
    //val likes: Int,
    //val comments: Int,
    //val user_id: Int,
    //val user: String,
    //val userImageURL: String
)
