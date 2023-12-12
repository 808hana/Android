package com.example.testapplication.data

interface Downloader {
    fun downloadFile(url: String): Long
}