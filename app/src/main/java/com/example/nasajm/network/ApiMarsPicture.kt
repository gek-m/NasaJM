package com.example.nasajm.network

import com.google.gson.annotations.SerializedName

data class ApiMarsPicture (
    @SerializedName("id")
    val id: Long,
    @SerializedName("sol")
    val sol: Int,
    @SerializedName("img_src")
    val image: String,
    @SerializedName("earth_date")
    val earthDate: String
)

