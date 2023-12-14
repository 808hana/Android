package com.example.testapplication.ui.home

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.bumptech.glide.Glide
import com.example.testapplication.R
import com.example.testapplication.data.AndroidDownloader
import com.example.testapplication.data.ImageHit
import com.example.testapplication.data.SQLiteManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MyListAdapter(private val context: Context, private val items: MutableList<ImageHit>, private val downloader: AndroidDownloader, private val dbHelper: SQLiteManager) : ArrayAdapter<ImageHit>(context, 0, items) {

    private var favoriteStatusMap = mutableMapOf<Int, Boolean>()
    init {
        fetchFavoriteStatuses()
    }

    private fun fetchFavoriteStatuses() {
        val db = dbHelper.readableDatabase
        val cursor = db.query("favorites", arrayOf("ImageId"), "Favorite = ?", arrayOf("1"), null, null, null)

        val favoritesSet = HashSet<Int>()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("ImageId"))
                favoritesSet.add(id)
            } while (cursor.moveToNext())
            cursor.close()
        }
        db.close()

        favoriteStatusMap.clear()
        items.forEach { imageHit ->
            favoriteStatusMap[imageHit.id] = favoritesSet.contains(imageHit.id)
        }
    }

    fun updateData(newItems : List<ImageHit>) {
        clear()
        addAll(newItems)
        CoroutineScope(Dispatchers.IO).launch {
            fetchFavoriteStatuses()
            withContext(Dispatchers.Main) {
                notifyDataSetChanged()
            }
        }
    }

    private fun isFavorite(ImageId: Int): Boolean {
        return favoriteStatusMap[ImageId] ?: false
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val view = convertView ?: inflater.inflate(R.layout.list_item, parent, false)

        val item = items[position]
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        val textView = view.findViewById<TextView>(R.id.textView)
        val btnFavorite = view.findViewById<ImageButton>(R.id.favorite_btn)
        val btnDownload = view.findViewById<ImageButton>(R.id.downloadBtn)

        Glide.with(context).load(item.webformatURL).error(R.drawable.error).into(imageView)
        textView.text = "Tags: ${item.tags}"

        if(isFavorite(item.id)){
            btnFavorite.setImageResource(R.drawable.heart_filled)
        } else{
            btnFavorite.setImageResource(R.drawable.heart)
        }

        btnFavorite.setOnClickListener {
            val imageUrl = item.webformatURL
            val tag = item.tags
            val id = item.id
            btnFavorite.setImageResource(R.drawable.heart_filled)
            saveFavoriteImage(id, imageUrl, tag)
        }

        btnDownload.setOnClickListener {
            downloader.downloadFile(item.webformatURL)
        }
        return view
    }

    private fun saveFavoriteImage(id : Int, imageUrl : String, tag : String){
        val sqLiteManager = SQLiteManager(context)
        val db = sqLiteManager.writableDatabase
        val contentValues = ContentValues().apply {
            put("ImageId", id)
            put("ImageUrl", imageUrl)
            put("Tag", tag)
            put("Favorite", 1)
        }

        db.insert("favorites", null, contentValues)
        db.close()
    }
}