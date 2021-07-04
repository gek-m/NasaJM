package com.example.nasajm.network

import com.google.gson.annotations.SerializedName

data class ApiPictureOfTheDay(
    @SerializedName("copyright")
    val copyright: String?,
    @SerializedName("date")
    val date: String?,
    @SerializedName("explanation")
    val explanation: String?,
    @SerializedName("hdurl")
    val hdurl: String?,
    @SerializedName("media_type")
    val mediaType: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?
)