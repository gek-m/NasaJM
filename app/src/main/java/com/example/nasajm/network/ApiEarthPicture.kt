package com.example.nasajm.network

import com.google.gson.annotations.SerializedName
import java.util.*

data class ApiEarthPicture (
    @SerializedName("identifier")
    val identifier: String,
    @SerializedName("caption")
    val caption: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("date")
    val date: String
)