package com.example.testapplication.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapplication.api.api_interface
import com.example.testapplication.data.ImageHit
import com.example.testapplication.data.PixabayResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeViewModel : ViewModel() {

    private val _images = MutableLiveData<List<ImageHit>>()
    val images: LiveData<List<ImageHit>> = _images

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://pixabay.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun searchImages(searchQuery: String) {
        val apiService = retrofit.create(api_interface::class.java)
        val call = apiService.searchImages("41183002-bf3427640d13d18680465e50d", searchQuery, "photo")

        call.enqueue(object : Callback<PixabayResponse> {
            override fun onResponse(call: Call<PixabayResponse>, response: Response<PixabayResponse>) {
                if (response.isSuccessful) {
                    _images.value = response.body()?.hits
                }
            }

            override fun onFailure(call: Call<PixabayResponse>, t: Throwable) {
                // Handle failure, e.g., post a value to a LiveData that the UI can observe to show an error message
            }
        })
    }
}