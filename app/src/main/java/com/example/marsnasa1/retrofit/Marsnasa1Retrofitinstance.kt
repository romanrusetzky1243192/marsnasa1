package com.example.marsnasa1.retrofit

import com.example.marsnasa1.api.Marsnasa1Api
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Marsnasa1Retrofitinstance {
    private const val BASE_URL = "https://api.nasa.gov/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)        .build()
    val api: Marsnasa1Api by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Marsnasa1Api::class.java)
    }
}