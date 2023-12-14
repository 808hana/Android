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
import com.bumptech.glide.Glide
import com.example.testapplication.R
import com.example.testapplication.data.AndroidDownloader
import com.example.testapplication.data.ImageHit
import com.example.testapplication.data.SQLiteManager


class MyListAdapter(private val context: Context, private val items: MutableList<ImageHit>, private val downloader: AndroidDownloader) : ArrayAdapter<ImageHit>(context, 0, items) {

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
        btnFavorite.setOnClickListener{
            val imageUrl = item.webformatURL
            val tag = item.tags
            saveFavoriteImage(imageUrl, tag)
        }
        btnDownload.setOnClickListener {
            downloader.downloadFile(item.webformatURL)
        }
        return view
    }

    fun updateData(newItems: List<ImageHit>) {
        clear()
        addAll(newItems)
        notifyDataSetChanged()
    }

    private fun saveFavoriteImage(imageUrl : String, tag : String){
        val sqLiteManager = SQLiteManager(context)
        val db = sqLiteManager.writableDatabase
        val contentValues = ContentValues().apply {
            put("ImageUrl", imageUrl)
            put("Tag", tag)
            put("Favorite", 1)
        }

        db.insert("favorites", null, contentValues)
        db.close()
    }
}