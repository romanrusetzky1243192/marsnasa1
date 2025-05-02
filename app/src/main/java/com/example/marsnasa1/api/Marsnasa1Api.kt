package com.example.marsnasa1.api

import com.example.marsnasa1.model.Marsnasa1Response
import com.example.marsnasa1.retrofit.Marsnasa1Retrofitinstance
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Marsnasa1Api {

    @GET("mars-photos/api/v1/rovers/{rover}/photos")
    fun getPhotos(
        @Path("rover") rover: String,
        @Query("sol") sol: Int,
        @Query("earth_date") date: String? = null,
        @Query("api_key") apiKey: String
    ): Call<Marsnasa1Response>



    @GET("mars-photos/api/v1/rovers/{rover}/photos")
    fun getPhotosForSearch(
        @Path("rover") rover: String,
        @Query("sol") sol: Int,
        @Query("api_key") apiKey: String
    ): Call<Marsnasa1Response>

}