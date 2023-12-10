package com.example.testapplication.ui.home

import android.app.LauncherActivity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.testapplication.R
import com.example.testapplication.data.ListItem

class MyListAdapter(context: Context, private val dataSource: List<ListItem>) :
    ArrayAdapter<ListItem>(context, 0, dataSource) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)

        val item = dataSource[position]
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        val textView = view.findViewById<TextView>(R.id.textView)

        imageView.setImageResource(item.imageResId)
        textView.text = item.text

        return view
    }
}