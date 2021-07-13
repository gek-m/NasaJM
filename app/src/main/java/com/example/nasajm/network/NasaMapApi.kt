package com.example.nasajm.network

import com.example.nasajm.BuildConfig
import com.example.nasajm.util.setDateInString
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaMapApi {

    @GET("planetary/apod")
    suspend fun getPictureOfTheDaySuspend(
        @Query("date") date: String,
        @Query("api_key") apiKey: String = BuildConfig.NASA_API_KEY
    ): ApiPictureOfTheDay

    @GET("EPIC/api/natural")
    suspend fun getEarthPhotoListSuspend(
        @Query("api_key") apiKey: String = BuildConfig.NASA_API_KEY
    ): List<ApiEarthPicture>

    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    suspend fun getMarsPhotoListSuspend(
        @Query("earth_date") date: String = setDateInString(2, "yyyy-M-dd"),
        @Query("api_key") apiKey: String = BuildConfig.NASA_API_KEY
    ): ApiMarsPhotoResponse
}