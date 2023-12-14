package com.example.testapplication.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.media.Image
import com.google.android.material.textfield.TextInputLayout.LengthCounter
import java.text.DateFormat
import java.text.SimpleDateFormat

class SQLiteManager(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "ImageDB"
        private const val DATABASE_VERSION = 3
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableStatement = """
            CREATE TABLE favorites (
                Id INTEGER PRIMARY KEY AUTOINCREMENT,
                ImageUrl TEXT UNIQUE,
                Tag TEXT,
                Favorite INTEGER
            )
        """.trimIndent()
        db?.execSQL(createTableStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS favorites")
        onCreate(db)
    }
}