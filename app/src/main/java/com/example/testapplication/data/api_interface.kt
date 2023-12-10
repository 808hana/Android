package com.example.testapplication.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
interface api_interface {
    @Headers(
        "Accept: application/json",
        "X-RapidAPI-Key: e58a9fb232mshf909572677157cdp1fe40bjsnd401a58c03d1",
        "X-RapidAPI-Host: mlemapi.p.rapidapi.com"

    )
    @GET("randommlem")
    fun getKittenImages(): Call<KittenImage>
}