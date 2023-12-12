package com.example.testapplication.ui.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.testapplication.R
import com.example.testapplication.data.AndroidDownloader
import com.example.testapplication.data.ImageHit


class MyListAdapter(private val context: Context, private val items: List<ImageHit>, private val downloader: AndroidDownloader) : ArrayAdapter<ImageHit>(context, 0, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val view = convertView ?: inflater.inflate(R.layout.list_item, parent, false)

        val item = items[position]
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        val textView = view.findViewById<TextView>(R.id.textView)
        val textViewBrightness = view.findViewById<TextView>(R.id.textView2)
        val btnDownload = view.findViewById<Button>(R.id.downloadBtn)

        val test_image = "https://img.freepik.com/free-photo/gray-kitty-with-monochrome-wall-her_23-2148955126.jpg?t=st=1702217947~exp=1702218547~hmac=873d4b8be07bcf3883fb36dc6b2cbfd50099b5f5925a1fe90d23ff29d03c92bd"
        Glide.with(context).load(item.webformatURL).error(R.drawable.ic_notifications_black_24dp).into(imageView)
        textView.text = "Tags: ${item.tags}"
        textViewBrightness.text = "Likes:  ${item.webformatURL}"
        btnDownload.setOnClickListener {
            downloader.downloadFile(item.webformatURL)
        }
        return view
    }
}