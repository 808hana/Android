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

    private var currentPage = 1
    private var currentQuery: String? = null

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://pixabay.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun searchImages(searchQuery: String) {
        currentPage = 1
        currentQuery = searchQuery
        loadImages(searchQuery, currentPage)
    }

    fun loadMoreImages() {
        if (_isLoading.value == true) {
            return
        }

        currentQuery?.let {
            _isLoading.value = true
            currentPage++
            loadImages(it, currentPage)
        }
    }


    private fun loadImages(query: String, page: Int) {
        val apiService = retrofit.create(api_interface::class.java)
        val call = apiService.searchImages("41183002-bf3427640d13d18680465e50d", query, "photo", page)

        call.enqueue(object : Callback<PixabayResponse> {
            override fun onResponse(call: Call<PixabayResponse>, response: Response<PixabayResponse>) {
                if (response.isSuccessful) {
                    val currentImages = _images.value ?: listOf()
                    val newImages = response.body()?.hits ?: listOf()
                    _images.value = currentImages + newImages
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<PixabayResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }
}