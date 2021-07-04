package com.example.nasajm.network

import com.example.nasajm.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaMapApi {

    @GET("planetary/apod")
    suspend fun getPictureOfTheDaySuspend(
        @Query("date") date: String,
        @Query("api_key") apiKey: String = BuildConfig.NASA_API_KEY
    ): ApiPictureOfTheDay
}