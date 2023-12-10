package com.example.testapplication.api

import com.example.testapplication.data.KittenImage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApiService {

    companion object {
        const val SUBJECT_INFO_ENDPOINT = "images/getImages"
    }

    @GET(SUBJECT_INFO_ENDPOINT)
    suspend fun getSubjectInfo (
        @Query("url") url: String,
        @Query("orientation") orientation: String,
        @Query("outputFormat") outputFormat: String = "json"
    ) : Response<KittenImage>
}