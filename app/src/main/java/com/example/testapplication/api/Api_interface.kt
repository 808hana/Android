package com.example.testapplication.api

import com.example.testapplication.data.PixabayResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface api_interface {
    /*@Headers(
        "Accept: application/json",
        "X-RapidAPI-Key: e58a9fb232mshf909572677157cdp1fe40bjsnd401a58c03d1",
        "X-RapidAPI-Host: mlemapi.p.rapidapi.com"

    )
    @GET("randommlem")
    fun getKittenImages(): Call<KittenImage>*/

    @GET("api/")
    fun searchImages(
        @Query("key") apiKey: String,
        @Query("q") query: String,
        @Query("image_type") imageType: String
        // Add other parameters as needed
    ): Call<PixabayResponse>
}