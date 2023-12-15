package com.example.testapplication.api

import com.example.testapplication.data.PixabayResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface api_interface {
    @GET("api/")
    fun searchImages(
        @Query("key") apiKey: String,
        @Query("q") query: String,
        @Query("image_type") imageType: String,
        @Query("page") page: Int
    ): Call<PixabayResponse>
}