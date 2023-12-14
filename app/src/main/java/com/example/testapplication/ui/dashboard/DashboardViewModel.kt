package com.example.testapplication.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapplication.data.ImageHit
import com.example.testapplication.data.SQLiteManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DashboardViewModel(private val dbHelper : SQLiteManager) : ViewModel() {

    private val _favoriteImages = MutableLiveData<List<ImageHit>>()
    val favoriteImages: LiveData<List<ImageHit>> = _favoriteImages

    fun loadFavoriteImages() {
        viewModelScope.launch(Dispatchers.IO) {
            val db = dbHelper.readableDatabase
            val cursor = db.query("favorites", arrayOf("ImageUrl", "Tag", "Favorite"), null, null, null, null, null)
            val images = mutableListOf<ImageHit>()

            if (cursor.moveToFirst()) {
                do {
                    val imageUrl = cursor.getString(cursor.getColumnIndexOrThrow("ImageUrl"))
                    val tag = cursor.getString(cursor.getColumnIndexOrThrow("Tag"))
                    val favorite = cursor.getInt(cursor.getColumnIndexOrThrow("Favorite"))
                    val imageHit = ImageHit(imageUrl, tag, favorite)
                    images.add(imageHit)
                } while (cursor.moveToNext())
            }
            cursor.close()
            db.close()

            _favoriteImages.postValue(images)
        }
    }
}